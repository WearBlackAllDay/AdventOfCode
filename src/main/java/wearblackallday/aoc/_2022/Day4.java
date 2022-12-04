package wearblackallday.aoc._2022;

import wearblackallday.aoc.Calendar;
import wearblackallday.aoc.common.Answer;

import java.util.Arrays;

public class Day4 extends Calendar.Day {

	private final int[][] assignments = Arrays.stream(this.input)
		.map(line -> this.parseInts(line.split("[,\\-]")))
		.toArray(int[][]::new);

	@Override @Answer(471)
	protected long partOne() {
		int fullyContained = 0;

		for(int[] sections : this.assignments) {
			if(sections[0] <= sections[2] && sections[1] >= sections[3]) fullyContained++;
			else if(sections[0] >= sections[2] && sections[1] <= sections[3]) fullyContained++;
		}

		return fullyContained;
	}

	@Override @Answer(888)
	protected long partTwo() {
		int partiallyContained = 0;

		for(int[] sections : this.assignments) {
			if(inRange(sections[0], sections[2], sections[3])) partiallyContained++;
			else if(inRange(sections[1], sections[2], sections[3])) partiallyContained++;
			else if(inRange(sections[2], sections[0], sections[1])) partiallyContained++;
			else if(inRange(sections[3], sections[0], sections[1])) partiallyContained++;
		}

		return partiallyContained;
	}

	private static boolean inRange(int i, int min, int max) {
		return i >= min && i <= max;
	}
}
