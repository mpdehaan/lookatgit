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

