import java.util.ArrayList;
import java.util.List;

public class Game {

    private static final int MAX_FRAMES = 10;
    private List<Frame> frames;
    private int currentFrameNumber;

    public Game() {
        this.frames = new ArrayList<>();
        this.currentFrameNumber = 0;
    }

    public void roll(int numberOfPins) {
        Frame currentFrame = getCurrentFrame();

        if (currentFrame.isFull()) {
            throw new IllegalStateException("game is over, stop rolling, dummy.");
        }

        currentFrame.roll(numberOfPins);
    }

    private Frame getCurrentFrame() {

        if (currentFrameNumber+1 == MAX_FRAMES) {
            return this.frames.get(currentFrameNumber);
        }

        if (this.frames.size() == 0) {
            return createNewFrame(true);
        }

        Frame f = this.frames.get(currentFrameNumber);

        if (!f.isFull()) {
            return f;
        }

        return createNewFrame(false);
    }

    private Frame createNewFrame(boolean isFirst) {
        if (!isFirst) {
            currentFrameNumber = currentFrameNumber + 1;
        }

        Frame newFrame = new Frame(currentFrameNumber+1);
        this.frames.add(newFrame);

        return newFrame;
    }

    public int score() {
        int sum = 0;

        for (int i = 0; i < this.frames.size(); i++) {

            final Frame currentFrame = this.frames.get(i);

            if (currentFrame.isSpare()) {
                final Frame nextFrame = this.frames.get(i+1);
                if (nextFrame == null) {
                    continue;
                }
                sum += currentFrame.value(nextFrame);
            } else if (currentFrame.isStrike()) {
                final Frame nextFrame = this.frames.get(i+1);
                if (nextFrame == null) {
                    continue;
                }
                if (!nextFrame.isStrike()) {
                    sum += currentFrame.value(nextFrame);
                } else {
                    final Frame nextNextFrame = this.frames.get(i+2);
                    if (nextNextFrame == null) {
                        continue;
                    }
                    sum += currentFrame.value(nextFrame) + nextNextFrame.pinsForRoll(0);
                }
            } else {
                sum += currentFrame.value(null);
            }
        }

        return sum;
    }

}
