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
