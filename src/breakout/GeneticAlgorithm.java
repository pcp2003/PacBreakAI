package breakout;

import utils.Commons;

import java.util.Arrays;
import java.util.Random;

public class GeneticAlgorithm {

    private final int POPULATION_SIZE = 100;
    private final int NUM_GENERATIONS = 100;
    private double MUTATION_CHANCE = 0.20733758777463301;
    private double MUTATION_PERCENTAGE = 0.4944831677092099;
    private double CUTOFF = 0.613588965089243;
    private double SELECTION_PARENTS_PERCENTAGE = 0.2; //0.8229914386919087
    private int k_tournament = 5;
    private int k_point = 3;
    private int seed;


    private final int NeuralNetworkValuesSize = (Commons.BREAKOUT_STATE_SIZE * Commons.BREAKOUT_HIDDENDIM_SIZE) + Commons.BREAKOUT_HIDDENDIM_SIZE + (Commons.BREAKOUT_HIDDENDIM_SIZE * Commons.BREAKOUT_NUM_ACTIONS) + Commons.BREAKOUT_NUM_ACTIONS;

    private NeuronalNetwork[] population = new NeuronalNetwork[POPULATION_SIZE];

    // Construtor que gera parâmetros ALEATÓRIOS -> type == 0
    // Construtor para testar o MELHOR parâmetro! -> type == 1

    public GeneticAlgorithm(int seed, int type) {

        if (type == 0) {
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

            generatePopulation();

            FileManager.saveParameters(this.MUTATION_CHANCE, this.MUTATION_PERCENTAGE, this.CUTOFF, this.SELECTION_PARENTS_PERCENTAGE, this.k_tournament, this.k_point, this.seed, "randomValues.txt");

        } else {

            this.seed = seed;

            generatePopulation();

        }

    }

    // Construtor para testar CADA parâmetro durante o TREINO

    public GeneticAlgorithm(double MUTATION_CHANCE, double MUTATION_PERCENTAGE, double CUTOFF, double SELECTION_PARENTS_PERCENTAGE, double k_tournament, double k_Point, double seed) {
        this.MUTATION_CHANCE = MUTATION_CHANCE;
        this.MUTATION_PERCENTAGE = MUTATION_PERCENTAGE;
        this.CUTOFF = CUTOFF;
        this.SELECTION_PARENTS_PERCENTAGE = SELECTION_PARENTS_PERCENTAGE;
        this.k_tournament = (int) k_tournament;
        this.k_point = (int) k_Point;
        this.seed = (int) seed;

        generatePopulation();

        FileManager.saveParameters(this.MUTATION_CHANCE, this.MUTATION_PERCENTAGE, this.CUTOFF, this.SELECTION_PARENTS_PERCENTAGE, this.k_tournament, this.k_point, this.seed, "bestParameters.txt");
    }

    // Função para gerar o fitness da população para não violar as diretrizes do compareTo() no próximo passo (sort)

    public void generatePopulationFitness(NeuronalNetwork[] Allindividuals) {
        for (NeuronalNetwork nn : Allindividuals) {
            nn.calculateAndStoreFitness(this.seed);
        }
    }

