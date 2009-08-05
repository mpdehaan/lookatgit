import scala.collection.mutable.HashSet

class PersonData(var name           : String,
                 var commits        : List[GitCommit],
                 var lines_added    : Integer, 
                 var lines_removed  : Integer, 
                 var files_modified : HashSet[String]
                 ) {

   def print() : Unit = {
       println("name = " + name)
       println("commits = " + commits)
       println("lines_added = " + lines_added)
       println("lines_removed = " + lines_removed)
       println("files_modified = " + files_modified)
   }

}

