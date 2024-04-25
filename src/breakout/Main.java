package breakout;

import utils.GeneticAlgorithm;

public class Main {

    // GOOD SEED: 296
    private static int seed = 296;

    public static void watchGameplay() {

        GeneticAlgorithm ga = new BreakoutGeneticAlgorithm(seed);
        new Breakout(ga.search(), seed);
    }

    public static void main(String[] args) {
        watchGameplay();
    }


}

