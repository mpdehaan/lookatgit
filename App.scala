import java.io._
import scala.collection.mutable.HashMap

object App {
   def main(args: Array[String]) {
      if (args.length < 1) {
          println("Must specify a local git repo path")
      }
      else {
          val commits = new Scanner(args(0)).scan()
          for ((name,data) <- new PersonStatCalculator(commits).compute()) {
              println("------------------")
              println("Name            = " + name)
              println("# Commits       = " + data.commits.length)
              println("# Lines Added   = " + data.lines_added)
              println("# Lines Removed = " + data.lines_removed)
              println("# Files Touched = " + data.files_modified.size) 
          } 
      }
   }
}

