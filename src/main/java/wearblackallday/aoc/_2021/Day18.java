package wearblackallday.aoc._2021;

import wearblackallday.aoc.common.Answer;

import java.util.*;

public class Day18 extends Calendar.Day {

	@Override @Answer(4173)
	protected long partOne() {
		return Arrays.stream(this.input)
			.map(SnailFishNumber::valueOf)
			.reduce(SnailFishNumber::add)
			.map(SnailFishNumber::magnitude)
			.orElse(-1);
	}

	@Override @Answer(4706)
	protected long partTwo() {
		int max = 0;
		for(String outer : this.input) {
			for(String inner : this.input) {
				if(outer.equals(inner)) continue;
				SnailFishNumber first = SnailFishNumber.valueOf(outer);
				SnailFishNumber second = SnailFishNumber.valueOf(inner);
				max = Math.max(max, first.add(second).magnitude());
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

	private static abstract sealed class SnailFishNumber permits SnailFishNumberRegular, SnailFishNumberPair {
		protected SnailFishNumberPair parent;

		protected abstract int magnitude();

		protected abstract boolean explode();

		protected abstract boolean split();

		protected abstract SnailFishNumberRegular numberOnSide(boolean leftSide);

		protected abstract Optional<SnailFishNumberRegular> firstNumberToSide(boolean leftSide, SnailFishNumber source);

		private static SnailFishNumber valueOf(String number) {
			Deque<SnailFishNumber> stack = new ArrayDeque<>();
			for(int i = 0; i < number.length(); i++) {
				char c = number.charAt(i);
				switch(c) {
					case ',', ' ', '[' -> {
					}
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
				while(this.explode()) ;
			} while(this.split());
		}

		protected int depth() {
			int depth = 0;
			SnailFishNumberPair parent = this.parent;
			while(parent != null) {
				depth++;
				parent = parent.parent;
			}
			return depth;
		}
	}

	private static final class SnailFishNumberRegular extends SnailFishNumber {
		private int value;

		private SnailFishNumberRegular(int value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return String.valueOf(this.value);
		}

		@Override
		protected int magnitude() {
			return this.value;
		}

		@Override
		protected boolean explode() {
			if(this.parent.depth() < 4) return false;

			this.parent.firstNumberToSide(true, this.parent.left)
				.ifPresent(left -> left.value += this.parent.left.magnitude());

			this.parent.firstNumberToSide(false, this.parent.right)
				.ifPresent(right -> right.value += this.parent.right.magnitude());

			replace(this.parent, new SnailFishNumberRegular(0));
			return true;
		}

		@Override
		protected boolean split() {
			if(this.value < 10) return false;

			replace(this, new SnailFishNumberPair(
				new SnailFishNumberRegular(this.value >> 1),
				new SnailFishNumberRegular((this.value + 1) >> 1)));
			return true;
		}

		@Override
		protected SnailFishNumberRegular numberOnSide(boolean leftSide) {
			return this;
		}

		@Override
		protected Optional<SnailFishNumberRegular> firstNumberToSide(boolean leftSide, SnailFishNumber source) {
			return Optional.of(this);
		}
	}

	private static final class SnailFishNumberPair extends SnailFishNumber {

		private SnailFishNumber left, right;

		private SnailFishNumberPair(SnailFishNumber left, SnailFishNumber right) {
			this.left = left;
			this.left.parent = this;

			this.right = right;
			this.right.parent = this;
		}

		@Override
		public String toString() {
			return '[' + this.left.toString() + ',' + this.right.toString() + ']';
		}

		@Override
		protected int magnitude() {
			return (this.left.magnitude() * 3) + (this.right.magnitude() << 1);
		}

		@Override
		protected boolean explode() {
			return this.left.explode() || this.right.explode();
		}

		@Override
		protected boolean split() {
			return this.left.split() || this.right.split();
		}

		@Override
		protected SnailFishNumberRegular numberOnSide(boolean leftSide) {
			return leftSide ? this.left.numberOnSide(leftSide) : this.right.numberOnSide(leftSide);
		}

		@Override
		protected Optional<SnailFishNumberRegular> firstNumberToSide(boolean leftSide, SnailFishNumber source) {
			SnailFishNumber sideChild = leftSide ? this.left : this.right;
			if(sideChild == source && this.parent == null) {
				return Optional.empty();
			} else if(sideChild == source) {
				return this.parent.firstNumberToSide(leftSide, this);
			} else {
				return Optional.of(sideChild.numberOnSide(!leftSide)); //switch side
			}
		}
	}
}