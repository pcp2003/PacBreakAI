package pacman;

import utils.GeneticAlgorithm;

public class Main {

	// GOOD SEED: 296
	private static int seed = 296;

	public static void watchGameplay() {

		GeneticAlgorithm ga = new PacmanGeneticAlgorithm(seed);
		new Pacman(ga.search(), true, seed);
	}

	public static void main(String[] args) {
		watchGameplay();
	}
}
