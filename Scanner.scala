
class Scanner(val repo : String) {

   def scan() : List[GitCommit] = {

      val commit_ttl = new SubProcess().run("git log --pretty=oneline",repo)._2.length
      System.err.println("Processing " + commit_ttl + " commits, this may take a while...")

      val results = new SubProcess().run("/usr/bin/git log", repo)
      var commits : List[GitCommit]  = Nil
      var hash    : String = null
      var comment : String = null
      var author  : String = null
      var date    : String = null

      results._2.foreach(line => {
          if (line == null)
              {}
          else if (line.startsWith("commit "))
              hash = line.replace("commit ","")
          else if (line.startsWith("Author:")) {
              author = line.replace("Author:","")
          }
          else if (line.startsWith("Date:"))
              date = line.replace("Date:","")
          else if ((line != "\n") && (line != "")) {
              if (comment == null) { comment = line }
              else { comment = comment + "\n" + line }
          }
          if ((author != null) && (hash != null) && (date != null) && (comment != null)) {
              commits += new GitCommit(
                  Utils.lstrip(author),
                  Utils.lstrip(hash),
                  Utils.lstrip(date),
                  Utils.lstrip(comment),
                  repo
              )
              author  = null
              hash    = null
              date    = null
              comment = null
          }
      })
      return commits
   }

}


