package wearblackallday.aoc.y2021;

import java.util.*;

public class Day9 extends Calendar.Day {
	private final int[] heightMap = Arrays.stream(this.input)
		.flatMapToInt(String::chars)
		.map(Character::getNumericValue)
		.toArray();
	private final int width = this.input[0].length(), height = this.input.length;

	@Override
	protected long partOne() {
		int riskLevel = 0;
		for(int y = 0; y < this.height; y++) {
			for(int x = 0; x < this.width; x++) {
				if(this.isLowestPoint(x, y)) riskLevel += this.heightAt(x, y) + 1;
			}
		}
		return riskLevel;
	}

	@Override
	protected long partTwo() {
		BitSet map = new BitSet(this.width * this.height);
		List<Integer> basins = new ArrayList<>();
		for(int y = 0; y < this.height; y++) {
			for(int x = 0; x < this.width; x++) {
				if(this.isLowestPoint(x, y)) {
					int prevSum = map.cardinality();
					this.mapBasin(x, y, this.heightAt(x, y) , map);
					basins.add(map.cardinality() - prevSum);
				}
			}
		}
		Collections.sort(basins);
		return basins.get(basins.size() - 1)
			 * basins.get(basins.size() - 2)
			 * basins.get(basins.size() - 3);
	}

	private void mapBasin(int x, int y, int prevHeight, BitSet map) {
		if(!this.inBounds(x, y)) return;
		if(map.get(this.toIndex(x, y))) return;
		int height = this.heightAt(x, y);
		if(height == 9) return;
		if(height < prevHeight) return;

		map.set(this.toIndex(x, y));
		this.mapBasin(x + 1, y, height, map);
		this.mapBasin(x - 1, y, height, map);
		this.mapBasin(x, y + 1, height, map);
		this.mapBasin(x, y - 1, height, map);
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

	private boolean inBounds(int x, int y) {
		return x >= 0 && x < this.width && y >= 0 && y < this.height;
	}

	private int heightAt(int x, int y) {
		return this.heightMap[this.toIndex(x, y)];
	}

	private int toIndex(int x, int y) {
		return x + this.width * y;
	}
}