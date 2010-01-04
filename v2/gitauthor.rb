require 'utils'

# Represents a contributor to a project
class GitAuthor

   attr_reader   :name
   attr_reader   :changes
   attr_reader   :commits
   attr_reader   :lines_added
   attr_reader   :lines_removed
   attr_reader   :files

   # constructed with the author's name as a string
   #     FIXME: this should parse into name/email/domain
   def initialize(name)
       @name    = name
       @changes = []
       @commits = {}
       @files   = {}
       @lines_added = 0
       @lines_removed = 0
       @now = Time.new()
   end

   # associate a file change with the author who made it
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

   # how many lines has the author changed total?
   def lines_changed()
       lines_added + lines_removed
   end

   # how many files has the author edited?
   def files_ct()
       @files.length()
   end

   # how many times has the author changed a file?
   def change_ct()
       @changes.length()
   end

   # how many commits has the author made?
   def commit_ct()
       @commits.length()
   end

   # a printable representation of the author
   def to_s()
       "#{@name} | #{lines_added} added, #{lines_removed} removed | #{change_ct} changes, #{files_ct} files"
   end

   # how many days between commits?
   def commit_frequency()
       commits = @commits.values.sort { |a,b| a.time <=> b.time }
       return 0 if commits.length < 2
       return ("%0.2f" % ((@now.to_f - commits.first.time.to_f) / (commits.length * 24 * 60 * 60))).to_f
   end
   
   # how many days since last commit?
   def awol_time()
       commits = @commits.values.sort { |a,b| a.time <=> b.time }
       return ("%0.2f" % ((@now.to_f - commits.last.time.to_f) / (24 * 60 * 60))).to_f
   end

end

