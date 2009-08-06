import scala.collection.mutable.HashMap

class GitCommit(val author  : String, 
                val hash    : String, 
                val date    : String, 
                val comment : String,
                val repo    : String) {

    var merger   : String       = null
    var diffs    : List[Diff]   = Nil

    def deepScan() : GitCommit  = {

        val results = new SubProcess().run("/usr/bin/git show " + hash, repo)
        var from_file    : String  = null
        var to_file      : String  = null
        var moved_file   : Boolean = false
        var diff_text    : String  = ""
        var file_added   : Int     = 0
        var file_removed : Int   = 0

        def start_new_diff() : Unit = {
            from_file  = null
            to_file    = null
            moved_file = false
            file_added = 0
            file_removed = 0
            diff_text  = "__UNDEFINED__"
        }

        results._2.foreach(line => {
            if (line == null) {
                if (to_file != null) {
                    diffs += new Diff(this, to_file, moved_file, file_added, file_removed, "") // diff_text)
                }
                start_new_diff()
            }
            else if (line.startsWith("commit ")) {}
            else if (line.startsWith("Merge: ")) 
                merger = line.replace("Merge: ","")
            else if (line.startsWith("Date: ")) {}
            else if (line.startsWith(" ")) {}
            else if (line.startsWith("diff --git")) {
               if (diff_text != "__UNDEFINED__" && to_file != null) {
                    diffs += new Diff(this, to_file, moved_file, file_added, file_removed, diff_text)
                    start_new_diff()
               }
               diff_text = ""
            }
            else if (line.startsWith("---")) {
               from_file = line.replace("+++ b/", "")
            }
            else if (line.startsWith("+++")) {
               to_file = line.replace("+++ b/", "")
               if (from_file != to_file) moved_file = true
            }
            else if (diff_text != "__UNDEFINED__" && line.startsWith("-"))
               file_removed += 1
            else if (diff_text != "__UNDEFINED__" && line.startsWith("+"))
               file_added += 1 
            if (diff_text != "__UNDEFINED__" && line != null)
               //diff_text += line
               {}
        })
        this
    }

}
