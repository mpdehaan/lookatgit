class Diff(var commit  : GitCommit,
           var path    : String, 
           var move    : Boolean, 
           var added   : Int, 
           var removed : Int,
           var text    : String) {

   def print() : Unit = {
       println("   filemod = " + path)
       println("         + = " + added)
       println("         - = " + removed)
       println("        mv = " + move)
       println("        by = " + commit.author) 
   }

}

