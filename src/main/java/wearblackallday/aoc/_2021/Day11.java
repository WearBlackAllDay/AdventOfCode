package wearblackallday.aoc._2021;

import wearblackallday.aoc.common.Answer;
import wearblackallday.aoc.common.Grid;

import java.util.Arrays;
import java.util.BitSet;

public class Day11 extends Calendar.Day implements Grid {
	private final int[] energy = Arrays.stream(this.input)
		.flatMapToInt(line -> Arrays.stream(this.parseInts(line.split(""))))
		.toArray();
	private final BitSet flashes = new BitSet(this.size());

	@Override @Answer(1644)
	protected long partOne() {
		int totalFlashes = 0;
		for(int step = 0; step < 100; step++) {
			totalFlashes += this.nextStep();
		}
		return totalFlashes;
	}

	@Override @Answer(229)
	protected long partTwo() {
		int step = 1;
		while(this.nextStep() < this.size()) {
			step++;
		}
		return step + 100;
	}

	private int nextStep() {
		this.flashes.clear();
		this.forEach(this::tryFlash);
		return this.flashes.cardinality();
	}

	private void tryFlash(int x, int y) {
		if(this.inBounds(x, y)) {
			if(this.flashes.get(this.toIndex(x, y)) || ++this.energy[this.toIndex(x, y)] <= 9) return;

			this.energy[this.toIndex(x, y)] = 0;
			this.flashes.set(this.toIndex(x, y));

			this.forAdjacent(x, y, this::tryFlash);
		}
	}

	@Override
	public int width() {
		return this.input[0].length();
	}

	@Override
	public int height() {
		return this.input.length;
	}
}