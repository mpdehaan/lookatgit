import java.io._
import scala.collection.mutable.HashMap
import scala.util.Sorting

object App {

   def main(args: Array[String]) {
      if (args.length < 1) {
          println("Must specify a local git repo path")
      }
      else {
          val commits = new Scanner(args(0)).scan()
          // FIXME: PersonReport should be called PeopleCounter
          var people = new PersonReport(commits).compute()
          people.foreach(person => {
              println("------------------")
              println("Name            = " + person.name)
              println("# Commits       = " + person.commits.size)
              println("# Lines Added   = " + person.added)
              println("# Lines Removed = " + person.removed)
              println("# Files Touched = " + person.files.size) 
          })
 
      }
   }
}

