package wearblackallday.aoc._2021;

import wearblackallday.aoc.common.Answer;

import java.util.*;

public class Day10 extends Calendar.Day {
	private final List<Long> incompleteSums = new ArrayList<>();

	@Override @Answer(367059)
	protected long partOne() {
		int errorSum = 0;
		main:
		for(String line : this.input) {
			Deque<Character> closers = new ArrayDeque<>();
			for(int i = 0; i < line.length(); i++) {
				char c = line.charAt(i);
				switch(c) {
					case '(' -> closers.push(')'); //'(' + 2 == '*' smh
					case '[', '{', '<' -> closers.push((char)(c + 2));
					case ')', ']', '}', '>' -> {
						if(closers.pop() != c) {
							errorSum += switch(c) {
								case ')' -> 3;
								case ']' -> 57;
								case '}' -> 1197;
								case '>' -> 25137;
								default -> 0;
							};
							continue main;
						}
					}
				}
			}
			this.incompleteSums.add(closers.stream()
				.mapToLong(closer -> (closer & 2) << 1 | (closer & 96) >> 5 ^ (closer & 2) >> 1)
				.reduce(0L, (a, b) -> a * 5 + b));
		}
		return errorSum;
	}

	@Override @Answer(1952146692)
	protected long partTwo() {
		Collections.sort(this.incompleteSums);
		return this.incompleteSums.get(this.incompleteSums.size() >> 1);
	}
}