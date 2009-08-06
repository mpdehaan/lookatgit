/*
Copyright 2009, Michael DeHaan <mdehaan@fedoraproject.org>

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
02110-1301  USA
*/

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


