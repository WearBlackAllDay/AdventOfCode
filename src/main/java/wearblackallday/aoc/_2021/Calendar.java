package wearblackallday.aoc._2021;

import wearblackallday.aoc.common.Answer;

import java.io.*;
import java.util.function.ToIntFunction;

public class Calendar {
	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_RED = "\u001B[31m";
	private static final String ANSI_GREEN = "\u001B[32m";
	private static final String ANSI_YELLOW = "\u001B[33m";
	private static final String ANSI_BLUE = "\u001B[34m";

	public static void main(String[] args) throws NoSuchMethodException {
		Day day = new Day18();

		Answer answerOne = day.getClass().getDeclaredMethod("partOne").getAnnotation(Answer.class);
		Answer answerTwo = day.getClass().getDeclaredMethod("partTwo").getAnnotation(Answer.class);
		long resultOne = day.partOne();
		long resultTwo = day.partTwo();
		System.out.println(ANSI_YELLOW + day.getClass().getSimpleName() + ':' + ANSI_RESET);

		System.out.print(ANSI_BLUE  + resultOne + ANSI_RESET);
		System.out.println(answerOne == null ? "" : answerOne.value() == resultOne
			? ANSI_GREEN + "\s(correct)" + ANSI_RESET
			: ANSI_RED + "\s(wrong!)" + ANSI_RESET);

		System.out.print(ANSI_BLUE  + resultTwo + ANSI_RESET);
		System.out.println(answerTwo == null ? "" : answerTwo.value() == resultTwo
			? ANSI_GREEN + "\s(correct)" + ANSI_RESET
			: ANSI_RED + "\s(wrong!)" + ANSI_RESET);
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
	}
}