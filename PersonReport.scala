import scala.collection.mutable.HashSet
import scala.collection.mutable.HashMap

class PersonReport(var commits : List[GitCommit]) {

 
   def compute() : Iterator[Person] = {
       var peopleInfo : HashMap[String, Person] = new HashMap()
       commits.foreach(c => {
            println("* processing commit = " + c.author)
            if (! peopleInfo.contains(c.author)) {
                peopleInfo += (c.author -> new Person(c.author))
            }
            peopleInfo(c.author) += c
       })
       return peopleInfo.values
   }

}

