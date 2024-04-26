package breakout;

import utils.Commons;
import utils.GeneticAlgorithm;

public class Main {

    public static void watchGameplay() {

        GeneticAlgorithm ga = new BreakoutGeneticAlgorithm();
        new Breakout(ga.search(), Commons.seed_BREAKOUT);
    }

    public static void main(String[] args) {
        watchGameplay();
    }


}

