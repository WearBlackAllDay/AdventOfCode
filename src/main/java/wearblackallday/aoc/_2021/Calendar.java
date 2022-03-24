package wearblackallday.aoc._2021;

import wearblackallday.aoc.common.Answer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.function.ToIntFunction;

public class Calendar {
	public static void main(String[] args) throws NoSuchMethodException {
		Day day = new Day18();

		Answer answerOne = day.getClass().getDeclaredMethod("partOne").getAnnotation(Answer.class);
		Answer answerTwo = day.getClass().getDeclaredMethod("partTwo").getAnnotation(Answer.class);
		long resultOne = day.partOne();
		long resultTwo = day.partTwo();
		System.out.println(day.getClass().getSimpleName() + ":");

		System.out.print(resultOne);
		System.out.println(answerOne == null ? "" : answerOne.value() == resultOne ? "\s(correct)" : "\s(wrong!)");

		System.out.print(resultTwo);
		System.out.println(answerTwo == null ? "" : answerTwo.value() == resultTwo ? "\s(correct)" : "\s(wrong!)");
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