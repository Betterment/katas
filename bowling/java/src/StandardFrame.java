public class StandardFrame {

    private int number;
    protected int throw1;
    protected int throw2;
    protected int numThrows;
    private StandardFrame nextFrame;

    public StandardFrame(int number) {

        this.number = number;
    }

    protected void gameRoll(Integer roll) {

        if (stillAcceptingThrows()) {
            roll(roll);
        } else {
            getNextFrame().gameRoll(roll);
        }
    }

    protected Integer gameScore() {

        if (isLastFrame()) {
            return score();
        }

        return score() + getNextFrame().gameScore();
    }

    protected Integer score() {

        if (isStrike()) {
            return throw1 + nextRollScore() + nextNextRollScore();
        }

        if (isSpare()) {
            return throw1 + throw2 + nextRollScore();
        }

        return throw1 + throw2;
    }

    private int nextRollScore() {

        if (getNextFrame() != null) {
            return getNextFrame().firstRoll();
        }

        return 0;
    }

    private int nextNextRollScore() {

        if (getNextFrame() != null && (getNextFrame().getNumThrows() == 2 || getNextFrame().getNumThrows() == 3)) {
            return getNextFrame().secondRoll();
        }

        if (getNextFrame() != null && getNextFrame().getNextFrame() != null) {
            return getNextFrame().getNextFrame().firstRoll();
        }

        return 0;
    }

    public Integer firstRoll() {

        return throw1;
    }

    public Integer secondRoll() {

        return throw2;
    }

    protected void roll(Integer roll) {

        if (numThrows == 0) {
            throw1 = roll;
        }

        if (numThrows == 1) {
            throw2 = roll;
        }

        numThrows++;

    }

    protected Boolean stillAcceptingThrows() {

        return !isStrike() && numThrows < 2;
    }

    protected Boolean isLastFrame() {

        return false;
    }

    protected Boolean isStrike() {

        return throw1 == 10;
    }

    protected Boolean isSpare() {

        return !isStrike() && throw1 + throw2 == 10;

    }

    protected StandardFrame getNextFrame() {

        if (nextFrame == null) {

            if (number == 9) {
                nextFrame = new FinalFrame();
            } else {
                nextFrame = new StandardFrame(number + 1);
            }

        }

        return nextFrame;
    }

    protected int getNumThrows() {

        return numThrows;
    }
}
