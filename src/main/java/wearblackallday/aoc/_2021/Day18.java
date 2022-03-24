package wearblackallday.aoc._2021;

import wearblackallday.aoc.common.Answer;

import java.util.*;
import java.util.stream.Stream;

public class Day18 extends Calendar.Day {

	private final SnailFishNumber[] numbers = Arrays.stream(this.input)
		.map(SnailFishNumber::valueOf)
		.toArray(SnailFishNumber[]::new);

	@Override @Answer(4173)
	protected long partOne() {
		return Arrays.stream(this.input)
			.map(SnailFishNumber::valueOf)
			.reduce(SnailFishNumber::add)
			.map(SnailFishNumber::magnitude)
			.orElse(-1);
	}

	@Override
	protected long partTwo() {
		int max = 0;
		for(SnailFishNumber number : this.numbers) {
			for(SnailFishNumber otherNumber : this.numbers) {
				if(number == otherNumber) continue;
				max = Math.max(max, number.add(otherNumber).magnitude());
				max = Math.max(max, otherNumber.add(number).magnitude());
			}
		}
		return max;
	}

	private static void replace(SnailFishNumber oldNumber, SnailFishNumber newNumber) {
		SnailFishNumberPair parent = oldNumber.parent;
		newNumber.parent = parent;
		if(parent.left == oldNumber) parent.left = newNumber;
		else parent.right = newNumber;
	}

	private static abstract class SnailFishNumber {
		protected SnailFishNumberPair parent;

		protected abstract int magnitude();

		private static SnailFishNumber valueOf(String number) {
			Deque<SnailFishNumber> stack = new ArrayDeque<>();
			for(int i = 0; i < number.length(); i++) {
				char c = number.charAt(i);
				switch(c) {
					case ',', ' ', '[' -> {}
					case ']' -> {
						SnailFishNumber right = stack.poll();
						stack.push(new SnailFishNumberPair(stack.poll(), right));
					}
					default -> stack.push(new SnailFishNumberRegular(c - 48)); //magic ascii number
				}
			}
			return stack.poll();
		}

		private SnailFishNumber add(SnailFishNumber other) {
			SnailFishNumber sum = new SnailFishNumberPair(this, other);
			sum.reduce();
			return sum;
		}

		private void reduce() {
			do {
				while(this.explode());
			} while(this.split());
		}

		private boolean explode() {
			Optional<SnailFishNumberPair> leftMostNested = this.stream()
				.filter(SnailFishNumberPair.class::isInstance)
				.filter(number -> number.depth() >= 4)
				.findFirst()
				.map(SnailFishNumberPair.class::cast);

			leftMostNested.ifPresent(pair -> {
				pair.firstNumberToSide(true, pair.left)
					.ifPresent(left -> left.value += pair.left.magnitude());

				pair.firstNumberToSide(false, pair.right)
					.ifPresent(right -> right.value += pair.right.magnitude());

				replace(pair, new SnailFishNumberRegular(0));
			});

			return leftMostNested.isPresent();
		}

		private boolean split() {
			Optional<SnailFishNumberRegular> bigRegular = this.stream()
				.filter(number -> number instanceof SnailFishNumberRegular regular && regular.value >= 10)
				.findFirst()
				.map(SnailFishNumberRegular.class::cast);

			bigRegular.ifPresent(regular -> {
				SnailFishNumber split = new SnailFishNumberPair(
					new SnailFishNumberRegular(regular.value >> 1),
					new SnailFishNumberRegular((regular.value + 1) >> 1)
				);

				replace(regular, split);
			});

			return bigRegular.isPresent();
		}

		private int depth() {
			int depth = 0;
			SnailFishNumberPair parent = this.parent;
			while(parent != null) {
				depth++;
				parent = parent.parent;
			}
			return depth;
		}

		protected Stream<SnailFishNumber> stream() {
			if(this instanceof SnailFishNumberPair pair)
				return Stream.concat(Stream.of(pair), Stream.of(pair.left, pair.right).flatMap(SnailFishNumber::stream));
			else return Stream.of(this);
		}
	}

	private static class SnailFishNumberRegular extends SnailFishNumber {
		private int value;

		private SnailFishNumberRegular(int value) {
			this.value = value;
		}

		@Override
		protected int magnitude() {
			return this.value;
		}

		@Override
		public String toString() {
			return String.valueOf(this.value);
		}
	}

	private static class SnailFishNumberPair extends SnailFishNumber {

		private SnailFishNumber left, right;

		public SnailFishNumberPair(SnailFishNumber left, SnailFishNumber right) {
			this.left = left;
			this.left.parent = this;

			this.right = right;
			this.right.parent = this;
		}

		private SnailFishNumberRegular numberOnSide(boolean leftSide) {
			SnailFishNumber sideChild = leftSide ? this.left : this.right;
			return sideChild instanceof SnailFishNumberPair pair
				? pair.numberOnSide(leftSide)
				: (SnailFishNumberRegular)sideChild;
		}

		private Optional<SnailFishNumberRegular> firstNumberToSide(boolean leftSide, SnailFishNumber source) {
			SnailFishNumber sideChild = leftSide ? this.left : this.right;
			if(sideChild == source) {
				if(this.parent == null) return Optional.empty();
				else return this.parent.firstNumberToSide(leftSide, this);
			} else {
				if(sideChild instanceof SnailFishNumberRegular regular) return Optional.of(regular);
				else return Optional.of(((SnailFishNumberPair)sideChild).numberOnSide(!leftSide)); //switch side
			}
		}

		@Override
		protected int magnitude() {
			return (this.left.magnitude() * 3) + (this.right.magnitude() << 1);
		}

		@Override
		public String toString() {
			return '[' + this.left.toString() + ',' + this.right.toString() + ']';
		}
	}
}