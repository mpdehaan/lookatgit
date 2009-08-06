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


