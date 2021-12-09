package wearblackallday.aoc.y2021;

import java.util.Arrays;

public class Day6 extends Calendar.Day {
	private final int[] population = this.parseInts(this.input[0].split(","));
	private static final int[][] TRANSITION_MATRIX = {
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

	@Override
	protected long partOne() {
		return populationGrowth(this.population, 80);
	}

	@Override
	protected long partTwo() {
		return populationGrowth(this.population, 256);
	}

	private static long populationGrowth(int[] population, int days) {
		long[] distribution = new long[9];
		for(int fish : population) {
			distribution[fish]++;
		}
		return Arrays.stream(matrixVectorProduct(matrixPower(TRANSITION_MATRIX, days - 1), distribution)).sum();
	}

	private static long[] matrixVectorProduct(int[][] matrix, long[] vector) {
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

	private static int[][] matrixMultiply(int matrix1[][], int matrix2[][]) {
		int size = matrix1.length;
		int matrix3[][] = new int[size][size];
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				matrix3[x][y] = 0;
				for (int z = 0; z < size; z++) {
					matrix3[x][y] += matrix1[x][z] * matrix2[z][y];
				}
			}
		}
		return matrix3;
	}

	private static int[][] matrixPower(int[][] matrix, int n) {
		int[][] result = matrix;
		while(n > 0) {
			if((n & 1) == 1) {
				result = matrixMultiply(result, matrix);
			}
			n >>= 1;
			matrix = matrixMultiply(matrix, matrix);
		}
		return result;
	}
}