require 'time'
require 'open3'

class GitChange

   attr_reader   :commit
   attr_reader   :filename
   attr_reader   :lines_added
   attr_reader   :lines_removed
   attr_reader   :author

   def initialize(commit, filename, lines_added, lines_removed)
       @commit = commit
       @author = commit.author
       @filename = filename
       @lines_added = lines_added.to_i()
       @lines_removed = lines_removed.to_i()
   end

   def to_s()
       print "#{@filename} | #{@lines_added} added, #{@lines_removed} removed | by #{@author}"
   end

end
