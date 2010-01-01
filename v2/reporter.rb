require 'time'
require 'open3'

class Reporter

   def initialize(commits)
       @commits = commits
       calculate()
   end

   def calculate()
       puts "thinking..."
   end

   def report(format="csv")
       raise "unsupported format #{format}" unless format == "csv"
   end

end
