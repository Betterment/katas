require_relative '../sudoku_solver'

describe Grid do
    it "can get the square contents for top left corner" do
        grid = Grid.new("123......456......789......" + (9 * 6).times.map{"."}.join(""))
        grid.square(0).should == "123456789".split("").map(&:to_i).to_set
    end

    it "can get the top row" do
        grid = Grid.new("123456789" + (9 * 8).times.map{"."}.join(""))
        grid.row(0).should == "123456789".split("").map(&:to_i).to_set
    end

    it "can get the left column" do
        grid = Grid.new([1,2,3,4,5,6,7,8,9].map {|i| "#{i}........" }.join(""))
        grid.column(0).should == "123456789".split("").map(&:to_i).to_set
    end
end

describe SudokuSolver do

    subject { SudokuSolver.new("../puzzles/1.txt") }

    describe "#remaining_numbers" do
        it "returns remaining numbers from set" do
            subject.remaining_numbers([1,2,3]).should == Set.new([4,5,6,7,8,9])
        end
    end

    describe "#grid" do
        it "has the right column" do
            subject.grid.column(1).should == Set.new([3,2])
        end

        it "has the right row" do
            subject.grid.row(1).should == Set.new([4,8,5])
        end

        it "has the right square" do
            subject.grid.square(1).should == Set.new([4, 3])
        end

        it "has the right column for 80" do
            subject.grid.column(80).should == Set.new([5])
        end

        it "has the right row for 80" do
            subject.grid.row(80).should == Set.new([1,4])
        end

        it "has the right square for 80" do
            subject.grid.square(80).should == Set.new([7])
        end
    end

    describe "#possibles" do
        it "returns nothing for a filled square" do
            subject.possibles(0).should == Set.new([])
        end

        it "returns correct value for square 1" do
            subject.possibles(1).should == Set.new([1,6,7,9])
        end

        it "returns correct value for square 1 after filling square 2" do
            subject.grid.fill(2, 7)
            subject.possibles(1).should == Set.new([1,6,9])
        end
    end
end
