package wearblackallday.aoc.y2021;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.function.ToIntFunction;

public class Calendar {
	public static void main(String[] args) {
		new Day6().printResults();
	}

	protected static abstract class Day {
		protected String[] input = new BufferedReader(new InputStreamReader(
			Calendar.class.getResourceAsStream("/input/2021/" + this.getClass().getSimpleName().toLowerCase())))
			.lines().toArray(String[]::new);

		protected abstract long partOne();
		protected abstract long partTwo();

		protected int[] parseInts(String[] lines) {
			return this.parseInts(lines, Integer::parseInt);
		}

		protected int[] parseInts(String[] lines, ToIntFunction<String> parser) {
			int[] ints = new int[lines.length];
			for(int i = 0; i < ints.length; i++) {
				ints[i] = parser.applyAsInt(lines[i]);
			}
			return ints;
		}

		public void printResults() {
			System.out.println(this.getClass().getSimpleName() + ":");
			System.out.println(this.partOne());
			System.out.println(this.partTwo());
		}
	}
}