require_relative '../game'

describe Frame do
    let(:frame) { Frame.new }
    subject { frame }

    it "has a score of zero" do
        frame.score.should == 0
    end

    it { should be_accepting_rolls }

    it "has a score of 5 with one roll" do
        frame.roll(5)
        frame.score.should == 5
    end

    it "is a strike with one 10" do
        frame.roll(10)
        frame.should be_strike
        frame.should_not be_spare
    end

    it "is a spare with two adding to 10" do
        frame.roll(4)
        frame.roll(6)
        frame.should be_spare
        frame.should_not be_strike
    end

    it "stops adding extra rolls when not a spare" do
        frame.roll(4)
        frame.roll(4)
        frame.roll(4)
        frame.score.should == 8
    end

    it "stops adding extra rolls after two when a spare" do
        frame.roll(8)
        frame.roll(2)
        frame.roll(4)
        frame.roll(4)
        frame.score.should == 14
    end

    it "stops adding extra rolls after two when a strike" do
        frame.roll(10)
        frame.roll(2)
        frame.roll(4)
        frame.roll(4)
        frame.score.should == 16
    end

    it "needs a new frame after a strike" do
        frame.roll(10)
        frame.should be_done
    end
end
