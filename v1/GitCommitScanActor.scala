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

import scala.actors._

// runs "git show" deep scans in the background
// for greater parallelism

case class DeepScanCommit(gc: GitCommit, respondTo : Actor)
case class DeepScanCommitResult(gc: GitCommit)
case class DeepScansComplete()

class GitCommitScanActor() extends Actor {
    def act() {
        Actor.loop {
            react {
                case DeepScansComplete() =>
                   exit()
                case DeepScanCommit(gc, actor) =>
                   actor ! DeepScanCommitResult(gc.deepScan())
            }
        }
    }
}


