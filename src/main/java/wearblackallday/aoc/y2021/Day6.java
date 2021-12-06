package wearblackallday.aoc.y2021;

public class Day6 extends Calendar.Day {
	private final int[] population = this.parseInts(this.input[0].split(","));

	@Override
	protected long partOne() {
		return populationGrowth(this.population, 80);
	}

	@Override
	protected long partTwo() {
		return populationGrowth(this.population, 256);
	}

	private static long populationGrowth(int[] population, int days) {
		int[] distribution = new int[6];
		long finalPopulation = 0L;

		for(int fish : population) {
			distribution[fish]++;
		}

		for(int state = 0; state < distribution.length; state++) {
			long partialGrowth = distribution[state] * singleFishGrowth(state, days);
			finalPopulation += partialGrowth;
		}
		return finalPopulation;
	}

	private static long singleFishGrowth(int initialState, int days) {
		long[] distribution = new long[9];
		distribution[initialState] = 1L;

		for(int day = 0; day < days; day++) {
			long newFish = 0L;
			for(int state = 0; state < distribution.length; state++) {
				if(state == 0) {
					newFish = distribution[0];
				} else {
					distribution[state - 1] += distribution[state];
				}
				distribution[state] = 0;
			}
			distribution[6] += newFish; //parent fish
			distribution[8] += newFish; //new fish
		}
		long population = 0L;

		for(long fishCount : distribution) {
			population += fishCount;
		}

		return population;
	}
}
