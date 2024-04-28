package breakout;

import utils.Commons;
import utils.NeuronalNetwork;

public class BreakoutNeuralNetwork extends NeuronalNetwork {

    private int inputDim = Commons.BREAKOUT_STATE_SIZE;
    private int hiddenDim = Commons.BREAKOUT_HIDDENDIM_SIZE;
    private int outputDim = Commons.BREAKOUT_NUM_ACTIONS;

    public BreakoutNeuralNetwork() {
        super(Commons.BREAKOUT_STATE_SIZE, Commons.BREAKOUT_HIDDENDIM_SIZE, Commons.BREAKOUT_NUM_ACTIONS);
    }

    public BreakoutNeuralNetwork(double[] values) {
        super(values, Commons.BREAKOUT_STATE_SIZE, Commons.BREAKOUT_HIDDENDIM_SIZE, Commons.BREAKOUT_NUM_ACTIONS);
    }

    @Override
    public int getNeuralNetworkSize() {
        return Commons.BREAKOUT_NETWORK_SIZE;
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

    @Override
    public void calculateAndStoreFitness(int seed) {
        BreakoutBoard bb = new BreakoutBoard(this, false, seed);
        bb.setSeed(seed);
        bb.runSimulation();
        this.setFitness(bb.getFitness());
    }
}

