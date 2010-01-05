# this is the main entry point for the lookatgit program
#
# GPLv2.  for license information, see COPYING
#
# Michael DeHaan, <michael.dehaan@gmail.com>

require 'optparse'
require 'ostruct'

require 'scanner'
require 'reporter'

@@options         = OpenStruct.new()
@@options.verbose = false
@@options.repo    = Dir.getwd() 
@@options.header  = false
@@options.top_contributors_report = false
@@options.top_files_report = false
@@options.sort    = nil
@@options.limit   = nil
@@options.which   = nil

# FIXME: clean up optparse usage
ARGV.options do |opts|
    opts.banner = "Usage: lookatgit.rb <options>"
    opts.on("-v", "--verbose", "Run verbosely") { |v| @@options.verbose = v }
    opts.on("-h", "--header", "Add column headers") { |h| @@options.header  = h }
    opts.on("-r", "--repo REPO", "Scan this repo") { |r| @@options.repo    = r }
    opts.on("-T", "--contrib", "Generate top contributor report") { |T| @@options.top_contributors_report = T }
    opts.on("-F", "--files", "Generate top files report") { |F| @@options.top_files_report = F }
    opts.on("-s", "--sort SORT", "Sort by field") { |s| @@options.sort = s }
    opts.on("-l", "--limit LIMIT", "Limit reports to X records") { |l| @@options.limit = l.to_i }
    opts.on("-w", "--which WHICH", "Git version range spec, ex: 'HEAD HEAD~500") { |w| @@options.which = w }
    opts.separator("")
    opts.on_tail("-h","--help","Show this help message") { puts opts; exit }
    opts.parse!
end

unless @@options.top_contributors_report or @@options.top_files_report
    puts "Must specify a report type, try -T and/or -F"
    exit 1
end

# FIXME: scan a list of repos from a configuration file?
# FIXME: convert globals into parameters
scanner = Scanner.new(@@options.repo, @@options.which)
reporter = Reporter.new(scanner.commits)
reporter.top_contributors_report() if @@options.top_contributors_report
reporter.top_files_report() if @@options.top_files_report
