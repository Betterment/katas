import java.util.ArrayList;

public class Game {

	private int currFrameIndex;
	private Frame[] frames = new Frame[12];
	private ArrayList<ArrayList<Frame>> dependentFrames = new ArrayList<ArrayList<Frame>>(2);

	public Game() {
		for (int i = 0; i < frames.length; i++) {
			frames[i] = new Frame();
		}
		for (int i = 0; i < 2; i++) {
			dependentFrames.add(new ArrayList<Frame>());
		}
	}
    public void roll(int numberOfPins) {
    	Frame currFrame = frames[currFrameIndex];
    	currFrame.roll(numberOfPins);

    	// set the extras
		ArrayList<Frame> oneExtras = dependentFrames.get(0);
		for (Frame f : oneExtras) {
			f.addExtra(numberOfPins);
		}
		ArrayList<Frame> twoExtras = dependentFrames.get(1);
		for (Frame f : twoExtras) {
			f.addExtra(numberOfPins);
		}
		dependentFrames.set(0, twoExtras);
		dependentFrames.set(1, new ArrayList<Frame>());

    	if (currFrame.isFinished()) {
    		if (currFrame.isStrike()) {
    			dependentFrames.get(1).add(currFrame);
    		} else if (currFrame.isSpare()) {
    			dependentFrames.get(0).add(currFrame);
    		}
    		currFrameIndex++;
    	}
    }

    public int score() {
    	int score = 0;
    	for (int i = 0; i < 10; i++) {
    		score += frames[i].getScore();
    	}
        return score;
    }

}

class Frame {
	private int rollCount;
	private int score;

	public Frame() {
		rollCount = 0;
		score = 0;
	}

	public void roll(int numberOfPins) {
		score += numberOfPins;
		rollCount++;
	}

	public void addExtra(int extraScore) {
		score += extraScore;
	}

	public boolean isFinished() {
		if (rollCount > 1 || score > 9) {
			return true;
		}
		return false;
	}

	public int getScore() {
		return score;
	}

	public boolean isSpare() {
		if (score >= 10 && rollCount == 2) {
			return true;
		}
		return false;
	}

	public boolean isStrike() {
		if (score >= 10 && rollCount == 1) {
			return true;
		}
		return false;
	}
}
