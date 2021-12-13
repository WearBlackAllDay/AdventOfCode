package wearblackallday.aoc._2021;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Day4 extends Calendar.Day {
	private final int[] rolls = this.parseInts(this.input[0].split(","));
	private final int[][][] boards;

	public Day4() {
		int[][] rows = Arrays.stream(this.input)
			.skip(1)
			.filter(str -> !str.isEmpty())
			.map(line -> this.parseInts(line.trim().split("\s+")))
			.toArray(int[][]::new);

		int[][][] boards = new int[rows.length / 5][][];
		for(int i = 0; i < boards.length; i++) {
			int[][] board = new int[5][];
			for(int j = 0, r = i * 5; j < board.length; j++, r++) {
				board[j] = rows[r];
			}
			boards[i] = board;
		}
		this.boards = boards;
	}

	@Override
	protected long partOne() {
		for(int roll : this.rolls) {
			for(int[][] board : this.boards) {
				mark(roll, board);
				if(isWinner(board)) {
					return winnerSum(roll, board);
				}
			}
		}
		return -1;
	}

	@Override
	protected long partTwo() {
		Set<int[][]> candidates = new HashSet<>(Arrays.asList(this.boards));
		for(int roll : this.rolls) {
			candidates.forEach(board -> mark(roll, board));
			if(candidates.size() == 1 && isWinner(candidates.iterator().next())) {
				return winnerSum(roll, candidates.iterator().next());
			}
			candidates.removeIf(Day4::isWinner);
		}
		return -1;
	}

	private static void mark(int roll, int[][] board) {
		for(int[] row : board) {
			for(int i = 0; i < row.length; i++) {
				if(row[i] == roll) {
					row[i] |= 1 << 31;
					return;
				}
			}
		}
	}

	private static boolean isWinner(int[][] board) {
		for(int[] row : board) {
			boolean wonRow = true;
			for(int entry : row) {
				if(entry >>> 31 == 0) {
					wonRow = false;
					break;
				}
			}
			if(wonRow) return true;
		}

		for(int row = 0; row < 5; row++) {
			boolean wonCol = true;
			for(int col = 0; col < 5; col++) {
				if(board[col][row] >>> 31 == 0) {
					wonCol = false;
					break;
				}
			}
			if(wonCol) return true;
		}
//diagonals dont count. fuck me
//		boolean wonDiagonal = true;
//		boolean wonOtherDia = true;
//		for(int x = 0; x < 5; x++) {
//			if(board[x][x] >>> 31 == 0) {
//				wonDiagonal = false;
//			}
//			if(board[x][4 - x] >>> 31 == 0) {
//				wonOtherDia = false;
//			}
//		}
//
//		return wonDiagonal || wonOtherDia;
		return false;
	}

	private static int winnerSum(int winnerRoll, int[][] winnerBoard) {
		int sum = 0;
		for(int[] row : winnerBoard) {
			for(int entry : row) {
				if(entry >>> 31 == 0) {
					sum += entry & Integer.MAX_VALUE;
				}
			}
		}
		return sum * winnerRoll;
	}
}
