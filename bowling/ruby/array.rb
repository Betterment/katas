class Array
    def sum
        inject(0) {|sum,v| sum + v}
    end
end
