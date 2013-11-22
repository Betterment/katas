public class Game {

    private Roll firstRoll = null;
    private Roll currentRoll = null;
    private int currentFrame = 1;

    private final int LAST_FRAME = 10;
    private final int MAX_PINS = 10;

    public void roll(int numberOfPins) {

        Roll roll = new Roll(numberOfPins, currentFrame);
        if (firstRoll == null)
            firstRoll = roll;
        linkRolls(currentRoll, roll);
        currentRoll = roll;

        if (roll.endsFrame())
            currentFrame++;
    }

    public int score() {

        int score = 0;

        Roll currentRoll = firstRoll;

        while (currentRoll != null) {
            score += currentRoll.score();
            currentRoll = currentRoll.getNext();
        }

        return score;
    }

    private void linkRolls(Roll prev, Roll next) {

        next.setPrevious(prev);
        if (prev != null) {
            prev.setNext(next);
        }
    }

    private class Roll {

        public Roll(int pins, int frame) {

            this.pins = pins;
            this.frame = frame;
        }

        private int pins;
        private int frame;
        private Roll previous;
        private Roll next;

        public int score() {

            if (isBonusBall())
                return 0;

            if (isStrike()) {
                return pinsIncludingXNextRolls(2);
            } else if (isSpare()) {
                return pinsIncludingXNextRolls(1);
            } else {
                return pinsIncludingXNextRolls(0);
            }
        }

        private boolean isSpare() {

            if (previous == null)
                return false;

            // it's a spare if the previous roll was in this frame and the rolls add up to 10
            return previous.frame == frame && previous.getPins() + pins == MAX_PINS;
        }

        private boolean isStrike() {

            return pins == MAX_PINS;
        }

        // returns the pins from this roll plus the pins from the next i rolls
        public int pinsIncludingXNextRolls(int i) {

            if (i == 0 || next == null) {
                return pins;
            }

            return pins + next.pinsIncludingXNextRolls(i - 1);
        }

        public boolean endsFrame() {

            if (frame == LAST_FRAME) { // if we have two rolls that add to less than 10 or we have 3 rolls in the frame, end the last frame
                return previous.getFrame() == LAST_FRAME && previous.getPins() + pins < MAX_PINS || previous.getPrevious().getFrame() == LAST_FRAME;
            }

            // frame ends if we have two rolls in the frame or it's a strike
            return ((previous != null && previous.getFrame() == frame) || isStrike());
        }

        private boolean isBonusBall() {

            // it's a bonus ball if it's the last frame and we've had a strike in the frame already, or if it's the last frame and we've had a spare in the frame already 
            return frame == LAST_FRAME
                    && (previous.getFrame() == LAST_FRAME && (previous.isStrike() || previous.isSpare() || (previous.getPrevious().getFrame() == LAST_FRAME && previous
                            .getPrevious().isStrike())));
        }

        public int getPins() {

            return pins;
        }

        public int getFrame() {

            return frame;
        }

        public Roll getPrevious() {

            return previous;
        }

        public void setPrevious(Roll previous) {

            this.previous = previous;
        }

        public Roll getNext() {

            return next;
        }

        public void setNext(Roll next) {

            this.next = next;
        }
    }

}
