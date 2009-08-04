import scala.collection.mutable.HashMap

class GitCommit(val author  : String, 
                val hash    : String, 
                val date    : String, 
                val comment : String,
                val repo    : String) {

    var added    : Int          = 0
    var deleted  : Int          = 0
    var merger   : String       = null
    var files    : List[String] = Nil
    var changes  : HashMap[String,FileChange] = new HashMap[String,FileChange]

    def deepScan() : Unit = {
        val results = new SubProcess().run("/usr/bin/git show " + hash, repo)

        var from_file  : String  = null
        var to_file    : String  = null
        var moved_file : Boolean = false
        var diff       : String  = ""
        var diff_start : Boolean = false

        results._2.foreach(line => {
            if (line == null) {
                if (to_file != null) {
                    files   += to_file
                    changes += (to_file -> new FileChange(this, to_file, moved_file, added, deleted, diff))
                }
                diff       = ""
                from_file  = null
                to_file    = null
                moved_file = false
                diff_start = false
            }
            else if (line.startsWith("commit ")) {
                // already scanned
            }
            else if (line.startsWith("Merge: ")) {
                merger = line.replace("Merge: ","")
            }
            else if (line.startsWith("Date: ")) {
                // already scanned
            }
            else if (line.startsWith(" ")) {
                // no data
            }
            else if (line.startsWith("diff --git")) {
               diff_start = true
            }
            else if (line.startsWith("---")) {
                // source file not that useful
                // FIXME: should we detect and flag move operations?
               from_file = line.replace("+++ b/", "")
            }
            else if (line.startsWith("+++")) {
               to_file = line.replace("+++ b/", "")
               if (from_file != to_file) {
                   moved_file = true
               }
            }
            else if (diff_start && line.startsWith("-")) {
               deleted += 1
            }
            else if (diff_start && line.startsWith("+")) {
               added += 1 
            }
            if (diff_start && line != null) {
               diff += line
            }
        })
    }

    def print() : Unit = {
        println("author  = " + author)
        println("files   = " + files)
        println("hash    = " + hash)
        println("date    = " + date)
        println("merger  = " + merger)
        println("added   = " + added)
        println("deleted = " + deleted)
    }

} // End class


