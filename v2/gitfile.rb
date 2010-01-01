class GitFile

   attr_reader   :filename
   attr_reader   :changes
   attr_reader   :lines_added
   attr_reader   :lines_removed
   attr_reader   :commits

   def initialize(filename)
       @filename = filename
       @changes  = []
       @authors  = {}
       @commits  = {}
       @lines_added = 0
       @lines_removed = 0
   end

   def record_change(change)
       @changes                     << change
       @lines_added                 += change.lines_added
       @lines_removed               += change.lines_removed
       @authors[change.author.name] = change.author
       unless @commits.has_key?(change.commit.hash)
           @commits[change.commit.hash] = change.commit
       end
   end

   def author_ct()
       @authors.length()
   end

   def commit_ct()
       @commits.values.length()
   end

   def change_ct()
       @changes.length()
   end

   def lines_changed()
       lines_added + lines_removed
   end

   def to_s()
       "#{filename} | #{lines_added} added, #{lines_removed} removed, #{change_ct} times, #{author_ct} authors"
   end

   #
   #  FIXME: add methods:  top_authors_by_commit(limit_ct), top_authors_by_lines(limit_ct)
   #

end

