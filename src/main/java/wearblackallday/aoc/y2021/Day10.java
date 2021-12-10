package wearblackallday.aoc.y2021;

import java.util.*;

public class Day10 extends Calendar.Day {
	private final List<Long> incompleteSums = new ArrayList<>();

	@Override
	protected long partOne() {
		int errorSum = 0;
		main:
		for(String line : this.input) {
			Deque<Character> closers = new ArrayDeque<>();
			for(int i = 0; i < line.length(); i++) {
				switch(line.charAt(i)) {
					case '(' -> closers.push(')'); //'(' + 2 == '*' smh
					case '[', '{', '<' -> closers.push((char)(line.charAt(i) + 2));
					case ')', ']', '}', '>' -> {
						if(closers.pop() != line.charAt(i)) {
							errorSum += switch(line.charAt(i)) {
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
				.mapToLong(closer -> switch(closer) {
					case ')' -> 1L;
					case ']' -> 2L;
					case '}' -> 3L;
					case '>' -> 4L;
					default -> 0;
				})
				.reduce(0, (a, b) -> a * 5 + b));
		}
		return errorSum;
	}

	@Override
	protected long partTwo() {
		Collections.sort(this.incompleteSums);
		return this.incompleteSums.get(this.incompleteSums.size() >> 1);
	}
}