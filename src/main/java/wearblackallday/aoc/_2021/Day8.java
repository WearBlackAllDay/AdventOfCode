package wearblackallday.aoc._2021;

import wearblackallday.aoc.Calendar;
import wearblackallday.aoc.common.Answer;

import java.util.*;
import java.util.stream.Collectors;

public class Day8 extends Calendar.Day {
	private final Entry[] entries = Arrays.stream(this.input)
		.map(Entry::new)
		.toArray(Entry[]::new);
	private char topLeft, middle, bottomLeft;

	@Override @Answer(365)
	protected long partOne() {
		int desiredDigits = 0;
		for(Entry entry : this.entries) {
			for(String output : entry.output) {
				switch(output.length()) {
					case 2, 4, 3, 7 -> desiredDigits++; //1, 4, 7, 8
				}
			}
		}
		return desiredDigits;
	}

	@Override @Answer(975706)
	protected long partTwo() {
		int sum = 0;
		for(Entry entry : this.entries) {
			this.decodeMapping(entry.signalPattern);
			sum += Arrays.stream(entry.output)
				.mapToInt(this::readDigit)
				.reduce(0, (a, b) -> a * 10 + b);
		}
		return sum;
	}

	private void decodeMapping(String[] signalPattern) {
		Map<Integer, List<String>> lengths = Arrays.stream(signalPattern)
			.collect(Collectors.groupingBy(String::length));

		String middleAndTopLeft = subtract(lengths.get(4).get(0), lengths.get(2).get(0));

		for(String lengthSix : lengths.get(6)) {
			String searchZero = subtract(middleAndTopLeft, lengthSix);
			String searchNine = subtract(subtract(lengths.get(7).get(0), lengthSix), lengths.get(4).get(0));
			if(!searchZero.isEmpty()) this.middle = searchZero.charAt(0);
			if(!searchNine.isEmpty()) this.bottomLeft = searchNine.charAt(0);
		}
		this.topLeft = middleAndTopLeft.charAt(0) == this.middle
			? middleAndTopLeft.charAt(1)
			: middleAndTopLeft.charAt(0);
	}

	private static String subtract(String target, String mask) {
		return target.replaceAll("[" + mask + "]+", "");
	}

	private int readDigit(String digit) {
		return switch(digit.length()) {
			case 2 -> 1;
			case 4 -> 4;
			case 3 -> 7;
			case 7 -> 8;
			case 5 -> containsChar(digit, this.topLeft)
				? 5 : containsChar(digit, this.bottomLeft) ? 2 : 3;
			case 6 -> !containsChar(digit, this.middle)
				? 0 : containsChar(digit, this.bottomLeft) ? 6 : 9;
			default -> throw new InputMismatchException("digit cannot be length: " + digit.length());
		};
	}

	private static boolean containsChar(String string, char c) {
		return string.indexOf(c) != -1;
	}

	private record Entry(String[] signalPattern, String[] output) {
		private Entry(String line) {
			this(line.substring(0, line.indexOf('|') - 1).split("\s"),
				 line.substring(line.indexOf('|') + 2).split("\s"));
		}
	}
}