import java.io._
import scala.collection.mutable.HashMap
import scala.util.Sorting
import scala.actors._

class MainActor(val path : String) extends Actor {

   // pool of 3 actors for git scanning
   val deepScanners : List[GitCommitScanActor] = List(
       new GitCommitScanActor(),
       new GitCommitScanActor(),
       new GitCommitScanActor()
   )

   def act() {

       // the initial scan cannot be parallelized
       var commits = new Scanner(path).scan()
       val commit_len = commits.length
      
       // send commits out for deeper scanning and get back a new list 
       // FIXME: abstract out a worker manager
       deepScanners.foreach(ds => ds.start())
       var ct : Int = 0
       for (c <- commits) { 
          ct = ct + 1
          if (ct % 3 == 0)      { deepScanners(0) ! DeepScanCommit(c,this); }
          else if (ct % 2 == 0) { deepScanners(1) ! DeepScanCommit(c,this); }
          else                  { deepScanners(2) ! DeepScanCommit(c,this); }
       } 

       // wait to recieve all fully scanned commits
       commits = Nil 
       while(commits.length < commit_len) {
            receive {
                 case DeepScanCommitResult(gc : GitCommit) => {
                    if (commits.length % 25 == 0) 
                        println("scanning: " + commits.length + "/" + commit_len)
                    commits += gc
                    if (commits.length == commit_len) {
                        deepScanners.foreach(ds => ds ! DeepScansComplete())
                    }
                 }
            }
  
       }

       // FIXME: this should be saved in reponame.csv
       println("=== PEOPLE REPORT ===")
       val personReport = new PersonReport(commits)
       println(personReport.csv_header())
       personReport.compute().foreach(person => {
            println(person.to_csv())
       })


   }
}

object App {
   def main(args: Array[String]) {
      if (args.length < 1) {
          println("Must specify a local git repo path")
      }
      new MainActor(args(0)).start()
   }
}

