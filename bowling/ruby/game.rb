class Game

    def initialize
        @frame = 1
        @frame_roll = 0
        @frame_score = 0
        @score = 0
        @strikes = []
        @spare = false
        @bonus = false
    end

    def roll(numberOfPins)
        @score += numberOfPins
        @frame_score += numberOfPins

        @strikes.select { |strike| strike.active? }.each do |strike|
            strike.use
            @score += numberOfPins
        end        
        
        unless bonus?
            handle_spare(numberOfPins)

            if @frame_score == 10
                @frame_roll == 0 ? strike! : spare!
                return
            end

            if @frame_roll < 1
                @frame_roll += 1
            else
                next_frame
            end
        end
    end

    def score
        @score
    end

    private

        def spare?
            @spare
        end

        def bonus?
            @bonus
        end

        def spare!
            @spare = true
        end

        def handle_spare(numberOfPins)
            if spare?
                @score += numberOfPins
                @spare = false
            end
        end

        def next_frame
            if @frame >= 9
                @bonus = true
            else
                @frame_roll = 0
                @frame_score = 0
                @frame += 1
            end
        end

        def reset_strike
            @rolls_since_strike = 0
            @strike = false
        end

        def strike!
            @strikes.push(Strike.new) unless bonus?
            next_frame
        end
end

class Strike

    def initialize
        @uses = 0
        @active = true
    end

    def use
        if @uses == 1
            @active = false
        end
        @uses += 1
    end

    def active?
        @active
    end
end
