package wearblackallday.aoc._2021;

import wearblackallday.aoc.Calendar;
import wearblackallday.aoc.common.Answer;

public class Day1 extends Calendar.Day {
	private final int[] data = this.parseInts(this.input);

	@Override @Answer(1288)
	protected long partOne() {
		int measurements = 0, prev = 0;

		for(int value : this.data) {
			if(value > prev) measurements++;
			prev = value;
		}
		return --measurements;
	}

	@Override @Answer(1311)
	protected long partTwo() {
		int measurements = 0, prevTriple = 0;

		for(int i = 0; i < this.input.length - 2; i++) {
			int currentTriple = this.getTripleSum(i);
			if(currentTriple > prevTriple) measurements++;
			prevTriple = currentTriple;
		}
		return --measurements;
	}

	private int getTripleSum(int index) {
		return this.data[index]
			 + this.data[++index]
			 + this.data[++index];
	}
}