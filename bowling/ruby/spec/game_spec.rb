require_relative '../game'

describe Game, '#score' do

    before(:each) do
        @game = Game.new
    end

    it 'should return 0 for a gutter game' do

        20.times { @game.roll(0) }

        @game.score().should eq(0)
    end

    it 'should return 20 for 1 pin on each roll' do

        20.times {
            @game.roll(1)
        }

        @game.score().should eq(20)
    end

    it 'should return 29 for a SPARE in the FIRST frame, one pin down in each other roll' do

        @game.roll(5)
        @game.roll(5)

        18.times {
            @game.roll(1)
        }

        @game.score().should eq(29)
    end

    it 'should return 29 for a SPARE in the LAST frame, one pin down in each other roll' do

        18.times {
            @game.roll(1)
        }

        @game.roll(5)
        @game.roll(5)

        @game.roll(1)

        @game.score().should eq(29)
    end

    it 'should return 30 for a STRIKE in the LAST frame, one pin down in each other roll' do

        @game.roll(10)

        18.times {
            @game.roll(1)
        }

        @game.score().should eq(30)
    end

    it 'should return 30 for a STRIKE in the LAST frame, one pin down in each other roll' do

        18.times {
            @game.roll(1)
        }

        @game.roll(10)

        @game.roll(1)

        @game.roll(1)

        @game.score().should eq(30)
    end

    it 'should return 300 for a perfect game' do

        12.times {
            @game.roll(10)
        }

        @game.score().should eq(300)
    end

end
