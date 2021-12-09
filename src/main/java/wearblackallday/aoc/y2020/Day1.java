package wearblackallday.aoc.y2020;

public class Day1 extends Calendar.Day {
	private final int[] numbers = this.parseInts(this.input);

	@Override
	protected long partOne() {
		for(int number : this.numbers) {
			for(int otherNumber : this.numbers) {
				if(number + otherNumber == 2020) return number * otherNumber;
			}
		}
		return -1;
	}

	@Override
	protected long partTwo() {
		for(int number : this.numbers) {
			for(int otherNumber : this.numbers) {
				for(int thirdNumber : this.numbers) {
					if(number + otherNumber + thirdNumber == 2020) return number * otherNumber * thirdNumber;
				}
			}
		}
		return -1;
	}
}