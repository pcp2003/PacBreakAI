package breakout;

import utils.Commons;
import utils.GeneticAlgorithm;
import utils.NeuronalNetwork;

public class Main {

    public static void watchGameplay() {

        GeneticAlgorithm ga = new BreakoutGeneticAlgorithm();
        NeuronalNetwork best = ga.search();
        System.out.println("Fitness that will be played: " + best.getFitness());
        new Breakout(best, Commons.seed_BREAKOUT);
    }

    public static void main(String[] args) {
        watchGameplay();
    }


}

