import java.util.ArrayList;
import java.util.List;

/**
 * TODO: documentation
 *
 * @author sam
 * @since 11/20/13 8:24 PM
 */
public class Frame {

    private final int frameNumber;
    private List<Integer> rolls;

    public Frame(int frameNumber) {
        this.frameNumber = frameNumber;
        this.rolls = new ArrayList<>();
    }

    public boolean isFull() {
        if (frameNumber < 10)
            return this.rolls.size() == 1 && this.sumRolls() == 10 || this.rolls.size() == 2;
        else
            return this.rolls.size() == 2 && this.sumRolls() < 10;
    }

    public void roll(int numberOfPins) {
        this.rolls.add(numberOfPins);
    }

    public boolean isStrike() {
        return this.rolls.size() == 1 && this.sumRolls() == 10;
    }

    public boolean isSpare() {
        return this.rolls.size() == 2 && sumRolls() == 10;
    }

    public int value(final Frame nextFrame) {
        if (isStrike())
            return 10 + nextFrame.pinsForRoll(0) + nextFrame.pinsForRoll(1);
        if (isSpare())
            return 10 + nextFrame.pinsForRoll(0);
        else
            return sumRolls();
    }

    public int pinsForRoll(int rollNumber) {
        if (this.rolls.size() >= rollNumber+1) {
            return this.rolls.get(rollNumber);
        } else {
            return 0;
        }
    }

    private int sumRolls() {
        int sum = 0;

        for(int i = 0; i < rolls.size(); i++) {
            sum += rolls.get(i);
        }

        return sum;
    }
}
