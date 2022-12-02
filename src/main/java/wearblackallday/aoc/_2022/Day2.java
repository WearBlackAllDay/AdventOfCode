package wearblackallday.aoc._2022;

import wearblackallday.aoc.Calendar;
import wearblackallday.aoc.common.Answer;

import java.util.Arrays;

public class Day2 extends Calendar.Day {

	@Override @Answer(14069)
	protected long partOne() {
		return Arrays.stream(this.input)
			.mapToInt(Day2::scoreNaive)
			.sum();
	}

	@Override @Answer(12411)
	protected long partTwo() {
		return Arrays.stream(this.input)
			.mapToInt(Day2::scoreStrategy)
			.sum();
	}

	private static int scoreNaive(String game) {
		return switch(game) {
			case "A X" -> 4;
			case "A Y" -> 8;
			case "A Z" -> 3;
			case "B X" -> 1;
			case "B Y" -> 5;
			case "B Z" -> 9;
			case "C X" -> 7;
			case "C Y" -> 2;
			case "C Z" -> 6;
			default -> throw new IllegalArgumentException();
		};
	}

	private static int scoreStrategy(String game) {
		return switch(game) {
			case "A X" -> 3;
			case "A Y" -> 4;
			case "A Z" -> 8;
			case "B X" -> 1;
			case "B Y" -> 5;
			case "B Z" -> 9;
			case "C X" -> 2;
			case "C Y" -> 6;
			case "C Z" -> 7;
			default -> throw new IllegalArgumentException();
		};
	}
}
