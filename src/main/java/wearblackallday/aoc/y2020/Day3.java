package wearblackallday.aoc.y2020;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Day3 extends Calendar.Day {
	private final int[][] trees = Arrays.stream(this.input)
		.map(String::chars)
		.map(IntStream::toArray)
		.toArray(int[][]::new);

	@Override
	protected long partOne() {
		return this.bumpsOnSlope(3, 1);
	}

	@Override
	protected long partTwo() {
		int[][] slopes = {{1, 1}, {3, 1}, {5, 1}, {7, 1}, {1, 2}};
		return Arrays.stream(slopes)
			.mapToLong(slope -> this.bumpsOnSlope(slope[0], slope[1]))
			.reduce(1L, (a, b) -> a * b);
	}

	private int bumpsOnSlope(int slopeRight, int slopeDown) {
		int encounters = 0;
		for(int y = 0, x = 0; y < this.trees.length; y += slopeDown, x += slopeRight) {
			if(this.trees[y][x % this.trees[0].length] == '#') encounters++;
		}
		return encounters;
	}
}