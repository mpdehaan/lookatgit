
class Scanner {

   // private var tree : String
   private var commits : List[GitCommit]  = Nil

   def scan() = {
      val results = new SubProcess().run("/usr/sbin/git log")
      println(results._1)
      println(results._2)
      var hash    = ""
      var comment = ""
      var author  = ""
      var date    = ""

      results._2.foreach(line => {
          if (line.startsWith("commit "))
              hash = line.replace("commit ","")
          else if (line.startsWith("Author "))
              author = line.replace("Author ","")
          else if (line.startsWith("Date "))
              date = line.replace("Date ","")
          else if (line != "\n")
              comment = comment + line
          commits += new GitCommit(author,hash,date,comment)
      })
   }

}


