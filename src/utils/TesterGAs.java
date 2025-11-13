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

                if (game.equals("breakout")) {

                    ga = new BreakoutGeneticAlgorithm(seed);

                    BreakoutBoard b = new BreakoutBoard(ga.search(), false, seed);
                    b.setSeed(seed);
                    b.runSimulation();

                    FA = new FileManager("randomValuesBreakout.txt");
                } else {

                    if (!game.equals("pacman"))
                        throw new IllegalArgumentException("Jogo não existe tente (pacman) ou (breakout)");

                    ga = new PacmanGeneticAlgorithm(seed);

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

    // Considerando um GA = X com pontuação(X) = 10, testBestParameters verifica a consistencia da pontuação(X) em NrOfTimesTested tentativas para a MESMA seed que pontuou 10.

    // Se specificSeed == true, então utiliza a seed definida em COMMONS, caso contrário, utiliza a seed que melhor pontuou no ficheiro randomValues.

    public static int[] testBestParameters(int NrOfTimesTested, String game, boolean specificSeed) {

        String randomValuesPath;
        String bestParametersPath;
        int LeastPointsAccepted;

        // Define qual dos jogos está sendo jogado.

        if (game.equals("breakout")) {
            System.out.println("Playing breakout");
            randomValuesPath = "randomValuesBreakout.txt";
            bestParametersPath = "bestParametersBreakout.txt";
            LeastPointsAccepted = Commons.BreakoutLeastPointsAccepted;
        } else {
            System.out.println("Playing pacman");
            randomValuesPath = "randomValuesPacman.txt";
            bestParametersPath = "bestParametersPacman.txt";
            LeastPointsAccepted = Commons.PacmanLeastPointsAccepted;
        }

        FileManager FR = new FileManager(randomValuesPath);

        bestParameters = FR.readAndProcessFile();

        int[] resultsList = new int[bestParameters.size()];

        double[][] bestParametersArray = new double[bestParameters.size()][];

        for (int i = 0; i < bestParameters.size(); i++) {
            bestParametersArray[i] = bestParameters.get(i);
        }


        for (int i = 0; i != NrOfTimesTested; i++) {

            for (int j = 0; j != bestParameters.size(); j++) {

                GeneticAlgorithm ga;
                FileManager FA = new FileManager(bestParametersPath);
                System.out.println("-------------------" + game + "-------------------");

                if (game.equals("breakout")) {

                    int seed = (int) bestParametersArray[j][6];

                    if (specificSeed)
                        seed = Commons.seed_BREAKOUT;


                    ga = new BreakoutGeneticAlgorithm(bestParametersArray[j][0], bestParametersArray[j][1], bestParametersArray[j][2], bestParametersArray[j][3], bestParametersArray[j][4], bestParametersArray[j][5], seed);

                    BreakoutBoard b = new BreakoutBoard(ga.search(), false, seed);
                    b.setSeed(seed);
                    b.runSimulation();
                } else {

                    int seed = (int) bestParametersArray[j][6];

                    if (specificSeed)
                        seed = Commons.seed_PACMAN;

                    ga = new PacmanGeneticAlgorithm(bestParametersArray[j][0], bestParametersArray[j][1], bestParametersArray[j][2], bestParametersArray[j][3], bestParametersArray[j][4], bestParametersArray[j][5], seed);

                    PacmanBoard p = new PacmanBoard(ga.search(), false, seed);
                    p.setSeed(seed);
                    p.runSimulation();
                }


                double[] LF = ga.LastFive();
                double avarageFitness = 0;

                for (int k = 0; k != LF.length; k++) {
                    avarageFitness += LF[k];
                }

                avarageFitness = avarageFitness / 5;

                FA.appendToFile(" Avarage Fitness = " + avarageFitness);

                if (avarageFitness >= LeastPointsAccepted) {
                    resultsList[j] += 1;
                }

            }

        }

        return resultsList;
    }


    public static void main(String[] args) {

        int[] result;

        result = testBestParameters(1, "pacman", false);

        for (int i = 0; i != result.length; i++) {
            System.out.print(result[i]);
        }
    }
}
