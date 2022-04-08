package wearblackallday.aoc._2021;

import wearblackallday.aoc.common.Answer;

import java.util.*;
import java.util.function.Predicate;

public class Day4 extends Calendar.Day {
	private final int[] rolls = this.parseInts(this.input[0].split(","));
	private final List<int[]> boards = new ArrayList<>();

	public Day4() {
		int[] all = Arrays.stream(this.input)
			.skip(1)
			.filter(Predicate.not(String::isEmpty))
			.flatMapToInt(line -> Arrays.stream(this.parseInts(line.trim().split("\s+"))))
			.toArray();

		for(int i = 0; i < all.length; i += 25) {
			this.boards.add(Arrays.copyOfRange(all, i, i + 25));
		}
	}

	@Override @Answer(8136)
	protected long partOne() {
		for(int roll : this.rolls) {
			for(int[] board : this.boards) {
				mark(roll, board);
				if(hasBingo(board)) {
					return winnerSum(roll, board);
				}
			}
		}
		return -1;
	}

	@Override @Answer(12738)
	protected long partTwo() {
		for(int roll : this.rolls) {
			this.boards.forEach(board -> mark(roll, board));
			if(this.boards.size() == 1 && hasBingo(this.boards.get(0))) return winnerSum(roll, this.boards.get(0));
			this.boards.removeIf(Day4::hasBingo);
		}
		return -1;
	}

	private static void mark(int roll, int[] board) {
		for(int i = 0; i < board.length; i++) {
			if(board[i] == roll) {
				board[i] |= 1 << 31;
				return;
			}
		}
	}

	private static boolean hasBingo(int[] board) {
		for(int i = 0; i < 5; i++) {
			boolean wonRow = true, wonCol = true;
			for(int row = 0; row < 5; row++) {
				if(board[toIndex(row, i)] >>> 31 == 0) {
					wonRow = false;
					break;
				}
			}
			if(wonRow) return true;
			for(int col = 0; col < 5; col++) {
				if(board[toIndex(i, col)] >>> 31 == 0) {
					wonCol = false;
					break;
				}
			}
			if(wonCol) return true;
		}
		return false;
	}

	private static int winnerSum(int winnerRoll, int[] winnerBoard) {
		int sum = 0;
		for(int entry : winnerBoard) {
			if(entry >>> 31 == 0) sum += entry;
		}
		return sum * winnerRoll;
	}

	private static int toIndex(int x, int y) {
		return y + 5 * x;
	}
}