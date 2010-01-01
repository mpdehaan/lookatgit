require 'time'
require 'open3'

require 'gitfile'

class GitCommit

   attr_reader   :hash
   attr_accessor :comments
   attr_accessor :author
   attr_accessor :time
   attr_accessor :files

   def initialize(hash)
       @hash     = hash
       @comments = []
       @author   = nil
       @time     = nil
       @files    = []
   end

   def time=(time)
       time = Time.parse(time) unless time.is_a?(Time)
       @time = time
   end

   def to_s()
       filenames = @files.collect { |f| f.filename }.join(",")
       "#{@hash} | #{@author} | #{@time} | #{filenames}"
   end

end