    public NeuronalNetwork search() {

        for (int i = 0; i < NUM_GENERATIONS; i++) {

            generatePopulationFitness(population);

            Arrays.sort(population);

            System.out.println("Generation " + i + " Best Fitness " + population[POPULATION_SIZE - 1].getFitness());

            NeuronalNetwork[] newGeneration = new NeuronalNetwork[POPULATION_SIZE];

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

    // lista com os 5 melhores fitness -> Usado no ficheiro randomValues.txt

    public double[] LastFive() {
        double[] LF = new double[5];
        for (int i = 0; i != 5; i++) {
            LF[i] = population[POPULATION_SIZE - 1 - i].getFitness();
        }
        return LF;
    }

    //SELECTION_PERCENTAGE of the best children + (1-SELECTION_PERCENTAGE) of the best from the previous population

    private void createNewPopulation(NeuronalNetwork[] newgeneration) {

        generatePopulationFitness(newgeneration);

        Arrays.sort(newgeneration);
        int cutoff = (int) (POPULATION_SIZE * CUTOFF);

        int bestIndividuals = POPULATION_SIZE - cutoff;

        for (int i = 0; i != cutoff; i++) {
            population[i] = newgeneration[i + bestIndividuals];
        }

    }

    private NeuronalNetwork mutate(NeuronalNetwork child) {
        Random random = new Random();

        if (Math.random() <= MUTATION_CHANCE) {
            double[] childNewPos = child.getNeuralNetwork();
            int genesToMutate = (int) (NeuralNetworkValuesSize * MUTATION_PERCENTAGE);

            for (int i = 0; i < genesToMutate; i++) {

                // Escolhendo um gene aleatório para mutação
                int geneIndex = random.nextInt(NeuralNetworkValuesSize);

                // Mutação usando distribuição normal, considera-se uma variação pequena, por exemplo, com média 0 e desvio padrão 0.1
                double mutationAmount = random.nextGaussian() * 0.05;

                // Aplica a mutação ao gene selecionado
                childNewPos[geneIndex] += mutationAmount;

                // Garantir que o valor mutado não ultrapasse seus limites esperados
                childNewPos[geneIndex] = Math.min(Math.max(childNewPos[geneIndex], -1), 1);
            }

            return new NeuronalNetwork(childNewPos);
        }
        return child;
    }

    // k-point crossover

    private NeuronalNetwork[] crossover(NeuronalNetwork parent1, NeuronalNetwork parent2) {
        int numberOfChildren = 2;
        int[] randoms = new int[k_point];

        // Gerar pontos de crossover aleatórios
        for (int i = 0; i < k_point; i++) {
            randoms[i] = (int) (Math.random() * NeuralNetworkValuesSize);
        }
        // Ordenar os pontos de crossover
        Arrays.sort(randoms);

        NeuronalNetwork[] children = new NeuronalNetwork[numberOfChildren];
        double[] child1 = new double[NeuralNetworkValuesSize];
        double[] child2 = new double[NeuralNetworkValuesSize];
        double[] parent1_positions = parent1.getNeuralNetwork();
        double[] parent2_positions = parent2.getNeuralNetwork();

        // Inicializa variável para controlar de qual pai copiar
        boolean copyFromParent1 = true;

        // Inicia no primeiro gene
        int startGeneIndex = 0;

        // Para cada ponto de crossover
        for (int crossoverPoint : randoms) {
            // Troca os genes entre os pontos de crossover
            for (int geneIndex = startGeneIndex; geneIndex < crossoverPoint; geneIndex++) {
                if (copyFromParent1) {
                    child1[geneIndex] = parent1_positions[geneIndex];
                    child2[geneIndex] = parent2_positions[geneIndex];
                } else {
                    child1[geneIndex] = parent2_positions[geneIndex];
                    child2[geneIndex] = parent1_positions[geneIndex];
                }
            }

            // Alterna a fonte de cópia após cada ponto de crossover
            copyFromParent1 = !copyFromParent1;
            startGeneIndex = crossoverPoint; // Atualiza o índice de início para o próximo segmento
        }

        // Copiar o segmento final após o último ponto de crossover
        for (int geneIndex = startGeneIndex; geneIndex < NeuralNetworkValuesSize; geneIndex++) {
            if (copyFromParent1) {
                child1[geneIndex] = parent1_positions[geneIndex];
                child2[geneIndex] = parent2_positions[geneIndex];
            } else {
                child1[geneIndex] = parent2_positions[geneIndex];
                child2[geneIndex] = parent1_positions[geneIndex];
            }
        }

        // Criar redes neurais filhas com os novos arrays de genes
        children[0] = new NeuronalNetwork(child1);
        children[1] = new NeuronalNetwork(child2);

        return children;
    }

    // Realiza seleção por torneio

    private NeuronalNetwork selectParent() {

        NeuronalNetwork[] possibleParents = new NeuronalNetwork[k_tournament];

        for (int i = 0; i != k_tournament; i++) {

            possibleParents[i] = population[(int) (POPULATION_SIZE - (Math.random() * POPULATION_SIZE * SELECTION_PARENTS_PERCENTAGE))];
        }

        Arrays.sort(possibleParents);

        return possibleParents[k_tournament - 1];

    }

    // generate random population
    private void generatePopulation() {
        for (int i = 0; i < population.length; i++) {
            population[i] = new NeuronalNetwork();
        }
    }

}

