package breakout;

import utils.Commons;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private String filePath;

    public FileManager(String filePath) {
        this.filePath = filePath;
    }

    // Método para escrever no ficheiro

    public void appendToFile(String content) {
        // Utiliza try-with-resources para garantir que o writer seja fechado após o uso
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(content);
            writer.newLine();
            System.out.println("Conteudo adicionado ao arquivo.");
        } catch (IOException e) {
            System.err.println("Ocorreu um erro ao escrever no arquivo: " + e.getMessage());
        }
    }

    // Método para ler e processar o arquivo, retornando os parâmetros desejados

    public List<double[]> readAndProcessFile() {
        List<double[]> parametersList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {

                // Assume que o primeiro fitness está sempre logo após os parâmetros
                if (line.startsWith("Mutation chance")) {
                    double mutationChance = Double.parseDouble(line.split(" : ")[1]);
                    double mutationPercentage = Double.parseDouble(reader.readLine().split(" : ")[1]);
                    double cutoff = Double.parseDouble(reader.readLine().split(" : ")[1]);
                    double selectionParentsPercentage = Double.parseDouble(reader.readLine().split(" : ")[1]);
                    int kTournament = Integer.parseInt(reader.readLine().split(" : ")[1]);
                    int kPoint = Integer.parseInt(reader.readLine().split(" : ")[1]);
                    int seed = Integer.parseInt(reader.readLine().split(" : ")[1]);


                    double firstFitness = Double.parseDouble(reader.readLine().split(" = ")[1]);
                    double secondFitness = Double.parseDouble(reader.readLine().split(" = ")[1]);
                    double thirdFitness = Double.parseDouble(reader.readLine().split(" = ")[1]);
                    double fourthFitness = Double.parseDouble(reader.readLine().split(" = ")[1]);
                    double FifthFitness = Double.parseDouble(reader.readLine().split(" = ")[1]);

                    double avarageFitness = (firstFitness + secondFitness + thirdFitness + fourthFitness + FifthFitness) / 5;

                    if (avarageFitness >= Commons.LeastPointsAccepted) {
                        parametersList.add(new double[]{
                                mutationChance, mutationPercentage, cutoff,
                                selectionParentsPercentage, (double) kTournament, (double) kPoint, (double) seed, avarageFitness
                        });
                    }

                    // Pular o resto dos valores de fitness para o próximo conjunto de parâmetros
                    reader.readLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Ocorreu um erro ao ler o arquivo: " + e.getMessage());
        }

        return parametersList;
    }

    // Método para adicionar algoritmos genéticos random ao ficheiro

    public static void addRandomGAToFile(int NrOfSeedsTested, int NrOfGATestedPerSeed) {

        for (int i = 0; i != NrOfSeedsTested; i++) {

            int seed = (int) ((Math.random() * 1000) + 1);


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

}
