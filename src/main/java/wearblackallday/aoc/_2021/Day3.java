package wearblackallday.aoc._2021;

import wearblackallday.aoc.Calendar;
import wearblackallday.aoc.common.Answer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Day3 extends Calendar.Day {

	@Override @Answer(4138664)
	protected long partOne() {
		int[] setBits = new int[this.input[0].length()];
		for(String line : this.input) {
			for(int i = 0; i < line.length(); i++) {
				if(line.charAt(i) == '1') setBits[i]++;
			}
		}
		int gamma = 0, epsilon = 0;
		for(int i = 0; i < setBits.length; i++) {
			if(setBits[i] > this.input.length >> 1) {
				gamma |= 1 << setBits.length - i - 1;
			} else {
				epsilon |= 1 << setBits.length - i - 1;
			}
		}
		return gamma * epsilon;
	}

	@Override @Answer(4273224)
	protected long partTwo() {
		Set<String> oxygenValues = new HashSet<>(Arrays.asList(this.input));
		Set<String> carbonValues = new HashSet<>(Arrays.asList(this.input));

		for(int i = 0; i < this.input[0].length(); i++) {
			int[] bits = {i, 0, 0, 0, 0};
			for(String line : oxygenValues) {
				if(line.charAt(i) == '1') {
					bits[1]++;
				} else {
					bits[2]++;
				}
			}
			for(String line : carbonValues) {
				if(line.charAt(i) == '1') {
					bits[3]++;
				} else {
					bits[4]++;
				}
			}
			if(oxygenValues.size() != 1) {
				oxygenValues.removeIf(line -> line.charAt(bits[0]) != (bits[1] >= bits[2] ? '1' : '0'));
			}
			if(carbonValues.size() != 1) {
				carbonValues.removeIf(line -> line.charAt(bits[0]) != (bits[3] >= bits[4] ? '0' : '1'));
			}
		}

		return Integer.parseInt(oxygenValues.iterator().next(), 2)
			 * Integer.parseInt(carbonValues.iterator().next(), 2);
	}
}