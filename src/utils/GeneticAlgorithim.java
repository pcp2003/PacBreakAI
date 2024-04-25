package utils;

public abstract class GeneticAlgorithim {

    public int POPULATION_SIZE = 100;
    public int NUM_GENERATIONS = 100;
    public double MUTATION_CHANCE = 0.20733758777463301;
    public double MUTATION_PERCENTAGE = 0.4944831677092099;
    public double CUTOFF = 0.613588965089243;
    public double SELECTION_PARENTS_PERCENTAGE = 0.2; //0.8229914386919087
    public int k_tournament = 5;
    public int k_point = 3;

    public int seed;
    public NeuronalNetwork[] population;

    public GeneticAlgorithim(int seed) {

        this.population = new NeuronalNetwork[POPULATION_SIZE];

        this.seed = seed;


    }

    // Constructors abaixo devem ser, idealmente, utilizados apenas na classe TesterGAs. Obrigado pela atenção, deus te abençoe.

    // O contructor abaixo pode ser utilizado no [mode == 0 -> apenas gera uma GA aleatória, mode == COMMONS.BREAKOUT -> modo de teste do BREAKOUT (guarda no ficheiro | apenas deve ser utilizado em TestesGA), mode == COMMONS.PACMAN -> modo de teste do PACMAN (guarda no ficheiro | apenas deve ser utilizado em TestesGA) ]

    public GeneticAlgorithim (int seed, int mode) {

        this.population = new NeuronalNetwork[POPULATION_SIZE];

        this.seed = seed;

        double r = ((Math.random() * 0.5) + 0.01);
        System.out.println("First Random Created: " + r);
        this.MUTATION_CHANCE = r;

        r = ((Math.random() * 0.5) + 0.01);
        System.out.println("Second Random Created: " + r);
        this.MUTATION_PERCENTAGE = r;

        r = ((Math.random() * 0.9) + 0.1);
        System.out.println("Third Random Created: " + r);
        this.CUTOFF = r;

        r = ((Math.random() * 0.9) + 0.1);
        System.out.println("Fourth Random Created: " + r);
        this.SELECTION_PARENTS_PERCENTAGE = r;

        int ra = (int) ((Math.random() * 5) + 2);
        System.out.println("Fifth Random Created: " + r);
        this.k_tournament = ra;

        ra = (int) ((Math.random() * 5) + 1);
        System.out.println("Fourth Random Created: " + r);
        this.k_point = ra;



    }

    // O contructor abaixo pode ser utilizado no [mode == COMMONS.BREAKOUT -> modo de teste do BREAKOUT (guarda no ficheiro | apenas deve ser utilizado em TestesGA), mode == COMMONS.PACMAN -> modo de teste do PACMAN (guarda no ficheiro | apenas deve ser utilizado em TestesGA) ]

    public GeneticAlgorithim(double MUTATION_CHANCE, double MUTATION_PERCENTAGE, double CUTOFF, double SELECTION_PARENTS_PERCENTAGE, double k_tournament, double k_Point, double seed, int mode) {

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



