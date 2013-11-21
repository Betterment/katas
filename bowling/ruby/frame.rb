class Frame

    attr_reader :rolls

    def initialize
        @rolls = []
    end

    def roll_count
        @rolls.size
    end

    def roll(pins_felled)
        self.rolls << pins_felled if accepting_rolls?
    end

    def strike?
        rolls.first == 10
    end

    def spare?
        !strike? && rolls.take(2).sum == 10
    end

    def score
        rolls.sum
    end

    def done?
        strike? || roll_count > 1
    end

    private

    def expected_rolls
        (strike? || spare?) ? 3 : 2
    end

    def accepting_rolls?
         roll_count < expected_rolls
    end
end
