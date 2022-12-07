package wearblackallday.aoc._2022;

import wearblackallday.aoc.Calendar;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day5 extends Calendar.Day {

	private final int cutOff = (int)IntStream.iterate(0, i -> !this.input[i].isEmpty(), i -> i + 1).count();
	private final Instruction[] instructions = Arrays.stream(this.input, this.cutOff + 1, this.input.length)
		.map(Instruction::of)
		.toArray(Instruction[]::new);

	@Override //MQTPGLLDN
	protected long partOne() {
		Deque<Character>[] stacks = this.getDefaultStacks();

		for(Instruction instruction : this.instructions) {
			for(int i = 0; i < instruction.quantity; i++) {
				stacks[instruction.toIndex].addFirst(stacks[instruction.fromIndex].removeFirst());
			}
		}

		printStacks(stacks);

		return 0;
	}

	@Override //LVZPSTTCZ
	protected long partTwo() {
		Deque<Character>[] stacks = this.getDefaultStacks();
		Deque<Character> buffer = new ArrayDeque<>();

		for(Instruction instruction : this.instructions) {
			for(int i = 0; i < instruction.quantity; i++) {
				buffer.addFirst(stacks[instruction.fromIndex].removeFirst());
			}
			while(!buffer.isEmpty()) stacks[instruction.toIndex].addFirst(buffer.removeFirst());
		}

		printStacks(stacks);

		return 0;
	}

	private Deque<Character>[] getDefaultStacks() {
		Deque<Character>[] stacks = new Deque[this.cutOff];
		Arrays.setAll(stacks, ArrayDeque::new);

		for(int line = this.cutOff - 2; line >= 0; line--) {
			String packages = this.input[line];
			for(int p = 1, i = 0; p < packages.length(); p += 4, i++) {
				char packet = packages.charAt(p);
				if(packet != ' ') stacks[i].addFirst(packet);
			}
		}

		return stacks;
	}

	private static void printStacks(Deque<Character>[] stacks) {
		for(Deque<Character> stack : stacks) {
			System.out.print(stack.getFirst());
		}
		System.out.println();
	}

	private record Instruction(int quantity, int fromIndex, int toIndex) {
		private static Instruction of(String instruction) {
			String[] ints = instruction.split("\\D+");
			return new Instruction(Integer.parseInt(ints[1]), Integer.parseInt(ints[2]) - 1, Integer.parseInt(ints[3]) - 1);
		}
	}
}
