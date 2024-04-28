package utils;

import java.util.Arrays;

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

    public NeuronalNetwork(int inputDim, int hiddenDim, int outputDim) {
        this.inputDim = inputDim;
        this.hiddenDim = hiddenDim;
        this.outputDim = outputDim;
        initializeParameters();
    }


    public NeuronalNetwork(double[] values, int inputDim, int hiddenDim, int outputDim) {
        this.inputDim = inputDim;
        this.hiddenDim = hiddenDim;
        this.outputDim = outputDim;
        int maxSize = getNeuralNetworkSize();
        if (values.length == maxSize) {
            fillParametersWithValues(values);
        } else {
            throw new IllegalArgumentException("Incorrect size of input values array");
        }
    }



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


    public double[] softmax(double[] inputs) {

        double max = Double.NEGATIVE_INFINITY;
        for (double input : inputs) {
            if (input > max) {
                max = input; // To prevent overflow
            }
        }

        double sum = 0.0;
        double[] exps = new double[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            exps[i] = Math.exp(inputs[i] - max); // Subtract max for numerical stability
            sum += exps[i];
        }

        for (int i = 0; i < exps.length; i++) {
            exps[i] /= sum;
        }

        return exps;
    }

    public double relu(double x) {
        return Math.max(0, x);
    }

    public double sigmoid(double x) {
        return 1/(1+Math.exp(-x));
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }


    public abstract int getNeuralNetworkSize();

    public void initializeParameters() {
        // Inicialização de He para os pesos da camada oculta
        double stdHidden = Math.sqrt(2.0 / inputDim);

        hiddenWeights = new double[inputDim][hiddenDim];
        hiddenBiases = new double[hiddenDim];
        outputWeights = new double[hiddenDim][outputDim];
        outputBiases = new double[outputDim];

        for (int i = 0; i < inputDim; i++) {
            for (int j = 0; j < hiddenDim; j++) {
                hiddenWeights[i][j] = stdHidden * (Math.random() * 2 - 1); // Distribuição uniforme [-stdHidden, stdHidden]
            }
        }

        // Inicialização de He para os pesos da camada de saída
        double stdOutput = Math.sqrt(2.0 / hiddenDim);
        for (int i = 0; i < hiddenDim; i++) {
            for (int j = 0; j < outputDim; j++) {
                outputWeights[i][j] = stdOutput * (Math.random() * 2 - 1); // Distribuição uniforme [-stdOutput, stdOutput]
            }
        }

        // Inicializar vieses para 0
        Arrays.fill(hiddenBiases, 0);
        Arrays.fill(outputBiases, 0);
    }

    @Override
    public abstract int nextMove(double[] currentState);

    // Método para normalizar os dados de entrada
    public abstract double[] normalizeInput(double[] inputValues);

    // Método para calcular e armazenar o fitness
    public abstract void calculateAndStoreFitness(int seed);

    public abstract double[] forward(double[] inputValues);

}
