class Game

    FRAMES = 10

    def initialize
        @rolls = []
        @frame = 1
        @try = 0
        @total_pins = 0
        @spare_last_frame = false
        @strike_last_frame = false
    end

    def roll(numberOfPins)
        @total_pins += numberOfPins
        @rolls << numberOfPins

        # add again after a spare
        if @spare_last_frame && @frame <= FRAMES
            @total_pins += this_roll
            @spare_last_frame = false # reset state
        elsif @strike_last_frame && @frame <= FRAMES
            @total_pins += this_roll
            if second_try?
                @strike_last_frame = false
            end
        end

        # was this frame a spare?
        if first_try? && this_roll == 10
            @strike_last_frame = true
            @try = 1 # move to next frame
        elsif second_try? && (last_roll + this_roll) == 10
            @spare_last_frame = true
        end

        # update game state
        @try = (@try == 0) ? 1 : 0
        @frame+=1 if @try == 0
    end

    def second_try?
        @try == 1
    end

    def first_try?
        @try == 0
    end

    def this_roll
        @rolls[-1]
    end

    def last_roll
        @rolls[-2]
    end

    def score
        @total_pins
    end
end
