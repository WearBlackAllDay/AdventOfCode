package wearblackallday.aoc._2021;

import java.awt.Point;
import java.util.Arrays;
import java.util.Set;
import java.util.Spliterator;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class Day13 extends Calendar.Day {
	private final Set<Point> paper = Arrays.stream(this.input)
		.filter(line -> !line.isEmpty() && line.charAt(0) != 'f')
		.map(s -> new Point(parseInt(s.substring(0, s.indexOf(','))), parseInt(s.substring(s.indexOf(',') + 1))))
		.collect(Collectors.toSet());
	private final Spliterator<String> folds = Arrays.spliterator(this.input, this.paper.size() + 1, this.input.length);

	@Override
	protected long partOne() {
		this.folds.tryAdvance(this::fold);
		return this.paper.size();
	}

	@Override
	protected long partTwo() {
		this.folds.forEachRemaining(this::fold);
		int width = 0, height = 0;
		for(Point point : this.paper) {
			width = Math.max(point.x, width);
			height = Math.max(point.y, height);
		}
		Point point = new Point(0, 0);
		for(int y = 0; y <= height; y++) {
			for(int x = 0; x <= width; x++) {
				point.move(x, y);
				System.out.print(this.paper.contains(point) ? '█' : ' ');
			}
			System.out.println();
		}
		return 0;
	}

	private void fold(String instruction) {
		int axis = parseInt(instruction.substring(13));
		switch(instruction.charAt(11)) {
			case 'x' -> {
				this.paper.addAll(this.paper.stream()
					.filter(point -> point.x > axis)
					.map(point -> new Point((axis << 1) - point.x, point.y))
					.toList());
				this.paper.removeIf(point -> point.x > axis);
			}
			case 'y' -> {
				this.paper.addAll(this.paper.stream()
					.filter(point -> point.y > axis)
					.map(point -> new Point(point.x, point.y = (axis << 1) - point.y))
					.toList());
				this.paper.removeIf(point -> point.y > axis);
			}
		}
	}
}