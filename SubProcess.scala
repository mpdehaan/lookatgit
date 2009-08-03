import java.io._

class SubProcess() {

    def run(cmd : String) : Tuple2[Integer, List[String]] = {
       val proc = Runtime.getRuntime.exec(cmd)
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

