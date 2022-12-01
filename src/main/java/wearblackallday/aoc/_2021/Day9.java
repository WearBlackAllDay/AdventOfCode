package wearblackallday.aoc._2021;

import wearblackallday.aoc.Calendar;
import wearblackallday.aoc.common.Answer;
import wearblackallday.aoc.common.Grid;

import java.util.*;

public class Day9 extends Calendar.Day implements Grid {
	private final int[] heightMap = Arrays.stream(this.input)
		.flatMapToInt(String::chars)
		.map(Character::getNumericValue)
		.toArray();

	@Override @Answer(425)
	protected long partOne() {
		int[] riskLevel = {0};
		this.forEach((x, y) -> {
			if(this.isLowestPoint(x, y)) riskLevel[0] += this.heightAt(x, y) + 1;
		});
		return riskLevel[0];
	}

	@Override @Answer(1135260)
	protected long partTwo() {
		BitSet basinMap = new BitSet(this.size());
		List<Integer> basins = new ArrayList<>();
		this.forEach((x, y) -> {
			if(this.isLowestPoint(x, y)) {
				int prevSum = basinMap.cardinality();
				this.mapBasin(x, y, this.heightAt(x, y) , basinMap);
				basins.add(basinMap.cardinality() - prevSum);
			}
		});
		Collections.sort(basins);
		return basins.get(basins.size() - 1)
			 * basins.get(basins.size() - 2)
			 * basins.get(basins.size() - 3);
	}

	private void mapBasin(int x, int y, int prevHeight, BitSet basinMap) {
		if(!this.inBounds(x, y) || basinMap.get(this.toIndex(x, y))) return;
		int height = this.heightAt(x, y);
		if(height == 9 || height < prevHeight) return;

		basinMap.set(this.toIndex(x, y));
		this.forAdjoining(x, y, (x1, y1) -> this.mapBasin(x1, y1, prevHeight, basinMap));
	}

	private boolean isLowestPoint(int x, int y) {
		int height = this.heightMap[this.toIndex(x, y)];
		if(height == 9) return false;
		if(this.inBounds(x + 1, y) && this.heightAt(x + 1, y) <= height) return false;
		if(this.inBounds(x - 1, y) && this.heightAt(x - 1, y) <= height) return false;
		if(this.inBounds(x, y + 1) && this.heightAt(x, y + 1) <= height) return false;
		if(this.inBounds(x, y - 1) && this.heightAt(x, y - 1) <= height) return false;
		return true;
	}

	private int heightAt(int x, int y) {
		return this.heightMap[this.toIndex(x, y)];
	}

	@Override
	public int width() {
		return this.input[0].length();
	}

	@Override
	public int height() {
		return this.input.length;
	}
}