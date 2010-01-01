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

import scala.collection.mutable.HashSet

class Person(var name : String) {

   val commits : HashSet[GitCommit]  = new HashSet[GitCommit]
   var added   : Int             = 0
   var removed : Int             = 0
   val files   : HashSet[String] = new HashSet[String]
   var impact  : Int             = 0

   def +=(commit : GitCommit) : Unit = {
       commits   += commit
       for(d <- commit.diffs) {
           added     += d.added
           removed   += d.removed
           files     += d.path
       }
       impact = added + removed
   }

   def to_csv() : String = {
       return List(name,commits.size,added,removed,impact).mkString(",")
   }

}

