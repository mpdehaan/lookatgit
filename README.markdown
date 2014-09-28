LookAtGit
=========

LookAtGit is a program to extract commit statistics from a git source control repo.
It may also be used with other repos provided you first convert them to git, for example, using git-svn.

Installation
============

* install ruby

Instructions
============

To see what options are available:

    $ ruby lookatgit.rb --help

To scan the current working directory:

    $ ruby lookatgit.rb 

To scan a specific repo:

    $ ruby lookatgit.rb --repo /path/to/git/repo

To generate the top contributors report

    $ ruby lookatgit.rb --topcontrib

To generate the same report, with headers so humans can read it

    $ ruby lookatgit.rb --topcontrib --headers

To generate the top contributors report AND the top files report

    $ ruby lookatgit.rb --topcontrib --topfiles

To sort a report by a different set of criteria (as listed in the headers)

    $ ruby lookatgit.rb --topfiles --criteria lines_removed

To only show 50 records in two reports

    $ ruby lookatgit.rb --topcontrib --headers --limit 50


How The App Works
=================

Look At Git scans git repos first, and then generates reports.
While scanning, it comes up with the list of changes that was made to each file and who made them.
When reporting, it goes back and then keeps track of what a single person did across the history.
It will then generate the list of all changes associated with a given file path.
Once we have that aggregrate data, we can then generate more complex reports with these aggregates.

To Do 
=====

* std-dev between commits
* We have a most active file report, aggregate into most active directories?
* Attempt to combine usernames by ignoring email addresses or other fuzzy logic (must be optional)
* Aggregrate stats.  What are the average stats for a project, so we can compare projects?
* For larger projects, marshall objects to disk so we can run reports w/o rescanning
* Support input of a regex to split output into people matching and it NOT matching it, ex:
  Contributors that are not part of organization XYZ.
* Be able to print out the ranked list of contributors for each file.  Like git blame but with historical awareness.

Contributions
=============

Ideas for expanding lookatgit and generating reports are welcome.  Send in a patch!
