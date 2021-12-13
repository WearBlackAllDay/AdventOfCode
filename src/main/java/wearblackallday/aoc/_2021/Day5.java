package wearblackallday.aoc._2021;

import java.util.Arrays;

import static java.lang.Math.*;

public class Day5 extends Calendar.Day {
	private final Vent[] vents = Arrays.stream(this.input)
		.map(Vent::new)
		.toArray(Vent[]::new);
	private final int width, height;

	public Day5() {
		int maxX = 0, maxY = 0;
		for(Vent vent : this.vents) {
			maxX = max(maxX, max(vent.x1, vent.y1));
			maxY = max(maxY, max(vent.x2, vent.y2));
		}
		this.width = maxX + 1;
		this.height = maxY + 1;
	}

	@Override //4745
	protected long partOne() {
		return this.countOverlaps(false);
	}

	@Override //18442
	protected long partTwo() {
		return this.countOverlaps(true);
	}

	private long countOverlaps(boolean includeDiagonals) {
		int[] diagram = new int[this.width * this.height];

		for(Vent vent : this.vents) {
			if(vent.x1 == vent.y1) { //vertical
				for(int y = min(vent.x2, vent.y2); y <= max(vent.x2, vent.y2); y++) {
					diagram[this.toIndex(vent.x1, y)]++;
				}
			} else if(vent.x2 == vent.y2) { //horizontal
				for(int x = min(vent.x1, vent.y1); x <= max(vent.x1, vent.y1); x++) {
					diagram[this.toIndex(x, vent.x2)]++;
				}
			} else if(includeDiagonals && abs(vent.x1 - vent.y1) == abs(vent.x2 - vent.y2)) { //diagonal
				int x1 = vent.x1, x2 = vent.x2, y1 = vent.y1, y2 = vent.y2;
				if(vent.x1 > vent.y1) {
					x1 = vent.y1; y1 = vent.x1;
					x2 = vent.y2; y2 = vent.x2;
				}
				for(int x = x1, y = x2; x <= y1; x++, y += Integer.signum(y2 - x2)) {
					diagram[this.toIndex(x, y)]++;
				}
			}

		}
		return Arrays.stream(diagram)
			.filter(i -> i >= 2)
			.count();
	}

	private record Vent(int x1, int x2, int y1, int y2) {
		private Vent(String line) {
			this(Integer.parseInt(line.substring(0, line.indexOf(','))), 
				 Integer.parseInt(line.substring(line.indexOf(',') + 1, line.indexOf(' '))),
				 Integer.parseInt(line.substring(line.lastIndexOf(' ') + 1, line.lastIndexOf(','))),
				 Integer.parseInt(line.substring(line.lastIndexOf(',') + 1)));
		}
	}

	private int toIndex(int x, int y) {
		return x + this.width * y;
	}
}