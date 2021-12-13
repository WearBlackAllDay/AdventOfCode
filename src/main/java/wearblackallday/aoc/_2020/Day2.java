package wearblackallday.aoc._2020;

import java.util.Arrays;
import java.util.function.BiPredicate;

public class Day2 extends Calendar.Day {
	private final String[][] passwordInfo = Arrays.stream(this.input)
		.map(line -> line.split("\s"))
		.toArray(String[][]::new);

	@Override
	protected long partOne() {
		return this.validPasswords((policy, info) -> {
			int charCount = (int)info[2].chars()
				.filter(c -> c == info[1].charAt(0))
				.count();
			return charCount >= policy[0] && charCount <= policy[1];
		});
	}

	@Override
	protected long partTwo() {
		return this.validPasswords((policy, info) ->
				info[2].charAt(policy[0] - 1) == info[1].charAt(0) ^
				info[2].charAt(policy[1] - 1) == info[1].charAt(0));
	}

	private int validPasswords(BiPredicate<int[], String[]> policies) {
		int validPasswords = 0;

		for(String[] info : this.passwordInfo) {
			int[] restrictions = this.parseInts(info[0].split("-"));
			if(policies.test(restrictions, info)) validPasswords++;
		}
		return validPasswords;
	}
}