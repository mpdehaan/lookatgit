import java.io._

object App {
   def main(args: Array[String]) {
      if (args.length < 1) {
          println("Must specify a local git repo path")
      }
      else {
          val s = new Scanner(args(0))
          s.scan()
      }
   }
}

