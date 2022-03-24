package wearblackallday.aoc._2021;

import wearblackallday.aoc.common.Answer;

public class Day17 extends Calendar.Day {
	private final int[] targetArea = this.parseInts(this.input[0].substring(15).split("[.]+|,\sy="));

	@Override @Answer(5253)
	protected long partOne() {
		int maxHeight = 0;
		for(int x = 0; x < 1000; x++) {
			for(int y = 0; y < 1000; y++) {
				maxHeight = Math.max(maxHeight, this.highestPoint(0, 0, x, y, 0));
			}
		}
		return maxHeight;
	}

	@Override @Answer(1770)
	protected long partTwo() {
		int startPositions = 0;
		for(int x = 0; x < 1000; x++) {
			for(int y = -1000; y < 1000; y++) {
				if(this.hitsTarget(x, y)) startPositions++;
			}
		}
		return startPositions;
	}

	private int highestPoint(int xPos, int yPos, int xVel, int yVel, int maxHeight) {
		if(yPos < this.targetArea[2]) return -1;
		if(this.inTarget(xPos, yPos)) return maxHeight;

		return this.highestPoint(xPos + xVel, yPos + yVel, Math.max(0, xVel - 1), yVel - 1, Math.max(maxHeight, yPos));
	}

	private boolean hitsTarget(int xVel, int yVel) {
		int xPos = 0, yPos = 0;
		while(yPos > this.targetArea[2]) {
			xPos += xVel;
			yPos += yVel;
			xVel = Math.max(0, xVel - 1);
			yVel--;
			if(this.inTarget(xPos, yPos)) return true;
		}
		return false;
	}

	private boolean inTarget(int x, int y) {
		return x >= this.targetArea[0] && x <= this.targetArea[1] &&
			   y >= this.targetArea[2] && y <= this.targetArea[3];
	}
}
