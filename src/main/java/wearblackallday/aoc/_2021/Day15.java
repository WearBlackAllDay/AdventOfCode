package wearblackallday.aoc._2021;

import wearblackallday.aoc.Calendar;
import wearblackallday.aoc.common.Answer;
import wearblackallday.aoc.common.Grid;

import java.util.*;

public class Day15 extends Calendar.Day implements Grid {
	private final int[] cave = Arrays.stream(this.input)
		.flatMapToInt(String::chars)
		.map(Character::getNumericValue)
		.toArray();
	private int width = this.input[0].length(), height = this.input.length;

	@Override @Answer(696)
	protected long partOne() {
		return this.aStar(this.cave);
	}

	@Override @Answer(2952)
	protected long partTwo() {
		int width = this.width, height = this.height;
		this.width *= 5; this.height *= 5;
		int[] bigCave = new int[this.size()];

		for(int x = 0; x < this.width; x++) {
			for(int y = 0; y < this.height; y++) {
				bigCave[this.toIndex(x, y)] = (this.cave[x % width + width * (y % height)]
					+ x / width + y / height - 1) % 9 + 1;
			}
		}
		return this.aStar(bigCave);
	}

	private int aStar(int[] cave) {
		BitSet closed = new BitSet(this.size());
		Queue<Node> open = new PriorityQueue<>(Comparator.comparingInt((Node node) -> node.cost + node.distance).reversed());
		open.add(new Node(0, 0, 0, Integer.MAX_VALUE));

		while(true) {
			Node currentNode = open.poll();
			if(currentNode.x == this.width - 1 && currentNode.y == this.height - 1) return currentNode.cost;
			if(closed.get(this.toIndex(currentNode.x, currentNode.y))) continue;

			closed.set(this.toIndex(currentNode.x, currentNode.y));
			this.forAdjoining(currentNode.x, currentNode.y, (x, y) -> {
				if(!closed.get(this.toIndex(x, y))) open.add(new Node(x, y,
					currentNode.cost + cave[this.toIndex(x, y)],
					this.height - y + this.width - x));
			});
		}
	}

	@Override
	public int width() {
		return this.width;
	}

	@Override
	public int height() {
		return this.height;
	}

	private record Node(int x, int y, int cost, int distance) {}
}