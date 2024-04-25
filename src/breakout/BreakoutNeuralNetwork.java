package breakout;

import utils.Commons;
import utils.NeuronalNetwork;

import java.util.Arrays;

public class BreakoutNeuralNetwork extends NeuronalNetwork {

    private int inputDim = Commons.BREAKOUT_STATE_SIZE;
    private int hiddenDim = Commons.BREAKOUT_HIDDENDIM_SIZE;
    private int outputDim = Commons.BREAKOUT_NUM_ACTIONS;

    // Campo para armazenar o fitness
    private Double fitness = null;

    public BreakoutNeuralNetwork() {
        initializeParameters();
    }

    public BreakoutNeuralNetwork(double[] values) {
        fillParametersWithValues(values);
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


}

