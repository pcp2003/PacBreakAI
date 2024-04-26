package pacman;

import utils.Commons;
import utils.GeneticAlgorithm;

public class Main {

    public static void watchGameplay() {

        GeneticAlgorithm ga = new PacmanGeneticAlgorithm();
        new Pacman(ga.search(), true, Commons.seed_PACMAN);
    }

    public static void main(String[] args) {
        watchGameplay();
    }
}
