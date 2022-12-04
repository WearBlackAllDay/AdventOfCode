package wearblackallday.aoc._2022;

import wearblackallday.aoc.Calendar;
import wearblackallday.aoc.common.Answer;

public class Day3 extends Calendar.Day {

	@Override @Answer(7821)
	protected long partOne() {
		int prioritySum = 0;

		for(String ruckSack : this.input) {
			String firstCompartment = ruckSack.substring(0, ruckSack.length() >> 1);
			String secondCompartment = ruckSack.substring(ruckSack.length() >> 1);

			for(int i = 0; i < firstCompartment.length(); i++) {
				char item = firstCompartment.charAt(i);

				if(secondCompartment.indexOf(item) != -1) {
					prioritySum += getPriority(item);
					break;
				}
			}
		}

		return prioritySum;
	}

	@Override @Answer(2752)
	protected long partTwo() {
		int prioritySum = 0;

		for(int group = 0; group < this.input.length; group += 3) {
			for(int i = 0; i < this.input[group].length(); i++) {
				char item = this.input[group].charAt(i);

				if(this.input[group + 1].indexOf(item) != -1 && this.input[group + 2].indexOf(item) != -1) {
					prioritySum += getPriority(item);
					break;
				}
			}
		}

		return prioritySum;
	}

	private static int getPriority(char item) {
		return item - (Character.isLowerCase(item) ? 96 : 38);
	}
}
