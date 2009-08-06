import scala.collection.mutable.HashSet

class Person(var name : String) {

   var commits : HashSet[GitCommit]  = new HashSet[GitCommit]
   var added   : Int             = 0
   var removed : Int             = 0
   var files   : HashSet[String] = new HashSet[String]
   var impact  : Int             = 0

   def +=(commit : GitCommit) : Unit = {
       commits   += commit
       for(d <- commit.diffs) {
           added     += d.added
           removed   += d.removed
           files      += d.path
       }
       impact = added + removed
   }

   def to_csv() : String = {
       return List(name,commits.size,added,removed,impact).mkString(",")
   }

}

