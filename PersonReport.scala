import scala.collection.mutable.HashSet
import scala.collection.mutable.HashMap

class PersonReport(var commits : List[GitCommit]) {

   def compute() : Iterator[Person] = {
       val peopleInfo : HashMap[String, Person] = new HashMap()
       commits.foreach(c => {
            if (! peopleInfo.contains(c.author)) {
                peopleInfo += (c.author -> new Person(c.author))
            }
            peopleInfo(c.author) += c
       })
       return peopleInfo.values
   }

   def csv_header() : String = {
       return "name,commits,added,removed,impact"
   }


}

