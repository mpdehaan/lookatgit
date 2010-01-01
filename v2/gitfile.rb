require 'time'
require 'open3'

class GitFile

   attr_reader   :commit
   attr_reader   :filename
   attr_reader   :lines_added
   attr_reader   :lines_removed

   def initialize(commit, filename, lines_added, lines_removed)
       @commit = commit
       @filename = filename
       @lines_added = lines_added
       @lines_removed = lines_removed
   end

   def to_s()
       print "#{@filename} | #{@lines_added} added, #{@lines_removed} removed | by #{@commit.author}"
   end

end
