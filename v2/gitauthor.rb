class GitAuthor

   attr_reader   :name
   attr_reader   :changes
   attr_reader   :commits
   attr_reader   :lines_added
   attr_reader   :lines_removed
   attr_reader   :files

   def initialize(name)
       @name    = name
       @changes = []
       @commits = {}
       @files   = {}
       @lines_added = 0
       @lines_removed = 0
   end

   def record_change(change, fileobj)
       unless @files.has_key?(fileobj.filename)
          @files[fileobj.filename] = fileobj
       end
       unless @commits.has_key?(change.commit.hash)
          @commits[change.commit.hash] = change.commit
       end
       @changes                     << change
       @lines_added                 += change.lines_added
       @lines_removed               += change.lines_removed
   end

   def lines_changed()
       lines_added + lines_removed
   end

   def files_ct()
       @files.length()
   end

   def change_ct()
       @changes.length()
   end

   def commit_ct()
       @commits.length()
   end

   def to_s()
       "#{@name} | #{lines_added} added, #{lines_removed} removed | #{change_ct} changes, #{files_ct} files"
   end

   #
   # FIXME: add methods: top_files_edited_by_commits(limit_count), top_files_edited_by_lines(limit_ct) ???
   #

end

