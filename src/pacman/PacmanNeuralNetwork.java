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

    @Override
    public int nextMove(double[] currentState) {
        double[] output = forward(currentState);
        if(output[0] > output[1])
            return 1;
        return 2;
    }

    private double[] forward(double[] currentState) {
        double[] inputValues = normalize(currentState);

        double[] hiddenLayer = new double[hiddenDim];

        for (int i = 0; i < hiddenDim; i++) {
            for (int j = 0; j < inputDim; j++) {
                hiddenLayer[i] += hiddenWeights[j][i] * inputValues[j];
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
        return output;
    }

    private double[] normalize(double[] values) {
        double[] result = new double[values.length];

    	double total = 0;
    	for(double k : values) total += k;
    	total/=values.length;
    	for(int i = 0; i < result.length; i++) result[i] = values[i]/total;

        return result;
    }

        private double sigmoid(double x) {
        return 1/(1+Math.exp(-x));
    }

    public double getFitness() {
        PacmanBoard bb = new PacmanBoard(this, false, seed);
        bb.runSimulation();
        return bb.getFitness();
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
                hiddenWeights[i][j] = values != null ? values[index++] :  ((Math.random() * 2) - 1);
            }
        }
        for (int i = 0; i < hiddenDim; i++) {
            hiddenBiases[i] =  values != null ? values[index++] :  ((Math.random() * 2) - 1);
        }
        for (int i = 0; i < hiddenDim; i++) {
            for (int j = 0; j < outputDim; j++) {
                outputWeights[i][j] =  values != null ? values[index++] :  ((Math.random() * 2) - 1);
            }
        }
        for (int i = 0; i < outputDim; i++) {
            outputBiases[i] =  values != null ? values[index++] :  ((Math.random() * 2) - 1);
        }
    }



    @Override
    public int compareTo(PacmanNeuralNetwork o) {
        return Double.compare(o.getFitness(), getFitness());
    }

}
