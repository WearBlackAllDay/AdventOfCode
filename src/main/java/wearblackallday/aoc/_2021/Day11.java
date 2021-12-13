package wearblackallday.aoc._2021;

import java.util.Arrays;
import java.util.BitSet;

public class Day11 extends Calendar.Day {
	private final int[][] energy = Arrays.stream(this.input)
		.map(line -> this.parseInts(line.split("")))
		.toArray(int[][]::new);
	private final BitSet flashes = new BitSet(this.energy.length * this.energy[0].length);


	@Override
	protected long partOne() {
		int totalFlashes = 0;
		for(int step = 0; step < 100; step++) {
			totalFlashes += this.nextStep();
		}
		return totalFlashes;
	}

	@Override
	protected long partTwo() {
		int step = 1;
		while(this.nextStep() < this.energy.length * this.energy[0].length) {
			step++;
		}
		return step + 100;
	}

	private int nextStep() {
		this.flashes.clear();
		for(int y = 0; y < this.energy.length; y++) {
			for(int x = 0; x < this.energy[0].length; x++) {
				this.tryFlash(x, y);
			}
		}
		return this.flashes.cardinality();
	}

	private void tryFlash(int x, int y) {
		if(x >= 0 && x < this.energy[0].length && y >= 0 && y < this.energy.length) {
			if(this.flashes.get(this.toIndex(x, y)) || ++this.energy[x][y] <= 9) return;

			this.energy[x][y] = 0;
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

	private int toIndex(int x, int y) {
		return x + this.energy[0].length * y;
	}
}