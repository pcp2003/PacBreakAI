package pacman;

import utils.Commons;
import utils.GameController;

public class PacmanNeuralNetwork implements GameController, Comparable<PacmanNeuralNetwork> {

    private final int inputDim = Commons.PACMAN_STATE_SIZE;
    private final int hiddenDim = Commons.PACMAN_HIDDEN_LAYER;
    private final int outputDim = Commons.PACMAN_NUM_ACTIONS;
    private double[][] hiddenWeights;
    private double[] hiddenBiases;
    private double[][] outputWeights;
    private double[] outputBiases;

    private double fitness = 0;
    private final int seed;

    PacmanNeuralNetwork(int seed) {
        this.seed = seed;
        initializeParameters(null);
    }

    PacmanNeuralNetwork(double[] values, int seed) {
        this.seed = seed;
        int maxSize = Commons.PACMAN_NETWORK_SIZE; // Adjust this value as per new network size
        if (values.length == maxSize) {
            initializeParameters(values);
        } else {
            throw new IllegalArgumentException("Incorrect size of input values array");
        }
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

    @Override
    public int nextMove(double[] currentState) {
        double maxValue = 0;
        int max = 0;
        double[] output = forward(currentState);
        for(int i = 0; i  < output.length; i++) {
            if( output[i] > maxValue) {
                max = i+1;
                maxValue = output[i];
            };
        }
        return maxValue == 0.25 ? 0 : max;
    }

    private double[] forward(double[] currentState) {
        double[] hiddenLayer = new double[hiddenDim];

        for (int i = 0; i < hiddenDim; i++) {
            for (int j = 0; j < inputDim; j++) {
                hiddenLayer[i] += hiddenWeights[j][i] * currentState[j];
            }
            hiddenLayer[i] = sigmoid(hiddenLayer[i] + hiddenBiases[i]);
        }

        // Output layer (now directly follows the first hidden layer)
        double[] output = new double[outputDim];
        for (int i = 0; i < outputDim; i++) {
            for (int j = 0; j < hiddenDim; j++) {
                output[i] += outputWeights[j][i] * hiddenLayer[j];
            }
            output[i] = sigmoid(output[i] + outputBiases[i]);
        }
        output = softmax(output);
        return output;
    }

    private double sigmoid(double x) {
        return 1/(1+Math.exp(-x));
    }

    public double getFitness() {
        return fitness;
    }

    public void calculateFitness() {
        PacmanBoard bb = new PacmanBoard(this, false, seed);
        bb.runSimulation();
        this.fitness = bb.getFitness();
    }

    public int getSeed() {
        return seed;
    }


    public double[] getNeuralNetwork() {
        int size = (inputDim * hiddenDim) + hiddenDim + // Weights and biases for the hidden layer
                (hiddenDim * outputDim) + outputDim; // Weights and biases for the output layer
        double[] networkParams = new double[size];

        int index = 0;
        // Hidden layer weights
        for (int i = 0; i < inputDim; i++) {
            for (int j = 0; j < hiddenDim; j++) {
                networkParams[index++] = hiddenWeights[i][j];
            }
        }
        // Hidden layer biases
        for (int i = 0; i < hiddenDim; i++) {
            networkParams[index++] = hiddenBiases[i];
        }

        // Output layer weights
        for (int i = 0; i < hiddenDim; i++) {
            for (int j = 0; j < outputDim; j++) {
                networkParams[index++] = outputWeights[i][j];
            }
        }
        // Output layer biases
        for (int i = 0; i < outputDim; i++) {
            networkParams[index++] = outputBiases[i];
        }

        return networkParams;
    }


    public void initializeParameters(double[] values) {
        hiddenWeights = new double[inputDim][hiddenDim];
        hiddenBiases = new double[hiddenDim];
        outputWeights = new double[hiddenDim][outputDim];
        outputBiases = new double[outputDim];

        int index = 0;
        for (int i = 0; i < inputDim; i++) {
            for (int j = 0; j < hiddenDim; j++) {
                hiddenWeights[i][j] = values != null ? values[index++] : ((Math.random() * 2) - 1);
            }
        }
        for (int i = 0; i < hiddenDim; i++) {
            hiddenBiases[i] =  values != null ? values[index++] : ((Math.random() * 2) - 1);
        }
        for (int i = 0; i < hiddenDim; i++) {
            for (int j = 0; j < outputDim; j++) {
                outputWeights[i][j] =  values != null ? values[index++] : ((Math.random() * 2) - 1);
            }
        }
        for (int i = 0; i < outputDim; i++) {
            outputBiases[i] =  values != null ? values[index++] : ((Math.random() * 2) - 1);
        }
    }



    @Override
    public int compareTo(PacmanNeuralNetwork o) {
        return Double.compare(getFitness(), o.getFitness());
    }

}
