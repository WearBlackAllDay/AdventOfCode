package wearblackallday.aoc._2021;

import java.util.AbstractMap.SimpleEntry;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day12 extends Calendar.Day {
	private final Map<String, Set<String>> connections = Arrays.stream(this.input)
		.map(line -> new SimpleEntry<>(line.substring(0, line.indexOf('-')), line.substring(line.indexOf('-') + 1)))
		.flatMap(entry -> Stream.of(entry, new SimpleEntry<>(entry.getValue(), entry.getKey())))
		.filter(entry -> !entry.getValue().equals("start"))
		.collect(Collectors.groupingBy(SimpleEntry::getKey,
			Collectors.mapping(SimpleEntry::getValue, Collectors.toSet())));
	private int totalPaths = 0;

	@Override //4186
	protected long partOne() {
		this.walkPath("start", new ArrayList<>(), false);
		return this.totalPaths;
	}

	@Override //92111
	protected long partTwo() {
		this.totalPaths = 0;
		this.walkPath("start", new ArrayList<>(), true);
		return this.totalPaths;
	}

	private void walkPath(String cave, List<String> visited, boolean canVisitTwice) {
		for(String neighborCave : this.connections.get(cave)) {
			if(neighborCave.equals("end")) {
				this.totalPaths++;
				continue;
			}
			boolean visitTwice = canVisitTwice;
			if(visited.contains(neighborCave)) {
				if(visitTwice) visitTwice = false;
				else continue;
			}
			List<String> copy = new ArrayList<>(visited);
			if(neighborCave.equals(neighborCave.toLowerCase())) copy.add(neighborCave);
			this.walkPath(neighborCave, copy, visitTwice);
		}
	}
}