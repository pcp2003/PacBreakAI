package breakout;

import utils.Commons;

import java.util.ArrayList;
import java.util.List;

import static breakout.FileManager.addRandomGAToFile;

public class TesterGAs {

// ATENÇãO, ESTA PARTE ABAIXO FOI REALIZADA COM INTUITO DE CONSEGUIR UMA GA QUE FOSSE BOA PARA VÁRIAS SEEDS.

    private static int MaxSeed = 1000;

    private static List<double[]> bestParameters = new ArrayList<double[]>(Commons.NrOfSeedsTested * Commons.NrOfGATested);

    public static int[] testBestParameters(int NrOfSeedsTested) {

        FileManager FR = new FileManager("randomValues.txt");

        bestParameters = FR.readAndProcessFile();

        int[] resultsList = new int[bestParameters.size()];

        double[][] bestParametersArray = new double[bestParameters.size()][];

        for (int i = 0; i < bestParameters.size(); i++) {
            bestParametersArray[i] = bestParameters.get(i);
        }

        for (int i = 0; i != NrOfSeedsTested; i++) {

            int seed = (int) ((Math.random() * MaxSeed) + 1);

            for (int j = 0; j != bestParameters.size(); j++) {

                GeneticAlgorithm ga = new GeneticAlgorithm(bestParametersArray[j][0], bestParametersArray[j][1], bestParametersArray[j][2], bestParametersArray[j][3], bestParametersArray[j][4], bestParametersArray[j][5], seed);

                FileManager FA = new FileManager("bestParameters.txt");

                BreakoutBoard b = new BreakoutBoard(ga.search(), false, seed);
                b.setSeed(seed);
                b.runSimulation();

                double[] LF = ga.LastFive();
                double avarageFitness = 0;

                for (int k = 0; k != LF.length; k++) {
                    avarageFitness += LF[k];
                }

                avarageFitness = avarageFitness / 5;

                FA.appendToFile(" Avarage Fitness = " + avarageFitness);

                if (avarageFitness >= Commons.LeastPointsAccepted) {
                    resultsList[j] += 1;
                }

            }

        }

        return resultsList;
    }

//        Pega os melhores parametros e fica num loop enquanto não encontrar um conjunto de parametros que
//        pontue >= 600000 em 7 / 10 seeds

    public static void finalTest(int[] resultList) {

        boolean found = false;

        while (!found) {

            addRandomGAToFile(Commons.NrOfSeedsTested, Commons.NrOfGATested);

            resultList = testBestParameters(10);

            for (int i = 0; i != resultList.length; i++) {
                if (resultList[i] >= 7)
                    found = true;
                System.out.println("i : " + resultList[i]);
            }

        }
    }

    public static void main(String[] args) {

        int[] result;

        result = testBestParameters(2);

        for (int i = 0; i != result.length; i++) {
            System.out.print(result[i]);
        }

//        finalTest(result);

//        System.out.print("PASSED THE TEST");

    }
}
