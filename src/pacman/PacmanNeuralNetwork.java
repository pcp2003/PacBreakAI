package pacman;

import utils.Commons;
import utils.NeuronalNetwork;

public class PacmanNeuralNetwork extends NeuronalNetwork {

    private int inputDim = Commons.PACMAN_STATE_SIZE;
    private int hiddenDim = Commons.PACMAN_HIDDEN_LAYER;
    private int outputDim = Commons.PACMAN_NUM_ACTIONS;


    public PacmanNeuralNetwork() {
        super(Commons.PACMAN_STATE_SIZE, Commons.PACMAN_HIDDEN_LAYER, Commons.PACMAN_NUM_ACTIONS);
    }

    public PacmanNeuralNetwork(double[] values) {
        super(values, Commons.PACMAN_STATE_SIZE, Commons.PACMAN_HIDDEN_LAYER, Commons.PACMAN_NUM_ACTIONS);
    }

    // Método para normalizar os dados de entrada
    public double[] normalizeInput(double[] inputValues) {

        double[] normalizedValues = new double[inputValues.length];

        double media = 0.0;

        for (int i = 0; i != inputDim; i++) {
            media += inputValues[i];
        }

        media = media / 7;

        for (int i = 0; i < inputValues.length; i++) {
            normalizedValues[i] = inputValues[i] / media;
        }

        return normalizedValues;
    }

    @Override
    public void calculateAndStoreFitness(int seed) {
        PacmanBoard bb = new PacmanBoard(this, false, seed);
        bb.setSeed(seed);
        bb.runSimulation();
        this.setFitness(bb.getFitness());
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

    public double[] forward(double[] currentState) {

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

    @Override
    public int getNeuralNetworkSize() {
        return Commons.PACMAN_NETWORK_SIZE;
    }

}
