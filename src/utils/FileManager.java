package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private String filePath;

    public FileManager(String filePath) {
        this.filePath = filePath;
    }

    // Método para guardar

    public static void saveParameters(double MUTATION_CHANCE, double MUTATION_PERCENTAGE, double CUTOFF, double SELECTION_PARENTS_PERCENTAGE, int k_tournament, int k_point, int seed, String filePath) {

        FileManager FA = new FileManager(filePath);

        FA.appendToFile(
                "\nMutation chance : " + MUTATION_CHANCE + "\nMutation Percentage : " + MUTATION_PERCENTAGE + "\nCutoff : " + CUTOFF + "\nSelection Parents Percentage : " + SELECTION_PARENTS_PERCENTAGE + "\nK_Tournament : " + k_tournament + "\nK_Point : " + k_point + "\nSeed : " + seed
        );
    }

    // Método para escrever no ficheiro

    public void appendToFile(String content) {
        // Utiliza try-with-resources para garantir que o writer seja fechado após o uso
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.filePath, true))) {
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

}
