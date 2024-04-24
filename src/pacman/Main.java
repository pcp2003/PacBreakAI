package pacman;


public class Main {

	public static void main(String[] args) {

		int seed = 10;
		PacmanGeneticAlgorithm ga = new PacmanGeneticAlgorithm(seed);
		PacmanNeuralNetwork nn = ga.search();
		new Pacman(nn, true, nn.getSeed());
		
	}
}
