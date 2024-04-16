package breakout;

import utils.Commons;

import java.util.ArrayList;
import java.util.List;

public class Main {

    // Loop exterior realiza o mesmo processo para i seeds diferentes, com objetivo de garantir que os parametros escolhidos funcionam para diferentes seeds

    // Loop interior gera j algoritmos geneticos, guarda num ficheiro suas pontuações que depois serão lidas e
    // processadas (Apenas os parâmetros gerados que sejam capazes de originar um avarage fitness das
    // 5 melhores >= 700000 serão adicionados a matriz de melhores parametros associados a uma seed)

    // GOOD SEED: 296,

    private static int seed;

    private static List<double[]> bestParameters = new ArrayList<double[]>(Commons.NrOfSeedsTested * Commons.NrOfGATested);

    public static void addRandomGAToFile(int NrOfSeedsTested, int NrOfGATestedPerSeed) {

        for (int i = 0; i != NrOfSeedsTested; i++) {

            seed = (int) ((Math.random() * 1000) + 1);


            for (int j = 0; j != NrOfGATestedPerSeed; j++) {

                GeneticAlgorithm ga = new GeneticAlgorithm(seed);

                FileManager FA = new FileManager("randomValues.txt");

                BreakoutBoard b = new BreakoutBoard(ga.search(), false, seed);
                b.setSeed(seed);
                b.runSimulation();

                double[] LF = ga.LastFive();

                for (int k = 0; k != LF.length; k++) {
                    FA.appendToFile(" Fitness = " + LF[k]);
                }

            }

        }
    }

    public static void watchGameplay() {

        GeneticAlgorithm ga = new GeneticAlgorithm(seed);
        new Breakout(ga.search(), seed);
    }

    public static int[] testBestParameters(int NrOfSeedsTested) {

        FileManager FR = new FileManager("randomValues.txt");

        bestParameters = FR.readAndProcessFile();

        int[] resultsList = new int[bestParameters.size()];

        // Convertendo para matriz

        double[][] bestParametersArray = new double[bestParameters.size()][];

        for (int i = 0; i < bestParameters.size(); i++) {
            bestParametersArray[i] = bestParameters.get(i);
        }

        for (int i = 0; i != NrOfSeedsTested; i++) {

            seed = (int) ((Math.random() * 1000) + 1);

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

                if (avarageFitness >= 700000) {
                    resultsList[j] += 1;
                }

            }

        }

        return resultsList;
    }

    public static void main(String[] args) {

        int[] result;
        boolean found = false;

//        result = testBestParameters(2);
//
//        System.out.print(result);

//        watchGameplay();

        // Pega os melhores parametros e fica num loop enquanto não encontrar um conjunto de parametros que pontue >= 600000 em 7/10 seeds

        while (!found) {

//            addRandomGAToFile(Commons.NrOfSeedsTested, Commons.NrOfGATested);

            result = testBestParameters(10);

            for (int i = 0; i != result.length; i++) {
                if (result[i] >= 7)
                    found = true;
                System.out.println("i : " + result[i]);
            }

        }

        System.out.print("Finished");


    }
}

