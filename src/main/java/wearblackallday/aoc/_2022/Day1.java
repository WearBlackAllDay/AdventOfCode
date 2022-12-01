package wearblackallday.aoc._2022;

import wearblackallday.aoc.Calendar;
import wearblackallday.aoc.common.Answer;

import java.util.*;

public class Day1 extends Calendar.Day {

	private final Queue<Integer> calories = new PriorityQueue<>(Comparator.reverseOrder());

	public Day1() {
		int elf = 0;
		for(String line : this.input) {
			if(line.isEmpty()) {
				this.calories.add(elf);
				elf = 0;
			} else elf += Integer.parseInt(line);
		}
	}

	@Override @Answer(69310)
	protected long partOne() {
		return this.calories.peek();

	}

	@Override @Answer(206104)
	protected long partTwo() {
		return this.calories.poll() + this.calories.poll() + this.calories.poll();
	}
}
