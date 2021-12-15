package wearblackallday.aoc._2021;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Day14 extends Calendar.Day {
	private final Map<Pair, PairPair> rules = Arrays.stream(this.input)
		.skip(2)
		.collect(Collectors.toMap(line -> new Pair(line.charAt(0), line.charAt(1)),
			line -> new PairPair(new Pair(line.charAt(0), line.charAt(6)), new Pair(line.charAt(6), line.charAt(1)))));

	@Override //3058
	protected long partOne() {
		return this.buildPolymer(10);
	}

	@Override //3447389044530
	protected long partTwo() {
		return this.buildPolymer(40);
	}

	private long buildPolymer(int steps) {
		Map<Pair, Long> frequency = new HashMap<>();
		for(int i = 0; i < this.input[0].length() - 1; i++) {
			Pair pair = new Pair(this.input[0].charAt(i), this.input[0].charAt(i + 1));
			frequency.put(pair, frequency.getOrDefault(pair, 0L) + 1);
		}
		for(int i = 0; i < steps; i++) {
			frequency = frequency.entrySet().stream()
				.flatMap(entry -> Stream.of(this.rules.get(entry.getKey()).left, this.rules.get(entry.getKey()).right)
					.map(pair -> Map.entry(pair, entry.getValue())))
				.collect(Collectors.groupingBy(Entry::getKey, Collectors.summingLong(Entry::getValue)));
		}

		LongSummaryStatistics statistics = frequency.entrySet().stream()
			.collect(Collectors.groupingBy(entry -> entry.getKey().right,
				Collectors.summingLong(Entry::getValue))).values().stream()
			.mapToLong(Long::longValue)
			.summaryStatistics();
		return statistics.getMax() - statistics.getMin();
	}

	private record Pair(char left, char right) {}
	private record PairPair(Pair left, Pair right) {}
}