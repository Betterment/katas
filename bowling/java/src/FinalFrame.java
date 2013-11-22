public class FinalFrame extends StandardFrame {

    private int throw3;

    public FinalFrame() {

        super(10);
    }

    protected void roll(Integer roll) {

        if (numThrows == 0) {
            throw1 = roll;
        }

        if (numThrows == 1) {
            throw2 = roll;
        }

        if (numThrows == 2) {
            throw3 = roll;
        }

        numThrows++;
    }

    protected StandardFrame getNextFrame() {

        return null;
    }

    protected Boolean isLastFrame() {

        return true;
    }

    protected Integer score() {

        return throw1 + throw2 + throw3;
    }

    protected Boolean stillAcceptingThrows() {

        if (numThrows == 0 || numThrows == 1) {
            return true;
        }

        if (numThrows == 2 && score() == 10) {
            return true;
        }

        if (numThrows == 3) {
            return true;
        }

        return false;
    }

    protected void gameRoll(Integer roll) {

        roll(roll);
    }
}
