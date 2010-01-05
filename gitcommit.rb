require 'time'
require 'open3'

require 'gitchange'

# represents a single commit to the source repo
class GitCommit

   attr_reader   :hash
   attr_accessor :comments
   attr_accessor :author
   attr_accessor :time
   attr_accessor :changes

   # hash here is a the git commit ID as a hex string
   def initialize(hash)
       @hash     = hash
       @comments = []
       @author   = nil
       @time     = nil
       @changes  = []
   end

   # store the time the commit was made as a Time object
   def time=(time)
       @time = Time.parse(time)
   end

   # return a printable representation of the commit for debug purposes
   def to_s()
       filenames = @changes.collect { |f| f.filename }.join(",")
       "#{@hash} | #{@author} | #{@time} | #{filenames}"
   end

end
