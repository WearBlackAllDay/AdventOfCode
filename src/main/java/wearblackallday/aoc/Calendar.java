package wearblackallday.aoc;

import wearblackallday.aoc._2022.*;
import wearblackallday.aoc.common.Answer;

import java.io.*;
import java.util.function.ToIntFunction;

public class Calendar {
	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_RED = "\u001B[31m";
	private static final String ANSI_GREEN = "\u001B[32m";
	private static final String ANSI_YELLOW = "\u001B[33m";
	private static final String ANSI_BLUE = "\u001B[34m";

	public static void main(String[] args) throws NoSuchMethodException, IOException {
		Day day = new Day3();

		System.out.println(ANSI_YELLOW + day.getClass().getSimpleName() + ':' + ANSI_RESET);

		printAnswer(day.partOne(), day.getClass().getDeclaredMethod("partOne").getAnnotation(Answer.class));
		printAnswer(day.partTwo(), day.getClass().getDeclaredMethod("partTwo").getAnnotation(Answer.class));
	}

	private static void printAnswer(long result, Answer correctAnswer) {
		System.out.print(ANSI_BLUE  + result + ANSI_RESET);
		System.out.println(correctAnswer == null ? "" : correctAnswer.value() == result
			? ANSI_GREEN + "\s(correct)" + ANSI_RESET
			: ANSI_RED + "\s(wrong!)" + ANSI_RESET);
	}

	public static abstract class Day {
		private final String filePath = "/input/"
			+ this.getClass().getPackage().getName().substring(21)
			+ "/"
			+ this.getClass().getSimpleName().toLowerCase();

		protected final String[] input = new BufferedReader(new InputStreamReader(
			Calendar.class.getResourceAsStream(this.filePath)))
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