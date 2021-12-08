package wearblackallday.aoc.y2021;

import java.util.Arrays;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Day5 extends Calendar.Day {
	private final int[][] vents = Arrays.stream(this.input)
		.map(line -> this.parseInts(line.split("\s->\s|,")))
		.toArray(int[][]::new);
	private final int maxX, maxY;

	public Day5() {
		int maxX = 0, maxY = 0;
		for(int[] vent : this.vents) {
			maxX = max(maxX, max(vent[0], vent[2]));
			maxY = max(maxY, max(vent[1], vent[3]));
		}
		this.maxX = maxX;
		this.maxY = maxY;
	}

	@Override
	protected long partOne() {
		return this.countOverlaps(this.vents, false);
	}

	@Override
	protected long partTwo() {
		return this.countOverlaps(this.vents, true);
	}

	private long countOverlaps(int[][] vents, boolean includeDiagonals) {
		int[][] diagram = new int[this.maxX + 1][this.maxY + 1];

		for(int[] vent : vents) {
			if(vent[0] == vent[2]) { //vertical
				for(int y = min(vent[1], vent[3]); y <= max(vent[1], vent[3]); y++) {
					diagram[vent[0]][y]++;
				}
			} else if(vent[1] == vent[3]) { //horizontal
				for(int x = min(vent[0], vent[2]); x <= max(vent[0], vent[2]); x++) {
					diagram[x][vent[1]]++;
				}
			} else if(includeDiagonals && Math.abs(vent[0] - vent[2]) == Math.abs(vent[1] - vent[3])) { //diagonal
				if(vent[0] > vent[2]) {
					vent[0] = vent[0] ^ vent[2];
					vent[2] = vent[0] ^ vent[2];
					vent[0] = vent[0] ^ vent[2];

					vent[1] = vent[1] ^ vent[3];
					vent[3] = vent[1] ^ vent[3];
					vent[1] = vent[1] ^ vent[3];
				}
				for(int x = vent[0], y = vent[1]; x <= vent[2]; x++, y += Integer.signum(vent[3] - vent[1])) {
					diagram[x][y]++;
				}
			}

		}
		return Arrays.stream(diagram)
			.flatMapToInt(Arrays::stream)
			.filter(i -> i >= 2)
			.count();
	}
}
