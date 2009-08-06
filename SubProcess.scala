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

import java.io._

class SubProcess() {

    def run(cmd : String, dir : String) : Tuple2[Integer, List[String]] = {
       val environ : Array[String] = new Array[String](0)
       val proc = Runtime.getRuntime.exec(cmd, environ, new File(dir))
       val reader = new BufferedReader(new InputStreamReader(proc.getInputStream))
       var lines : List[String] = Nil
       var line : String = null
       do {
           line = reader.readLine
           lines = line :: lines
       } while (line != null)
       proc.waitFor
       reader.close
       (proc.exitValue, lines.reverse)
    }
}

