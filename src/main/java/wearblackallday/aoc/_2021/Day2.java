package wearblackallday.aoc._2021;

import wearblackallday.aoc.Calendar;
import wearblackallday.aoc.common.Answer;

public class Day2 extends Calendar.Day {

	@Override @Answer(2147104)
	protected long partOne() {
		int horizontal = 0, depth = 0;

		for(String line : this.input) {
			switch(line.charAt(0)) {
				case 'f' -> horizontal += getLastInt(line);
				case 'u' -> depth -= getLastInt(line);
				case 'd' -> depth += getLastInt(line);
			}
		}

		return depth * horizontal;
	}

	@Override @Answer(2044620088)
	protected long partTwo() {
		int horizontal = 0, depth = 0, aim = 0;

		for(String line : this.input) {
			switch(line.charAt(0)) {
				case 'f' -> {
					horizontal += getLastInt(line);
					depth += aim * getLastInt(line);
				}
				case 'u' -> aim -= getLastInt(line);
				case 'd' -> aim += getLastInt(line);
			}
		}

		return depth * horizontal;
	}

	private static int getLastInt(String line) {
		return line.charAt(line.length() - 1) - '0';
	}
}