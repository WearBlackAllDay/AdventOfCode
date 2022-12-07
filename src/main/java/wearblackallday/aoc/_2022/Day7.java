package wearblackallday.aoc._2022;

import wearblackallday.aoc.Calendar;
import wearblackallday.aoc.common.Answer;

import java.util.*;
import java.util.function.Consumer;

public class Day7 extends Calendar.Day {

	private final File root;

	public Day7() {
		File currentDir = null;

		for(String line : this.input) {
			switch(line.charAt(0)) {
				case '$' -> {
					if(line.charAt(2) == 'c') {
						String fileName = line.substring(5);
						if("..".equals(fileName)) currentDir = currentDir.parent;
						else if(currentDir != null) {
							currentDir = currentDir.contents.stream()
								.filter(file -> file.name.equals(fileName))
								.findAny()
								.orElse(new File(currentDir, fileName));
						} else currentDir = new File(currentDir, fileName);
					}
				}
				case 'd' -> currentDir.contents.add(new File(currentDir, line.substring(4)));
				default -> {
					File dataFile = new File(currentDir, line.substring(line.indexOf(' ') + 1));
					dataFile.size = Integer.parseInt(line.substring(0, line.indexOf(' ')));
					currentDir.contents.add(dataFile);
				}
			}
		}

		for(File file = currentDir; file.parent != null; file = file.parent) {
			currentDir = file.parent;
		}

		this.root = currentDir;
	}

	@Override @Answer(1642503)
	protected long partOne() {
		int[] dirSize = {0};

		this.root.forEachNested(file -> {
			if(file.isDirectory()) {
				int size = file.getTotalSize();
				if(size <= 100_000) dirSize[0] += size;
			}
		});

		return dirSize[0];
	}

	@Override @Answer(6999588)
	protected long partTwo() {
		int totalSize = 70_000_000;
		int requiredSize = 30_000_000;
		int usedSize = this.root.getTotalSize();
		int unusedSize = totalSize - usedSize;
		int minimumSizeNeeded = requiredSize - unusedSize;

		Queue<Integer> folderSize = new PriorityQueue<>();

		this.root.forEachNested(file -> {
			if(file.isDirectory()) {
				int size = file.getTotalSize();
				if(size >= minimumSizeNeeded) folderSize.add(size);
			}
		});

		return folderSize.peek();
	}


	private static class File {
		private final File parent;
		private final String name;
		private int size = 0;
		private final List<File> contents = new ArrayList<>();

		private File(File parent, String name) {
			this.parent = parent;
			this.name = name;
		}

		private boolean isDirectory() {
			return this.size <= 0;
		}

		private void forEachNested(Consumer<File> code) {
			code.accept(this);
			for(File child : this.contents) {
				child.forEachNested(code);
			}
		}

		private int getTotalSize() {
			return this.size + this.contents.stream().mapToInt(File::getTotalSize).sum();
		}

		private int getIndent() {
			int indent = 0;
			File file = this;
			while(file.parent != null) {
				file = file.parent;
				indent++;
			}

			return indent + 1;
		}

		@Override
		public String toString() {
			StringBuilder stringBuilder = new StringBuilder("-\s")
				.append(this.name)
				.append("\s(")
				.append(this.isDirectory() ? "dir" : ("file, size=" + this.size))
				.append(")\n");


			for(File child : this.contents) {
				stringBuilder.append("\s\s".repeat(this.getIndent()));
				stringBuilder.append(child);
			}

			return stringBuilder.toString();
		}
	}
}
