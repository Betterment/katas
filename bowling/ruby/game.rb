require './frame'
require './array'

class Game

    attr_reader :frames

    def initialize
        @frames = []
    end

    def new_frame_needed?
        frames.empty? || frames.length < 10 && frames.last.done?
    end

    def roll(pins_felled)
        frames << Frame.new if new_frame_needed?
        frames.each do |f|
            f.roll(pins_felled)
        end
    end

    def score
        frames.map(&:score).sum
    end
end
