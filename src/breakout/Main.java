package breakout;

public class Main {

    // GOOD SEED: 296
    private static int seed = 296;

    public static void watchGameplay() {

        GeneticAlgorithm ga = new GeneticAlgorithm(seed, 1);
        new Breakout(ga.search(), seed);
    }

    public static void main(String[] args) {
        watchGameplay();
    }


}

