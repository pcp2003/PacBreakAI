package breakout;

import utils.Commons;
import utils.GameController;

import java.util.Arrays;

public class NeuronalNetwork implements GameController, Comparable<NeuronalNetwork> {

    public int inputDim = Commons.BREAKOUT_STATE_SIZE;
    public int hiddenDim = Commons.BREAKOUT_HIDDENDIM_SIZE;
    public int outputDim = Commons.BREAKOUT_NUM_ACTIONS;
    private double[][] hiddenWeights;
    private double[] hiddenBiases;
    private double[][] outputWeights;
    private double[] outputBiases;

    // Campo para armazenar o fitness
    private Double fitness = null;

    public NeuronalNetwork() {
        initializeParameters();
    }

    public NeuronalNetwork(double[] values) {
        fillParametersWithValues(values);
    }

    public Double getFitness() {
        if (fitness != null)
            return fitness;
        return 0.0;
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

    public double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    public double relu(double x) {
        return Math.max(0, x);
    }

    // next move e o foward da rede
    @Override
    public int nextMove(double[] currentState) {

        double[] fowardState = forward(currentState);

        if (fowardState[0] > fowardState[1])
            return BreakoutBoard.LEFT;
        else if (fowardState[0] < fowardState[1])
            return BreakoutBoard.RIGHT;
        else
            return 0;

    }

    // Método para normalizar os dados de entrada
    private double[] normalizeInput(double[] inputValues) {

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

    public double[] forward(double[] inputValues) {

        double[] normalizedInputValues = normalizeInput(inputValues);

        double[] hiddenLayerOutput = new double[hiddenDim];
        double[] outputLayerOutput = new double[outputDim];

        for (int i = 0; i != hiddenDim; i++) {
            double res = 0;
            for (int j = 0; j != inputDim; j++) {
                res += hiddenWeights[j][i] * normalizedInputValues[j];
            }
            res += hiddenBiases[i];
            hiddenLayerOutput[i] = relu(res);
        }


        for (int i = 0; i != outputDim; i++) {
            double res = 0;
            for (int j = 0; j != hiddenDim; j++) {
                res += outputWeights[j][i] * hiddenLayerOutput[j];
            }
            res += outputBiases[i];
            outputLayerOutput[i] = sigmoid(res);
        }

        return outputLayerOutput;
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

    // Método para calcular e armazenar o fitness
    public void calculateAndStoreFitness(int seed) {
        BreakoutBoard bb = new BreakoutBoard(this, false, seed);
        bb.setSeed(seed);
        bb.runSimulation();
        this.fitness = bb.getFitness();
    }

    @Override
    public int compareTo(NeuronalNetwork other) {
        if (this.fitness == null || other.fitness == null) {
            throw new IllegalStateException("Fitness not calculated before comparison.");
        }
        return Double.compare(this.fitness, other.fitness);
    }

}
