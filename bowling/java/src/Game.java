public class Game {

    private StandardFrame firstFrame = new StandardFrame(1);

    public void roll(int numberOfPins) {

        firstFrame.gameRoll(numberOfPins);
    }

    public int score() {

        return firstFrame.gameScore();
    }

}
