package wearblackallday.aoc.y2021;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.stream.IntStream;

public class Day7 extends Calendar.Day {
	private final int[] crabs = this.parseInts(this.input[0].split(","));
	private final int min, max;

	public Day7() {
		IntSummaryStatistics stats = Arrays.stream(this.crabs).summaryStatistics();
		this.min = stats.getMin();
		this.max = stats.getMax();
	}

	@Override
	protected long partOne() {
		int lowestCost = Integer.MAX_VALUE;
		for(int horizontal = this.min; horizontal <= this.max; horizontal++) {
			int fuelCost = 0;
			for(int crab : this.crabs) {
				fuelCost += Math.abs(crab - horizontal);
			}
			lowestCost = Math.min(fuelCost, lowestCost);
		}
		return lowestCost;
	}

	@Override
	protected long partTwo() {
		int lowestCost = Integer.MAX_VALUE;
		for(int horizontal = this.min; horizontal <= this.max; horizontal++) {
			int fuelCost = 0;
			for(int crab : this.crabs) {
				fuelCost += IntStream.rangeClosed(1, Math.abs(crab - horizontal)).sum();
			}
			lowestCost = Math.min(fuelCost, lowestCost);
		}
		return lowestCost;
	}
}