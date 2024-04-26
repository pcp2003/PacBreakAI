package breakout;

import utils.Commons;
import utils.FileManager;
import utils.GeneticAlgorithm;
import utils.NeuronalNetwork;

import java.util.Arrays;
import java.util.Random;

public class BreakoutGeneticAlgorithm extends GeneticAlgorithm {


    public BreakoutGeneticAlgorithm(int seed) {

        super(Commons.MUTATION_CHANCE_BREAKOUT, Commons.MUTATION_PERCENTAGE_BREAKOUT, Commons.CUTOFF_BREAKOUT, Commons.SELECTION_PARENTS_PERCENTAGE_BREAKOUT, Commons.k_tournament_BREAKOUT, Commons.k_point_BREAKOUT, seed);

        generatePopulation();

    }

    // Constructors abaixo devem ser, idealmente, utilizados apenas na classe TesterGAs. Obrigado pela atenção, deus te abençoe.

    public BreakoutGeneticAlgorithm(int seed, int mode) {

        super(seed);

        generatePopulation();


        if (mode == Commons.BREAKOUT )
            FileManager.saveParameters(this.MUTATION_CHANCE, this.MUTATION_PERCENTAGE, this.CUTOFF, this.SELECTION_PARENTS_PERCENTAGE, this.k_tournament, this.k_point, this.seed, "randomValuesBreakout.txt");
        else throw new IllegalArgumentException("Não é possível utilizar um modo diferente do breakout");
    }

    public BreakoutGeneticAlgorithm(double MUTATION_CHANCE, double MUTATION_PERCENTAGE, double CUTOFF, double SELECTION_PARENTS_PERCENTAGE, double k_tournament, double k_Point, double seed, int mode) {

        super(MUTATION_CHANCE, MUTATION_PERCENTAGE, CUTOFF, SELECTION_PARENTS_PERCENTAGE, k_tournament, k_Point, seed);

        generatePopulation();

        if (mode == Commons.BREAKOUT )
            FileManager.saveParameters(this.MUTATION_CHANCE, this.MUTATION_PERCENTAGE, this.CUTOFF, this.SELECTION_PARENTS_PERCENTAGE, this.k_tournament, this.k_point, this.seed, "bestParametersBreakout.txt");
        else throw new IllegalArgumentException("Não é possível utilizar um modo diferente do breakout");
    }

    public NeuronalNetwork search() {

        for (int i = 0; i < NUM_GENERATIONS; i++) {

            generatePopulationFitness(population);

            Arrays.sort(population);

            System.out.println("Generation " + i + " Best Fitness " + population[POPULATION_SIZE - 1].getFitness());

            NeuronalNetwork[] newGeneration = new BreakoutNeuralNetwork[POPULATION_SIZE];

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

    public NeuronalNetwork mutate(NeuronalNetwork child) {
        Random random = new Random();

        if (Math.random() <= MUTATION_CHANCE) {
            double[] childNewPos = child.getNeuralNetwork();
            int genesToMutate = (int) (Commons.BREAKOUT_NETWORK_SIZE * MUTATION_PERCENTAGE);

            for (int i = 0; i < genesToMutate; i++) {

                // Escolhendo um gene aleatório para mutação
                int geneIndex = random.nextInt(Commons.BREAKOUT_NETWORK_SIZE);

                // Mutação usando distribuição normal, considera-se uma variação pequena, por exemplo, com média 0 e desvio padrão 0.1
                double mutationAmount = random.nextGaussian() * 0.05;

                // Aplica a mutação ao gene selecionado
                childNewPos[geneIndex] += mutationAmount;

                // Garantir que o valor mutado não ultrapasse seus limites esperados
                childNewPos[geneIndex] = Math.min(Math.max(childNewPos[geneIndex], -1), 1);
            }

            return new BreakoutNeuralNetwork(childNewPos);
        }
        return child;
    }

    // k-point crossover

    public NeuronalNetwork[] crossover(NeuronalNetwork parent1, NeuronalNetwork parent2) {
        int numberOfChildren = 2;
        int[] randoms = new int[k_point];

        // Gerar pontos de crossover aleatórios
        for (int i = 0; i < k_point; i++) {
            randoms[i] = (int) (Math.random() * Commons.BREAKOUT_NETWORK_SIZE);
        }
        // Ordenar os pontos de crossover
        Arrays.sort(randoms);

        NeuronalNetwork[] children = new BreakoutNeuralNetwork[numberOfChildren];
        double[] child1 = new double[Commons.BREAKOUT_NETWORK_SIZE];
        double[] child2 = new double[Commons.BREAKOUT_NETWORK_SIZE];
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
        for (int geneIndex = startGeneIndex; geneIndex < Commons.BREAKOUT_NETWORK_SIZE; geneIndex++) {
            if (copyFromParent1) {
                child1[geneIndex] = parent1_positions[geneIndex];
                child2[geneIndex] = parent2_positions[geneIndex];
            } else {
                child1[geneIndex] = parent2_positions[geneIndex];
                child2[geneIndex] = parent1_positions[geneIndex];
            }
        }

        // Criar redes neurais filhas com os novos arrays de genes
        children[0] = new BreakoutNeuralNetwork(child1);
        children[1] = new BreakoutNeuralNetwork(child2);

        return children;
    }

    // Realiza seleção por torneio

    public NeuronalNetwork selectParent() {

        NeuronalNetwork[] possibleParents = new BreakoutNeuralNetwork[k_tournament];

        for (int i = 0; i != k_tournament; i++) {

            possibleParents[i] = population[(int) (POPULATION_SIZE - (Math.random() * POPULATION_SIZE * SELECTION_PARENTS_PERCENTAGE))];
        }

        Arrays.sort(possibleParents);

        return possibleParents[k_tournament - 1];

    }

    public void generatePopulation() {
        for (int i = 0; i < population.length; i++) {
            population[i] = new BreakoutNeuralNetwork();
        }
    }


}

