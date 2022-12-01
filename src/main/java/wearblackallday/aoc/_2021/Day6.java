package wearblackallday.aoc._2021;

import wearblackallday.aoc.Calendar;
import wearblackallday.aoc.common.Answer;

import java.util.Arrays;

public class Day6 extends Calendar.Day {
	private final int[] population = this.parseInts(this.input[0].split(","));
	private static final long[][] TRANSITION_MATRIX = {
		{0, 1, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 1, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 1, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 1, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 1, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 1, 0, 0},
		{1, 0, 0, 0, 0, 0, 0, 1, 0},
		{0, 0, 0, 0, 0, 0, 0, 0, 1},
		{1, 0, 0, 0, 0, 0, 0, 0, 0},
	};

	@Override @Answer(359344)
	protected long partOne() {
		return this.populationGrowth(80);
	}

	@Override @Answer(1629570219571L)
	protected long partTwo() {
		return this.populationGrowth(256);
	}

	private long populationGrowth(int days) {
		long[] distribution = new long[9];
		for(int fish : this.population) {
			distribution[fish]++;
		}
		return Arrays.stream(matrixVectorProduct(matrixPower(TRANSITION_MATRIX, days), distribution)).sum();
	}

	protected static long[] matrixVectorProduct(long[][] matrix, long[] vector) {
		long[] result = new long[vector.length];
		for(int row = 0; row < matrix.length; row++) {
			long sum = 0L;
			for(int col = 0; col < matrix[0].length; col++) {
				sum += matrix[row][col] * vector[col];
			}
			result[row] = sum;
		}
		return result;
	}

	protected static long[][] matrixMultiply(long[][] matrix1, long[][] matrix2) {
		int size = matrix1.length;
		long matrix3[][] = new long[size][size];
		for(int x = 0; x < size; x++) {
			for(int y = 0; y < size; y++) {
				for(int z = 0; z < size; z++) {
					matrix3[x][y] += matrix1[x][z] * matrix2[z][y];
				}
			}
		}
		return matrix3;
	}

	protected static long[][] matrixPower(long[][] matrix, int n) {
		if(n == 1) return matrix;

		long[][] temp = matrixPower(matrix, n >> 1);
		long[][] square = matrixMultiply(temp, temp);

		if((n & 1) == 0) return square;
		else return matrixMultiply(square, matrix);
	}
}