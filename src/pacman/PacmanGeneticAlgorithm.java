package pacman;

import utils.Commons;

import java.util.Arrays;

public class PacmanGeneticAlgorithm {

    private final int POPULATION_SIZE = 100;
    private final int NUM_GENERATIONS = 100;
    private double MUTATION_CHANCE = .8;
    private double MUTATION_PERCENTAGE = .8;
    private double CUTOFF = .2;
    private double SELECTION_PARENTS_PERCENTAGE = .2;
    private int k_tournament = 5;

    private int seed ;

    private PacmanNeuralNetwork[] population = new PacmanNeuralNetwork[POPULATION_SIZE];

    // Construtor para testar CADA parâmetro durante o TREINO

    public PacmanGeneticAlgorithm(int seed) {
        this.seed = seed;
        generatePopulation();
    }

    private void generatePopulationFitness(PacmanNeuralNetwork[] gen) {
        for(PacmanNeuralNetwork nn : gen) nn.calculateFitness();
    }
    // Função para gerar o fitness da população para não violar as diretrizes do compareTo() no próximo passo (sort)
    public PacmanNeuralNetwork search() {


        for (int i = 0; i < NUM_GENERATIONS; i++) {
            System.out.print("Gen: " + i + " - ");
            generatePopulationFitness(population);
            Arrays.sort(population);

            System.out.println(population[POPULATION_SIZE-1].getFitness());

            PacmanNeuralNetwork[] newGeneration = new PacmanNeuralNetwork[POPULATION_SIZE];

            for (int j = 0; j < POPULATION_SIZE; j += 2) {

                PacmanNeuralNetwork parent1 = selectParent();
                PacmanNeuralNetwork parent2 = selectParent();
                PacmanNeuralNetwork[] children = crossover(parent1, parent2);

                newGeneration[j] = mutate(children[0]);
                newGeneration[j + 1] = mutate(children[1]);

            }

            if( i != NUM_GENERATIONS - 1)
                createNewPopulation(newGeneration);
        }
        System.out.println("-------");

        return population[POPULATION_SIZE - 1];
    }

    private void createNewPopulation(PacmanNeuralNetwork[] newgeneration) {

        generatePopulationFitness(newgeneration);
        Arrays.sort(newgeneration);
        int cutoff = (int) (POPULATION_SIZE * CUTOFF);

        int bestIndividuals = POPULATION_SIZE - cutoff;

        for (int i = 0; i != cutoff; i++) {
            population[i] = newgeneration[i + bestIndividuals];
        }

    }

    private PacmanNeuralNetwork mutate(PacmanNeuralNetwork individual) {
        double[] genes = individual.getNeuralNetwork();
        if (Math.random() < MUTATION_CHANCE) {
            for (int i = 0; i < MUTATION_PERCENTAGE * Commons.PACMAN_NETWORK_SIZE; i++) {
                int index = (int) (Math.random() * Commons.PACMAN_NETWORK_SIZE);
                genes[index] = (Math.random() * 2 - 1);
            }
        }
        individual.initializeParameters(genes);
        return individual;
    }


    private PacmanNeuralNetwork[] crossover(PacmanNeuralNetwork parent1, PacmanNeuralNetwork parent2) {
        double[] genes1 = parent1.getNeuralNetwork();
        double[] genes2 = parent2.getNeuralNetwork();
        double[] child1 = new double[genes1.length];
        double[] child2 = new double[genes2.length];

        int crossoverPoint = (int) (Math.random() * genes1.length);

        for (int i = 0; i < genes1.length; i++) {
            child1[i] = (i < crossoverPoint) ? genes1[i] : genes2[i];
            child2[i] = (i < crossoverPoint) ? genes2[i] : genes1[i];
        }

        PacmanNeuralNetwork offspring1 = new PacmanNeuralNetwork(child1, seed);
        PacmanNeuralNetwork offspring2 = new PacmanNeuralNetwork(child2, seed);
        return new PacmanNeuralNetwork[]{offspring1, offspring2};

    }


    // Realiza seleção por torneio
    private PacmanNeuralNetwork selectParent() {

        PacmanNeuralNetwork[] possibleParents = new PacmanNeuralNetwork[k_tournament];

        for (int i = 0; i != k_tournament; i++) {
            possibleParents[i] = population[(int) (POPULATION_SIZE - (Math.random() * POPULATION_SIZE * SELECTION_PARENTS_PERCENTAGE))];
        }

        Arrays.sort(possibleParents);

        return possibleParents[k_tournament - 1];

    }

    // generate random population
    private void generatePopulation() {
        for (int i = 0; i < population.length; i++) {
            population[i] = new PacmanNeuralNetwork(seed);
        }
    }

}

