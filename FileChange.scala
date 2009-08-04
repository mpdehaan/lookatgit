class FileChange(var commit  : GitCommit,
                 var path    : String, 
                 var move    : Boolean, 
                 var added   : Int, 
                 var deleted : Int,
                 var diff    : String) {

}

