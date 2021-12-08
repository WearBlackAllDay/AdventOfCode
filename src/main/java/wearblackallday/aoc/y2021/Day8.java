package wearblackallday.aoc.y2021;

import java.util.*;
import java.util.stream.Collectors;

public class Day8 extends Calendar.Day {
	private final String[][] wires = Arrays.stream(this.input)
		.map(line -> line.split("\s\\|\s"))
		.toArray(String[][]::new);
	private final char[] mapping = new char[3]; //0=topLeft 1=middle 2=bottomLeft

	@Override
	protected long partOne() {
		int desiredDigits = 0;
		for(String[] wiring : this.wires) {
			for(String output : wiring[1].split("\s")) {
				switch(output.length()) {
					case 2, 4, 3, 7 -> desiredDigits++; //1, 4, 7, 8
				}
			}
		}
		return desiredDigits;
	}

	@Override
	protected long partTwo() {
		int sum = 0;
		for(String[] wiring : this.wires) {
			this.decodeMapping(wiring[0]);
			sum += Arrays.stream(wiring[1].split("\s"))
				.mapToInt(this::readDigit)
				.reduce((a, b) -> a * 10 + b)
				.getAsInt();
		}
		return sum;
	}

	private void decodeMapping(String input) {
		Map<Integer, List<String>> lengths = Arrays.stream(input.split("\s"))
			.collect(Collectors.groupingBy(String::length));

		String middleAndTopLeft = removeCommonChars(lengths.get(4).get(0), lengths.get(2).get(0));

		for(String lengthSix : lengths.get(6)) {
			String searchZero = removeCommonChars(middleAndTopLeft, lengthSix);
			String searchNine = removeCommonChars(removeCommonChars(lengths.get(7).get(0), lengthSix), lengths.get(4).get(0));
			if(!searchZero.isEmpty()) this.mapping[1] = searchZero.charAt(0);
			if(!searchNine.isEmpty()) this.mapping[2] = searchNine.charAt(0);
		}
		this.mapping[0] = middleAndTopLeft.charAt(0) == this.mapping[1]
			? middleAndTopLeft.charAt(1)
			: middleAndTopLeft.charAt(0);
	}

	private static String removeCommonChars(String target, String mask) {
		return target.replaceAll(String.join("|", mask.split("")), "");
	}

	private int readDigit(String wiring) {
		return switch(wiring.length()) {
			case 2 -> 1;
			case 4 -> 4;
			case 3 -> 7;
			case 7 -> 8;
			case 5 -> containsChar(wiring, this.mapping[0])
				? 5 : containsChar(wiring, this.mapping[2]) ? 2 : 3;
			case 6 -> !containsChar(wiring, this.mapping[1])
				? 0 : containsChar(wiring, this.mapping[2]) ? 6 : 9;
			default -> throw new InputMismatchException("wire cannot be length: " + wiring.length());
		};
	}

	private static boolean containsChar(String string, char c) {
		return string.indexOf(c) != -1;
	}
}