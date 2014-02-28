require 'set'

class SudokuSolver

    FULL_SET = [1,2,3,4,5,6,7,8,9].to_set

    attr_accessor :grid, :solution

    def initialize(filename)
        File.open(filename) do |f|
            self.grid = Grid.new(f.gets.chomp)
            self.solution = Grid.new(f.gets.chomp)
        end
    end

    def solve
        while grid.incomplete? do
            (Grid::DIMENSION ** 2).times do |i|
                candidates = possibles(i)
                if candidates.length == 1
                    grid.fill(i, candidates.first)
                else
                    puts "#{candidates.length} possibilities for #{i} (#{candidates.inspect})"
                end
            end
        end
        puts grid.dump
        puts solution.dump
        puts grid == solution ? "YAY" : "BOO"
    end

    def remaining_numbers(group)
        FULL_SET - group.to_set
    end

    def possibles(square_index)
        return Set.new() if grid.filled?(square_index)
        remaining_numbers(grid.column(square_index)) & remaining_numbers(grid.row(square_index)) & remaining_numbers(grid.square(square_index))
    end
end

class Grid
    DIMENSION = 9
    SQUARE_DIMENSION = 3

    def initialize(raw_grid)
        @contents = raw_grid.split("").map{|char| char == "." ? nil : char.to_i }
        @unfilled = @contents.select{|i| i == nil }.count
    end

    def dump
        @contents.map{|char| char.nil? ? "." : char.to_s }.join
    end

    def ==(other)
        self.dump == other.dump
    end

    def column(square_index)
        x_offset = square_index % DIMENSION
        @contents.each_slice(DIMENSION).map {|row| row[x_offset] }.to_set.delete(nil)
    end

    def row(square_index)
        y_offset = square_index / DIMENSION
        @contents[DIMENSION * y_offset, DIMENSION].to_set.delete(nil)
    end

    def square(square_index)
        square_x = (square_index % DIMENSION) / SQUARE_DIMENSION
        square_y = square_index / DIMENSION / SQUARE_DIMENSION
        @contents.each_slice(DIMENSION).to_a[square_y * SQUARE_DIMENSION, (square_y + 1) * SQUARE_DIMENSION].map do |row|
            row[square_x * SQUARE_DIMENSION, SQUARE_DIMENSION]
        end.flatten.to_set.delete(nil)
    end

    def fill(index, value)
        puts "filling #{index} with #{value}"
        @unfilled -= 1
        @contents[index] = value
    end

    def incomplete?
        @unfilled > 0
    end

    def filled?(i)
        !!@contents[i]
    end
end
