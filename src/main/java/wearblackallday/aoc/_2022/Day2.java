package wearblackallday.aoc._2022;

import wearblackallday.aoc.Calendar;
import wearblackallday.aoc.common.Answer;

import java.util.Arrays;

public class Day2 extends Calendar.Day {

	@Override @Answer(14069)
	protected long partOne() {
		int[][] table = {
			{4, 8, 3},
			{1, 5, 9},
			{7, 2, 6},
		};

		return Arrays.stream(this.input)
			.mapToInt(line -> table[line.charAt(0) - 'A'][line.charAt(2) - 'X'])
			.sum();
	}

	@Override @Answer(12411)
	protected long partTwo() {
		int[][] table = {
			{3, 4, 8},
			{1, 5, 9},
			{2, 6, 2},
		};

		return Arrays.stream(this.input)
			.mapToInt(line -> table[line.charAt(0) - 'A'][line.charAt(2) - 'X'])
			.sum();
	}
}
