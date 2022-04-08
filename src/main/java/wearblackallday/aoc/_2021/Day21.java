package wearblackallday.aoc._2021;

import wearblackallday.aoc.common.Answer;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day21 extends Calendar.Day {

	private final int player1Start = this.input[0].charAt(this.input[0].length() - 1) - 48;
	private final int player2Start = this.input[1].charAt(this.input[1].length() - 1) - 48;

	@Override @Answer(998088)
	protected long partOne() {
		Player player1 = new Player(this.player1Start);
		Player player2 = new Player(this.player2Start);
		int roll = 6;
		int totalRolls = 3;

		while(((totalRolls & 1) == 1 ? player1 : player2).add(roll).score < 1000) {
			totalRolls += 3;
			roll = nextRoll(roll);
		}
		return totalRolls * ((totalRolls & 1) == 0 ? player1 : player2).score;
	}

	@Override @Answer(306621346123766L)
	protected long partTwo() {
		long player1Wins = 0L;
		long player2Wins = 0L;

		Queue<Game> realities = new PriorityQueue<>(Comparator.comparingInt((Game g) -> g.player1.score + g.player2.score).reversed());
		realities.add(new Game(new Player(this.player1Start), new Player(this.player2Start), true, 1));

		while(!realities.isEmpty()) {
			Game possibleGame = realities.poll();

			if(possibleGame.player1.score >= 21) player1Wins += possibleGame.universes;
			else if(possibleGame.player2.score >= 21) player2Wins += possibleGame.universes;
			else realities.addAll(possibleGame.rollDiracDie());
		}
		return Math.max(player1Wins, player2Wins);
	}

	private static int nextRoll(int prevRoll) {
		return switch(prevRoll) {
			case 291 -> 200; // 96+97+98 -> 99+100+1
			case 294 -> 103; // 97+98+99 -> 100+1+2
			case 297 -> 6; // 98+99+100 -> 1+2+3
			default -> prevRoll + 9;
		};
	}

	private static class Player {
		private int position, score;

		private Player(Player other) {
			this.position = other.position;
			this.score = other.score;
		}

		private Player(int startingPosition) {
			this.position = startingPosition;
			this.score = 0;
		}

		private Player add(int roll) {
			this.position = (this.position + roll) % 10;
			if(this.position == 0) this.position = 10;
			this.score += this.position;
			return this;
		}

	}

	private record Game(Player player1, Player player2, boolean player1Turn, long universes) {
		private static final Map<Integer, Long> SUM_FREQUENCY;

		static {
			record Permutation(int a, int b, int c) {
				int sum() {
					return this.a + this.b + this.c;
				}
			}
			Stream.Builder<Permutation> permutationBuilder = Stream.builder();

			for(int a = 1; a <= 3; a++)
				for(int b = 1; b <= 3; b++)
					for(int c = 1; c <= 3; c++)
						permutationBuilder.add(new Permutation(a, b, c));

			SUM_FREQUENCY = permutationBuilder.build()
				.collect(Collectors.groupingBy(Permutation::sum, Collectors.counting()));
		}

		private Game advance(int rollSum, long universes) {
			Game next = new Game(new Player(this.player1), new Player(this.player2), !this.player1Turn, this.universes * universes);
			(this.player1Turn ? next.player1 : next.player2).add(rollSum);
			return next;
		}

		private List<Game> rollDiracDie() {
			return SUM_FREQUENCY.entrySet().stream()
				.map(sf -> this.advance(sf.getKey(), sf.getValue()))
				.toList();
		}
	}
}
