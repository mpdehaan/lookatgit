# various extensions to built in classes and other useful utility functions

class Array

   # show the first N items in a collection
   # FIXME: probably can be rewritten to just use slice?
   def each_with_limit(limit)
       each do |item|
          unless limit.nil?
              limit-=1
              return nil if limit <= 0
          end
          yield item
       end
   end

   # average a numeric collection
   def mean()
       inject(0) { |sum,n| sum+=n } / length
   end

end
