
class Scanner {

   // private var tree : String
   private var commits : List[GitCommit]  = Nil

   def scan() = {
      val results = new SubProcess().run("/usr/bin/git log")
      var hash    : String = null
      var comment : String = null
      var author  : String = null
      var date    : String = null

      results._2.foreach(line => {
          if (line == null)
              {}
          else if (line.startsWith("commit "))
              hash = line.replace("commit ","")
          else if (line.startsWith("Author "))
              author = line.replace("Author ","")
          else if (line.startsWith("Date "))
              date = line.replace("Date ","")
          else if (line != "\n")
              comment = comment + line
          if ((author != null) && (hash != null) && (date != null) && (comment != null)) {
              commits += new GitCommit(author,hash,date,comment)
              author  = null
              hash    = null
              date    = null
              comment = null
          }
      })

      commits.foreach(c => {
          println("Author = " + c.author.toString())
      })
   }

}


