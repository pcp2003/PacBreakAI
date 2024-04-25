package pacman;

import utils.Commons;
import utils.FileManager;
import utils.GeneticAlgorithm;
import utils.NeuronalNetwork;

import java.util.Arrays;

public class PacmanGeneticAlgorithm extends GeneticAlgorithm {

    public PacmanGeneticAlgorithm(int seed) {

        super(seed);

        generatePopulation();

    }

    // Constructors abaixo devem ser, idealmente, utilizados apenas na classe TesterGAs. Obrigado pela atenção, deus te abençoe.

    public PacmanGeneticAlgorithm(int seed, int mode) {

        super(seed, mode);

        generatePopulation();

        if (mode == Commons.PACMAN)
            throw new IllegalArgumentException("Não é possível utilizar o modo Pacman durante o breakout");

        if (mode == Commons.BREAKOUT )
            FileManager.saveParameters(this.MUTATION_CHANCE, this.MUTATION_PERCENTAGE, this.CUTOFF, this.SELECTION_PARENTS_PERCENTAGE, this.k_tournament, this.k_point, this.seed, "randomValuesBreakout.txt");

    }

    public PacmanGeneticAlgorithm(double MUTATION_CHANCE, double MUTATION_PERCENTAGE, double CUTOFF, double SELECTION_PARENTS_PERCENTAGE, double k_tournament, double k_Point, double seed, int mode) {

        super(MUTATION_CHANCE, MUTATION_PERCENTAGE, CUTOFF, SELECTION_PARENTS_PERCENTAGE, k_tournament, k_Point, seed, mode);

        generatePopulation();

        if (mode == Commons.PACMAN)
            throw new IllegalArgumentException("Não é possível utilizar o modo Pacman durante o breakout");

        if (mode == Commons.BREAKOUT )
            FileManager.saveParameters(this.MUTATION_CHANCE, this.MUTATION_PERCENTAGE, this.CUTOFF, this.SELECTION_PARENTS_PERCENTAGE, this.k_tournament, this.k_point, this.seed, "randomValuesBreakout.txt");

    }

    public NeuronalNetwork search() {

        for (int i = 0; i < NUM_GENERATIONS; i++) {

            generatePopulationFitness(population);

            Arrays.sort(population);

            System.out.println("Generation " + i + " Best Fitness " + population[POPULATION_SIZE - 1].getFitness());

            NeuronalNetwork[] newGeneration = new PacmanNeuralNetwork[POPULATION_SIZE];

            for (int j = 0; j < POPULATION_SIZE; j += 2) {
                NeuronalNetwork parent1 = selectParent();
                NeuronalNetwork parent2 = selectParent();
                NeuronalNetwork[] children = crossover(parent1, parent2);

                newGeneration[j] = mutate(children[0]);
                if (j + 1 < POPULATION_SIZE) {
                    newGeneration[j + 1] = mutate(children[1]);
                }
            }

            if (i != NUM_GENERATIONS - 1)
                createNewPopulation(newGeneration);

        }

        System.out.println("Last individual " + population[POPULATION_SIZE - 1].getFitness());
        return population[POPULATION_SIZE - 1];
    }

    //SELECTION_PERCENTAGE of the best children + (1-SELECTION_PERCENTAGE) of the best from the previous population

    public void createNewPopulation(NeuronalNetwork[] newgeneration) {

        generatePopulationFitness(newgeneration);

        Arrays.sort(newgeneration);
        int cutoff = (int) (POPULATION_SIZE * CUTOFF);

        int bestIndividuals = POPULATION_SIZE - cutoff;

        for (int i = 0; i != cutoff; i++) {
            population[i] = newgeneration[i + bestIndividuals];
        }

    }

    public NeuronalNetwork mutate(NeuronalNetwork individual) {
        double[] genes = individual.getNeuralNetwork();
        if (Math.random() < MUTATION_CHANCE) {
            for (int i = 0; i < MUTATION_PERCENTAGE * Commons.PACMAN_NETWORK_SIZE; i++) {
                int index = (int) (random.nextDouble() * Commons.PACMAN_NETWORK_SIZE);
                genes[index] = (random.nextDouble() * 2 - 1);
            }
        }
        individual.fillParametersWithValues(genes);
        return individual;
    }

    // k-point crossover

    public NeuronalNetwork[] crossover(NeuronalNetwork parent1, NeuronalNetwork parent2) {
        double[] genes1 = parent1.getNeuralNetwork();
        double[] genes2 = parent2.getNeuralNetwork();
        double[] child1 = new double[genes1.length];
        double[] child2 = new double[genes2.length];

        int crossoverPoint = (int) (Math.random() * genes1.length);

        for (int i = 0; i < genes1.length; i++) {
            child1[i] = (i < crossoverPoint) ? genes1[i] : genes2[i];
            child2[i] = (i < crossoverPoint) ? genes2[i] : genes1[i];
        }

        NeuronalNetwork offspring1 = new PacmanNeuralNetwork(child1);
        NeuronalNetwork offspring2 = new PacmanNeuralNetwork(child2);
        return new NeuronalNetwork[]{offspring1, offspring2};

    }

    // Realiza seleção por torneio

    public NeuronalNetwork selectParent() {

        NeuronalNetwork best = population[(int) (Math.random() * POPULATION_SIZE)];

        for (int i = 1; i < k_tournament; i++) {
            NeuronalNetwork c = population[(int) (Math.random() * POPULATION_SIZE)];

            if (c.getFitness() > best.getFitness())
                best = c;

        }
        return best;

    }

    public void generatePopulation() {
        for (int i = 0; i < population.length; i++) {
            population[i] = new PacmanNeuralNetwork();
        }
    }
}

