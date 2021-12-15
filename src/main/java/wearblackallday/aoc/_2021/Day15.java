package wearblackallday.aoc._2021;

import wearblackallday.common.Grid;

import java.util.*;

public class Day15 extends Calendar.Day implements Grid {
	private final int[] cave = Arrays.stream(this.input)
		.flatMapToInt(line -> Arrays.stream(this.parseInts(line.split(""))))
		.toArray();
	private int width = this.input[0].length(), height = this.input.length;

	@Override //696
	protected long partOne() {
		return this.lowestRiskTraversal(this.cave);
	}

	@Override //2952
	protected long partTwo() {
		this.width *= 5;
		this.height *= 5;
		int[] bigCave = new int[this.size()];

		for(int x = 0; x < this.width; x++) {
			for(int y = 0; y < this.height; y++) {
				bigCave[this.toIndex(x, y)] = this.expand(x, y);
			}
		}
		return this.lowestRiskTraversal(bigCave);
	}

	private int lowestRiskTraversal(int[] cave) {
		BitSet visited = new BitSet(this.size());
		Queue<Chiton> queue = new PriorityQueue<>(Comparator.comparingInt(Chiton::risk));
		queue.add(new Chiton(0, 0, 0));

		while(true) {
			Chiton chiton = queue.poll();

			if(chiton.x == this.width - 1 && chiton.y == this.height - 1) return chiton.risk;
			visited.set(this.toIndex(chiton.x, chiton.y));

			this.forAdjoining(chiton.x, chiton.y, ((x, y) -> {
				if(!visited.get(this.toIndex(x, y))) {
					Chiton newChiton = new Chiton(x, y, chiton.risk + cave[this.toIndex(x, y)]);
					if(!queue.contains(newChiton)) queue.add(newChiton);
				}
			}));
		}
	}

	private int expand(int x, int y) {
		int width = this.width / 5, height = this.height / 5;
		int i = this.cave[(x % width) + width * (y % height)] + x / width + y / height;
		return i > 9 ? i - 9 : i;
	}

	@Override
	public int width() {
		return this.width;
	}

	@Override
	public int height() {
		return this.height;
	}

	private record Chiton(int x, int y, int risk) {
	}
}
