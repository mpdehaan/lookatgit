class FileChange(var commit  : GitCommit,
                 var path    : String, 
                 var move    : Boolean, 
                 var added   : Int, 
                 var deleted : Int,
                 var diff    : String) {

   def print() : Unit = {
       println("   filemod = " + path)
       println("         + = " + added)
       println("         - = " + deleted)
       println("        mv = " + move)
       println("        by = " + commit.author) 
   }

}

