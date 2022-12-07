package wearblackallday.aoc._2022;

import wearblackallday.aoc.Calendar;
import wearblackallday.aoc.common.Answer;

import java.util.HashMap;
import java.util.Map;

public class Day6 extends Calendar.Day {

	@Override @Answer(1920)
	protected long partOne() {
		return this.firstMarker(4);
	}

	@Override @Answer(2334)
	protected long partTwo() {
		return this.firstMarker(14);
	}

	private int firstMarker(int length) {
		Map<Character, Integer> frequency = new HashMap<>();

		for(int i = 0; i < (length - 1); i++) {
			frequency.merge(this.input[0].charAt(i), 1, Integer::sum);
		}

		for(int i = (length - 1); i < this.input[0].length(); i++) {
			frequency.merge(this.input[0].charAt(i), 1, Integer::sum);

			if(frequency.size() == length) return i + 1;

			frequency.merge(this.input[0].charAt(i - (length - 1)), -1, (a, b) -> a + b == 0 ? null : a + b);
		}

		return -1;
	}
}
