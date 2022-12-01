package wearblackallday.aoc._2021;

import wearblackallday.aoc.Calendar;
import wearblackallday.aoc.common.Answer;

import java.util.*;
import java.util.stream.Collectors;
import static wearblackallday.aoc._2021.Day6.*;

public class Day14 extends Calendar.Day {
	private final Map<String, Character> rules = Arrays.stream(this.input)
		.skip(2)
		.collect(Collectors.toMap(line -> line.substring(0, 2), line -> line.charAt(line.length() - 1)));

	private final Map<String, Integer> indexMap = new HashMap<>(this.rules.size());
	private final long[][] transitionMatrix = new long[this.rules.size()][this.rules.size()];
	private final long[] startVector = new long[this.rules.size()];

	protected Day14() {
		Iterator<String> possiblePairs = this.rules.keySet().iterator();
		for(int i = 0; i < this.rules.size(); i++) {
			this.indexMap.put(possiblePairs.next(), i);
		}

		for(var pairIndex : this.indexMap.entrySet()) {
			String pair1 = "" + pairIndex.getKey().charAt(0) + this.rules.get(pairIndex.getKey());
			String pair2 = "" + this.rules.get(pairIndex.getKey()) + pairIndex.getKey().charAt(1);
			this.transitionMatrix[this.indexMap.get(pair1)][pairIndex.getValue()] = 1;
			this.transitionMatrix[this.indexMap.get(pair2)][pairIndex.getValue()] = 1;
		}

		for(int i = 0; i < this.input[0].length() - 1; i++) {
			String pair = this.input[0].substring(i, i + 2);
			this.startVector[this.indexMap.get(pair)]++;
		}
	}

	@Override @Answer(3058)
	protected long partOne() {
		return this.buildPolymer(10);
	}

	@Override @Answer(3447389044530L)
	protected long partTwo() {
		return this.buildPolymer(40);
	}

	private long buildPolymer(int steps) {
		long[] distribution = matrixVectorProduct(matrixPower(this.transitionMatrix, steps), this.startVector);

		LongSummaryStatistics statistics = this.indexMap.entrySet().stream()
			.collect(Collectors.groupingBy(e -> e.getKey().charAt(1), Collectors.summingLong(e -> distribution[e.getValue()])))
			.values().stream()
			.mapToLong(Long::longValue)
			.summaryStatistics();

		return statistics.getMax() - statistics.getMin();
	}
}