# represents a file in a git repo with statistics
class GitFile

   attr_reader   :filename
   attr_reader   :changes
   attr_reader   :lines_added
   attr_reader   :lines_removed
   attr_reader   :commits

   # constructed with the filename as a string, other fields will be populated later with record_change
   def initialize(filename)
       @filename = filename
       @changes  = []
       @authors  = {}
       @commits  = {}
       @lines_added = 0
       @lines_removed = 0
   end

   # associate a GitChange with the file and update the stats
   def record_change(change)
       @changes                     << change
       @lines_added                 += change.lines_added
       @lines_removed               += change.lines_removed
       @authors[change.author.name] = change.author
       unless @commits.has_key?(change.commit.hash)
           @commits[change.commit.hash] = change.commit
       end
   end

   # how many authors have edited this file?
   def author_ct()
       @authors.length()
   end

   # how many commits have affected this file?
   def commit_ct()
       @commits.values.length()
   end

   # how many times has this file been edited?
   # FIXME: this is redundant, and should be the same as commit_ct
   def change_ct()
       @changes.length()
   end

   # how much line activity has occured in this file?
   # FIXME: a statistic about frequency of activity may be interesting
   def lines_changed()
       lines_added + lines_removed
   end

   # return a string representation of the file for debug purposes
   def to_s()
       "#{filename} | #{lines_added} added, #{lines_removed} removed, #{change_ct} times, #{author_ct} authors"
   end

   #
   #  FIXME: add methods:  top_authors_by_commit(limit_ct), top_authors_by_lines(limit_ct)
   #

end

