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

class PersonReport(var commits : List[GitCommit]) {

   def compute() : Iterator[Person] = {
       val peopleInfo : HashMap[String, Person] = new HashMap()
       commits.foreach(c => {
            if (! peopleInfo.contains(c.author)) {
                peopleInfo += (c.author -> new Person(c.author))
            }
            peopleInfo(c.author) += c
       })
       return peopleInfo.values
   }

   def csv_header() : String = {
       return "name,commits,added,removed,impact"
   }


}

