package utils;

import java.util.Random;

public abstract class GeneticAlgorithm {

    public static Random random = new Random();

    public int POPULATION_SIZE = Commons.POPULATION_SIZE;
    public int NUM_GENERATIONS = Commons.NUM_GENERATIONS;
    public double MUTATION_CHANCE;
    public double MUTATION_PERCENTAGE;
    public double CUTOFF;
    public double SELECTION_PARENTS_PERCENTAGE;
    public int k_tournament;
    public int k_point;


    public int seed;
    public NeuronalNetwork[] population;

    public GeneticAlgorithm(int seed) {

        this.population = new NeuronalNetwork[POPULATION_SIZE];

        this.seed = seed;

        double r = ((Math.random() * 0.5) + 0.01);
        System.out.println("MUTATION_CHANCE: " + r);
        this.MUTATION_CHANCE = r;

        r = ((Math.random() * 0.5) + 0.01);
        System.out.println("MUTATION_PERCENTAGE: " + r);
        this.MUTATION_PERCENTAGE = r;

        r = ((Math.random() * 0.9) + 0.1);
        System.out.println("CUTOFF: " + r);
        this.CUTOFF = r;

        r = ((Math.random() * 0.9) + 0.1);
        System.out.println("SELECTION_PARENTS_PERCENTAGE: " + r);
        this.SELECTION_PARENTS_PERCENTAGE = r;

        int ra = (int) ((Math.random() * 5) + 2);
        System.out.println("k_tournament: " + r);
        this.k_tournament = ra;

        ra = (int) ((Math.random() * 5) + 1);
        System.out.println("k_point: " + r);
        this.k_point = ra;


    }

    public GeneticAlgorithm(double MUTATION_CHANCE, double MUTATION_PERCENTAGE, double CUTOFF, double SELECTION_PARENTS_PERCENTAGE, double k_tournament, double k_Point, double seed) {

        this.population = new NeuronalNetwork[POPULATION_SIZE];

        this.MUTATION_CHANCE = MUTATION_CHANCE;
        this.MUTATION_PERCENTAGE = MUTATION_PERCENTAGE;
        this.CUTOFF = CUTOFF;
        this.SELECTION_PARENTS_PERCENTAGE = SELECTION_PARENTS_PERCENTAGE;
        this.k_tournament = (int) k_tournament;
        this.k_point = (int) k_Point;
        this.seed = (int) seed;

    }

    public abstract NeuronalNetwork search();

    public abstract void createNewPopulation(NeuronalNetwork[] newgeneration);

    public abstract NeuronalNetwork mutate(NeuronalNetwork child);

    public abstract NeuronalNetwork[] crossover(NeuronalNetwork parent1, NeuronalNetwork parent2);

    public abstract NeuronalNetwork selectParent();

    // lista com os 5 melhores fitness -> Usado no ficheiro randomValuesBreakout.txt

    public double[] LastFive() {
        double[] LF = new double[5];
        for (int i = 0; i != 5; i++) {
            LF[i] = population[POPULATION_SIZE - 1 - i].getFitness();
        }
        return LF;
    }

    // Função para gerar o fitness da população para não violar as diretrizes do compareTo() no próximo passo (sort)

    public void generatePopulationFitness(NeuronalNetwork[] Allindividuals) {
        for (NeuronalNetwork nn : Allindividuals) {
            nn.calculateAndStoreFitness(this.seed);
        }
    }

}



