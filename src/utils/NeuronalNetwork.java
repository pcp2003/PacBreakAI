package utils;

import breakout.BreakoutBoard;

public abstract class NeuronalNetwork implements GameController, Comparable<NeuronalNetwork> {

    public int inputDim;
    public int hiddenDim;
    public int outputDim;
    public double[][] hiddenWeights;
    public double[] hiddenBiases;
    public double[][] outputWeights;
    public double[] outputBiases;

    // Campo para armazenar o fitness
    private Double fitness = null;

    public NeuronalNetwork() {
    }

    public NeuronalNetwork(double[] values) {
    }

    public abstract void initializeParameters();
    @Override
    public abstract int nextMove(double[] currentState);

    // Método para normalizar os dados de entrada
    public abstract double[] normalizeInput(double[] inputValues);

    public abstract double[] forward(double[] inputValues);

    public double[] getNeuralNetwork() {

        double[] wheightsAndBiases = new double[(inputDim * hiddenDim) + hiddenDim + (hiddenDim * outputDim) + outputDim];

        int index = 0;

        for (int i = 0; i != inputDim; i++) {
            for (int j = 0; j != hiddenDim; j++) {
                wheightsAndBiases[index++] = hiddenWeights[i][j];
            }
        }

        for (int i = 0; i != hiddenDim; i++) {
            wheightsAndBiases[index++] = hiddenBiases[i];
        }

        for (int i = 0; i != hiddenDim; i++) {
            for (int j = 0; j != outputDim; j++) {
                wheightsAndBiases[index++] = outputWeights[i][j];

            }
        }

        for (int i = 0; i != outputDim; i++) {
            wheightsAndBiases[index++] = outputBiases[i];
        }

        return wheightsAndBiases;
    }

    public void fillParametersWithValues(double[] values) {

        // [w1,1; w1,2; w2,1; w2,2; B1; B2; w1,o; w2,o; Bo]
        hiddenWeights = new double[inputDim][hiddenDim];
        hiddenBiases = new double[hiddenDim];
        outputWeights = new double[hiddenDim][outputDim];
        outputBiases = new double[outputDim];

        int index = 0;

        for (int i = 0; i != inputDim; i++) {
            for (int j = 0; j != hiddenDim; j++) {
                hiddenWeights[i][j] = values[index++];
            }
        }

        for (int i = 0; i != hiddenDim; i++) {
            hiddenBiases[i] = values[index++];
        }

        for (int i = 0; i != hiddenDim; i++) {
            for (int j = 0; j != outputDim; j++) {
                outputWeights[i][j] = values[index++];

            }
        }

        for (int i = 0; i != outputDim; i++) {
            outputBiases[i] = values[index++];
        }
    }

    @Override
    public int compareTo(NeuronalNetwork other) {
        if (this.fitness == null || other.fitness == null) {
            throw new IllegalStateException("Fitness not calculated before comparison.");
        }
        return Double.compare(this.fitness, other.fitness);
    }

    public Double getFitness() {
        if (fitness != null)
            return fitness;
        return 0.0;
    }

    // Método para calcular e armazenar o fitness
    public void calculateAndStoreFitness(int seed) {
        BreakoutBoard bb = new BreakoutBoard(this, false, seed);
        bb.setSeed(seed);
        bb.runSimulation();
        this.fitness = bb.getFitness();
    }

    @Override
    public String toString() {
        String result = "Neural Network: \nNumber of inputs: "
                + inputDim + "\n"
                + "Weights between input and hidden layer with " + hiddenDim + " neurons: \n";
        String hidden = "";
        for (int input = 0; input < inputDim; input++) {
            for (int i = 0; i < hiddenDim; i++) {
                hidden += " input" + input + "-hidden" + i + ": "
                        + hiddenWeights[input][i] + "\n";
            }
        }
        result += hidden;
        String biasHidden = "Hidden biases: \n";
        for (int i = 0; i < hiddenDim; i++) {
            biasHidden += " bias hidden" + i + ": " + hiddenBiases[i] + "\n";
        }
        result += biasHidden;
        String output = "Weights between hidden and output layer with "
                + outputDim + " neurons: \n";
        for (int hiddenw = 0; hiddenw < hiddenDim; hiddenw++) {
            for (int i = 0; i < outputDim; i++) {
                output += " hidden" + hiddenw + "-output" + i + ": "
                        + outputWeights[hiddenw][i] + "\n";
            }
        }
        result += output;
        String biasOutput = "Ouput biases: \n";
        for (int i = 0; i < outputDim; i++) {
            biasOutput += " bias ouput" + i + ": " + outputBiases[i] + "\n";
        }
        result += biasOutput;
        return result;
    }

}
