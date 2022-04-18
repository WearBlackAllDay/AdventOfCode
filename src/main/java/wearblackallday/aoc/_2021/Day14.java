package wearblackallday.aoc._2021;

import wearblackallday.aoc.common.Answer;

import java.util.*;
import java.util.stream.Collectors;
import static wearblackallday.aoc._2021.Day6.*;

public class Day14 extends Calendar.Day {
	private final Map<Pair, PairPair> rules = Arrays.stream(this.input)
		.skip(2)
		.collect(Collectors.toMap(Pair::new, PairPair::new));

	private final Map<Pair, Integer> indexMap = new HashMap<>(this.rules.size());
	private final long[][] transitionMatrix = new long[this.rules.size()][this.rules.size()];
	private final long[] startVector = new long[this.rules.size()];

	protected Day14() {
		Iterator<Pair> possiblePairs = this.rules.keySet().iterator();
		for(int i = 0; i < this.rules.size(); i++) {
			this.indexMap.put(possiblePairs.next(), i);
		}

		for(var pairIndex : this.indexMap.entrySet()) {
			PairPair mapping = this.rules.get(pairIndex.getKey());
			this.transitionMatrix[this.indexMap.get(mapping.left)][pairIndex.getValue()] = 1;
			this.transitionMatrix[this.indexMap.get(mapping.right)][pairIndex.getValue()] = 1;
		}

		for(int i = 0; i < this.input[0].length() - 1; i++) {
			Pair pair = new Pair(this.input[0].charAt(i), this.input[0].charAt(i + 1));
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

		Map<Character, Long> frequency = new HashMap<>();

		for(var pairIndex : this.indexMap.entrySet()) {
			frequency.merge(pairIndex.getKey().right, distribution[pairIndex.getValue()], Long::sum);
		}

		LongSummaryStatistics statistics = frequency.values().stream()
			.mapToLong(Long::longValue)
			.summaryStatistics();

		return statistics.getMax() - statistics.getMin();
	}

	private record Pair(char left, char right) {
		private Pair(String line) {
			this(line.charAt(0), line.charAt(1));
		}
	}
	private record PairPair(Pair left, Pair right) {
		private PairPair(String line) {
			this(new Pair(line.charAt(0), line.charAt(6)), new Pair(line.charAt(6), line.charAt(1)));
		}
	}
}