package pacman;


public class Main {

	public static void main(String[] args) {

		int seed = 10;
		PacmanGeneticAlgorithm ga = new PacmanGeneticAlgorithm(.5,.5,.5,.5, 2,4,seed);
		PacmanNeuralNetwork nn = ga.search();
		new Pacman(nn, true, nn.getSeed());
		
	}
}
