require 'open3'

require 'gitcommit'
require 'gitauthor'

# runs through a git repo and records the individual commits for later usage with Reporter
class Scanner
   
   attr_reader :repo
   attr_reader :commit_ct
   attr_reader :commits
   
   # constructed with the path to a git repo
   # optional which specifies a git-revision range, ex: "HEAD..HEAD~50"
   def initialize(repo, which=nil)
       @repo = repo
       @commits = []
       @commit_ct = 0
       @which = which
       raise "cannot execute /usr/bin/git" unless File.executable?("/usr/bin/git")
       raise "#{repo} is not a git repo" unless File.directory?("#{repo}/.git")
       Dir.chdir(@repo) do 
           puts "scanning..." if @@options.verbose
           pre_scan()
           puts "processing #{@commit_ct} commits..." if @@options.verbose
           log_scan()
           puts "generating report..." if @@options.verbose
       end
   end

   # count how many commits we have to process
   def pre_scan()
       Open3.popen3("git log --pretty=oneline") do |stdin,stdout,stderr| 
           stdout.each_line do |commit|
               (hash, comment) = commit.split(' ', 1)
               @commit_ct += 1 
           end
       end
   end 
       
   # scan the git logs 
   def log_scan()
       commit = nil
       cmd = "git log --numstat #{@which}"
       puts cmd if @@options.verbose
       Open3.popen3(cmd) do |stdin,stdout,stderr|
           stdout.each_line do |line|
              if line =~/^commit\s*(.*)/
                  @commits << commit unless commit.nil?
                  commit = GitCommit.new($1)
              elsif line =~ /^Author:\s*(.+)/
                  commit.author = GitAuthor.new($1)
              elsif line =~ /^Date:\s*(.+)/
                  commit.time = $1
              elsif line =~ /^(\d+)\s+(\d+)\s+(.+)/
                  commit.changes << GitChange.new(commit,$3,$1,$2)
              elsif line =~ /^\s*(.+)/
                  commit.comments << $1 
              end
           end
       end
       @commits << commit unless commit.nil?
   end 

end

