package utils;

import breakout.BreakoutBoard;
import breakout.BreakoutGeneticAlgorithm;
import pacman.PacmanBoard;
import pacman.PacmanGeneticAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class TesterGAs {

// ATENÇãO, ESTA PARTE ABAIXO FOI REALIZADA COM INTUITO DE CONSEGUIR UMA GA QUE FOSSE BOA PARA VÁRIAS SEEDS.

    private static int MaxSeed = 1000;

    private static List<double[]> bestParameters = new ArrayList<double[]>(Commons.NrOfSeedsTested * Commons.NrOfGATested);


    // Método para adicionar algoritmos genéticos random ao ficheiro

    public static void addRandomGAToFile(int NrOfSeedsTested, int NrOfGATestedPerSeed, String game) {

        for (int i = 0; i != NrOfSeedsTested; i++) {

            int seed = (int) ((Math.random() * MaxSeed) + 1);


            for (int j = 0; j != NrOfGATestedPerSeed; j++) {

                FileManager FA;
                GeneticAlgorithm ga;

                if(game.equals("breakout")) {

                    ga = new BreakoutGeneticAlgorithm(seed, Commons.BREAKOUT);

                    BreakoutBoard b = new BreakoutBoard(ga.search(), false, seed);
                    b.setSeed(seed);
                    b.runSimulation();

                    FA = new FileManager("randomValuesBreakout.txt");
                } else {
                    ga = new PacmanGeneticAlgorithm(seed, Commons.PACMAN);

                    PacmanBoard b = new PacmanBoard(ga.search(), false, seed);
                    b.setSeed(seed);
                    b.runSimulation();

                    FA = new FileManager("randomValuesPacman.txt");
                }

                double[] LF = ga.LastFive();

                for (int k = 0; k != LF.length; k++) {
                    FA.appendToFile(" Fitness = " + LF[k]);
                }

            }

        }
    }

    public static int[] testBestParameters(int NrOfSeedsTested, String game) {

        String randomValuesPath;
        String bestParametersPath;
        int mode;

        // Define qual dos jogos está sendo jogado.

        if (game.equals("breakout")){
            System.out.println("Playing breakout");
            randomValuesPath = "randomValuesBreakout.txt";
            bestParametersPath = "bestParametersBreakout.txt";
            mode = Commons.BREAKOUT;
        }else{
            System.out.println("Playing pacman");
            randomValuesPath = "randomValuesPacman.txt";
            bestParametersPath = "bestParametersPacman.txt";
            mode = Commons.PACMAN;
        }

        FileManager FR = new FileManager(randomValuesPath);

        bestParameters = FR.readAndProcessFile();

        int[] resultsList = new int[bestParameters.size()];

        double[][] bestParametersArray = new double[bestParameters.size()][];

        for (int i = 0; i < bestParameters.size(); i++) {
            bestParametersArray[i] = bestParameters.get(i);
        }


        for (int i = 0; i != NrOfSeedsTested; i++) {


            int seed = (int) ((Math.random() * MaxSeed) + 1);

            for (int j = 0; j != bestParameters.size(); j++) {

                GeneticAlgorithm ga;
                FileManager FA = new FileManager(bestParametersPath);
                System.out.println(" Modo : " + mode);
                System.out.println(game);
                if(game.equals("breakout")){

                    ga = new BreakoutGeneticAlgorithm(bestParametersArray[j][0], bestParametersArray[j][1], bestParametersArray[j][2], bestParametersArray[j][3], bestParametersArray[j][4], bestParametersArray[j][5], seed, mode);

                    BreakoutBoard b = new BreakoutBoard(ga.search(), true, seed);
                    b.setSeed(seed);
                    b.runSimulation();
                } else {
                    ga = new PacmanGeneticAlgorithm(bestParametersArray[j][0], bestParametersArray[j][1], bestParametersArray[j][2], bestParametersArray[j][3], bestParametersArray[j][4], bestParametersArray[j][5], seed, mode);

                    BreakoutBoard b = new BreakoutBoard(ga.search(), false, seed);
                    b.setSeed(seed);
                    b.runSimulation();
                }


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

    // Pega os melhores parametros e fica num loop enquanto não encontrar um conjunto de parametros que
    // pontue >= 600000 em 7 / 10 seeds

    // Se não encontrar, ele preenche o ficheiro (randomValuesPath) com random values, para originar um novo best parameters que
    // , possivelmente, possuira individous que pontuem positivamente e passem o finalTest.

    public static void finalTest(int[] resultList, String randomValuesPath) {

        boolean found = false;

        while (!found) {

            addRandomGAToFile(Commons.NrOfSeedsTested, Commons.NrOfGATested, randomValuesPath);

            resultList = testBestParameters(10, randomValuesPath);

            for (int i = 0; i != resultList.length; i++) {
                if (resultList[i] >= 7)
                    found = true;
                System.out.println("i : " + resultList[i]);
            }

        }
    }


    public static void main(String[] args) {

        //addRandomGAToFile(2, 2, "pacman");


        int[] result;

        result = testBestParameters(2, "pacman");

        for (int i = 0; i != result.length; i++) {
            System.out.print(result[i]);
        }

    }
}
