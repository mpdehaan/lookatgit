require 'optparse'
require 'ostruct'
#require 'dir'
require 'scanner'

@@options         = OpenStruct.new()
@@options.verbose = false
@@options.repo    = Dir.getwd() 

ARGV.options do |opts|
    opts.banner = "Usage: lookatgit.rb <options>"
    opts.on("-v", "--verbose",   "Run verbosely")  { |v| @@options.verbose = v }
    opts.on("-r", "--repo REPO", "Scan this repo") { |r| @@options.repo    = r }
    opts.separator("")
    opts.on_tail("-h","--help","Show this help message") { puts opts; exit }
    opts.parse!
end

puts @@options.verbose
puts @@options.repo

scanner = Scanner.new(@@options.repo)
puts scanner.generate_report()
