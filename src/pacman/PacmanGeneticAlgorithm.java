package pacman;

import utils.Commons;

import java.util.Arrays;

public class PacmanGeneticAlgorithm {

    private final int POPULATION_SIZE = 100;
    private final int NUM_GENERATIONS = 100;
    private double MUTATION_CHANCE = 0.20733758777463301;
    private double MUTATION_PERCENTAGE = 0.4944831677092099;
    private double CUTOFF = 0.613588965089243;
    private double SELECTION_PARENTS_PERCENTAGE = 0.8229914386919087;
    private int k_tournament = 5;
    private int k_point = 3;
    private int seed;


    private final int NeuralNetworkValuesSize = (Commons.BREAKOUT_STATE_SIZE * Commons.BREAKOUT_HIDDENDIM_SIZE) + Commons.BREAKOUT_HIDDENDIM_SIZE + (Commons.BREAKOUT_HIDDENDIM_SIZE * Commons.BREAKOUT_NUM_ACTIONS) + Commons.BREAKOUT_NUM_ACTIONS;

    private PacmanNeuralNetwork[] population = new PacmanNeuralNetwork[POPULATION_SIZE];

    // Construtor para testar CADA parâmetro durante o TREINO

    public PacmanGeneticAlgorithm(double MUTATION_CHANCE, double MUTATION_PERCENTAGE, double CUTOFF, double SELECTION_PARENTS_PERCENTAGE, double k_tournament, double k_Point, double seed) {
        this.MUTATION_CHANCE = MUTATION_CHANCE;
        this.MUTATION_PERCENTAGE = MUTATION_PERCENTAGE;
        this.CUTOFF = CUTOFF;
        this.SELECTION_PARENTS_PERCENTAGE = SELECTION_PARENTS_PERCENTAGE;
        this.k_tournament = (int) k_tournament;
        this.k_point = (int) k_Point;
        this.seed = (int) seed;

        generatePopulation();
    }

    // Função para gerar o fitness da população para não violar as diretrizes do compareTo() no próximo passo (sort)
    public PacmanNeuralNetwork search() {

        for (int i = 0; i < NUM_GENERATIONS; i++) {
            System.out.println("Gen: " + i);
            Arrays.sort(population);

            System.out.println("Generation " + i + ": " + population[0].getFitness());

            PacmanNeuralNetwork[] newGeneration = new PacmanNeuralNetwork[POPULATION_SIZE];

            for (int j = 0; j < POPULATION_SIZE; j += 2) {
                PacmanNeuralNetwork parent1 = selectParent();
                PacmanNeuralNetwork parent2 = selectParent();
                PacmanNeuralNetwork[] children = crossover(parent1, parent2);

                newGeneration[j] = mutate(children[0]);
                newGeneration[j + 1] = mutate(children[1]);
            }

            if (i != NUM_GENERATIONS - 1)
                createNewPopulation(newGeneration);


        }
        return population[POPULATION_SIZE - 1];
    }


    //SELECTION_PERCENTAGE of the best children +
    //(1-SELECTION_PERCENTAGE) of the best from the previous population

    // Ex: Deixo os 25 melhores da populacao anterior, e substituo os 25 piores da populacao anterior pelo 25 melhores da nova geracao

    private void createNewPopulation(PacmanNeuralNetwork[] newgeneration) {

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
            possibleParents[i] = population[(int) (Math.random() * POPULATION_SIZE * SELECTION_PARENTS_PERCENTAGE)];
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

