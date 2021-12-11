package wearblackallday.aoc.y2021;

import java.util.Arrays;
import java.util.BitSet;

public class Day11 extends Calendar.Day {
	private final int[][] octoPus = Arrays.stream(this.input)
		.map(line -> this.parseInts(line.split("")))
		.toArray(int[][]::new);
	private final BitSet flashes = new BitSet(this.octoPus.length * this.octoPus[0].length);


	@Override
	protected long partOne() {
		int totalFlashes = 0;
		for(int step = 0; step < 100; step++) {
			for(int y = 0; y < this.octoPus.length; y++) {
				for(int x = 0; x < this.octoPus[0].length; x++) {
					this.tryFlash(x, y);
				}
			}
			totalFlashes += this.flashes.cardinality();
			this.flashes.clear();
		}
		return totalFlashes;
	}

	private void tryFlash(int x, int y) {
		if(x >= 0 && x < this.octoPus[0].length && y >= 0 && y < this.octoPus.length) {
			if(this.flashes.get(this.toIndex(x, y)) || ++this.octoPus[x][y] <= 9) return;

			this.octoPus[x][y] = 0;
			this.flashes.set(this.toIndex(x, y));

			this.tryFlash(x + 1, y);
			this.tryFlash(x - 1, y);

			this.tryFlash(x + 1, y + 1);
			this.tryFlash(x, y + 1);
			this.tryFlash(x - 1, y + 1);

			this.tryFlash(x + 1, y - 1);
			this.tryFlash(x, y - 1);
			this.tryFlash(x - 1, y - 1);
		}
	}

	@Override
	protected long partTwo() {
		int step = 0;
		while(this.flashes.cardinality() < this.octoPus.length * this.octoPus[0].length) {
			step++;
			this.flashes.clear();
			for(int y = 0; y < this.octoPus.length; y++) {
				for(int x = 0; x < this.octoPus[0].length; x++) {
					this.tryFlash(x, y);
				}
			}
		}
		return step + 100;
	}

	private int toIndex(int x, int y) {
		return x + this.octoPus[0].length * y;
	}
}