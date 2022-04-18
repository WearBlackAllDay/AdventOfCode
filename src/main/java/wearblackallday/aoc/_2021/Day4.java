package wearblackallday.aoc._2021;

import wearblackallday.aoc.common.Answer;

import java.util.*;
import java.util.function.Predicate;

public class Day4 extends Calendar.Day {
	private final int[] rolls = this.parseInts(this.input[0].split(","));
	private final List<BingoBoard> boards = new ArrayList<>();

	public Day4() {
		int[] all = Arrays.stream(this.input)
			.skip(1)
			.filter(Predicate.not(String::isEmpty))
			.flatMapToInt(line -> Arrays.stream(this.parseInts(line.trim().split("\s+"))))
			.toArray();

		for(int i = 0; i < all.length; i += 25) {
			this.boards.add(new BingoBoard(Arrays.copyOfRange(all, i, i + 25)));
		}
	}

	@Override @Answer(8136)
	protected long partOne() {
		for(int roll : this.rolls) {
			for(BingoBoard board : this.boards) {
				board.mark(roll);
				if(board.hasBingo()) return board.winnerSum(roll);
			}
		}
		return -1;
	}

	@Override @Answer(12738)
	protected long partTwo() {
		for(int roll : this.rolls) {
			this.boards.forEach(board -> board.mark(roll));
			if(this.boards.size() == 1 && this.boards.get(0).hasBingo()) return this.boards.get(0).winnerSum(roll);
			this.boards.removeIf(BingoBoard::hasBingo);
		}
		return -1;
	}

	private record BingoBoard(int[] board) {
		private void mark(int roll) {
			for(int i = 0; i < this.board.length; i++) {
				if(this.board[i] == roll) {
					this.board[i] |= Integer.MIN_VALUE;
					return;
				}
			}
		}

		private boolean isMarked(int x, int y) {
			return this.board[y + 5 * x] >>> 31 == 1;
		}

		private boolean hasBingo() {
			boolean wonRow = false, wonCol = false;
			for(int i = 0; i < 5 && !wonRow && !wonCol; i++) {
				wonRow = this.checkRow(i);
				wonCol = this.checkCol(i);
			}
			return wonRow || wonCol;
		}

		private boolean checkRow(int row) {
			for(int i = 0; i < 5; i++) {
				if(!this.isMarked(row, i)) return false;
			}
			return true;
		}

		private boolean checkCol(int col) {
			for(int i = 0; i < 5; i++) {
				if(!this.isMarked(i, col)) return false;
			}
			return true;
		}

		private int winnerSum(int winnerRoll) {
			int sum = 0;
			for(int entry : this.board) {
				if(entry >>> 31 == 0) sum += entry;
			}
			return sum * winnerRoll;
		}
	}
}