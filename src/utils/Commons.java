package utils;

//import pacman.PacmanBoard;

import pacman.PacmanBoard;

public interface Commons {

    public static final int WIDTH = 300;
    public static final int HEIGHT = 400;
    public static final int BOTTOM_EDGE = 390;
    public static final int N_OF_BRICKS = 1;//30;
    public static final int INIT_PADDLE_X = 200;
    public static final int INIT_PADDLE_Y = 360;
    public static final int INIT_BALL_X = 230;
    public static final int INIT_BALL_Y = 355;
    public static final int PERIOD = 1;


    //Breakout
    public static final int BREAKOUT_STATE_SIZE = 7;
    public static final int BREAKOUT_NUM_ACTIONS = 2;
    public static final int BREAKOUT_HIDDENDIM_SIZE = 7;

    public static final int BREAKOUT_NETWORK_SIZE = (Commons.BREAKOUT_STATE_SIZE * Commons.BREAKOUT_HIDDENDIM_SIZE) + Commons.BREAKOUT_HIDDENDIM_SIZE + (Commons.BREAKOUT_HIDDENDIM_SIZE * Commons.BREAKOUT_NUM_ACTIONS) + Commons.BREAKOUT_NUM_ACTIONS;

    //BREAKOUT PARAMETERES
    public static final double MUTATION_CHANCE_BREAKOUT = 0.20733758777463301;
    public static final double MUTATION_PERCENTAGE_BREAKOUT = 0.4944831677092099;
    public static final double CUTOFF_BREAKOUT = 0.613588965089243;
    public static final double SELECTION_PARENTS_PERCENTAGE_BREAKOUT = 0.2;
    public static final int k_tournament_BREAKOUT = 5;
    public static final int k_point_BREAKOUT = 3;
    public static final int seed_BREAKOUT = 296;


    // AUX
    public static final int NrOfSeedsTested = 1;
    public static final int NrOfGATested = 1;
    public static final int LeastPointsAccepted = 10;


    public static final int PACMAN_STATE_SIZE = PacmanBoard.N_BLOCKS * PacmanBoard.N_BLOCKS * 2 + 2 + PacmanBoard.MAX_GHOSTS * 2;
    public static final int PACMAN_NUM_ACTIONS = 4;
    public static final int PACMAN_HIDDEN_LAYER = 2;//(int) ((PACMAN_STATE_SIZE+PACMAN_NUM_ACTIONS)/2);s
    public static final int PACMAN_NETWORK_SIZE = (PACMAN_STATE_SIZE * PACMAN_HIDDEN_LAYER) + PACMAN_HIDDEN_LAYER + (PACMAN_HIDDEN_LAYER * PACMAN_NUM_ACTIONS) + PACMAN_NUM_ACTIONS;
    //PACMAN PARAMETERES
    public static final double MUTATION_CHANCE_PACMAN = 0.20733758777463301;
    public static final double MUTATION_PERCENTAGE_PACMAN = 0.4944831677092099;
    public static final double CUTOFF_PACMAN = 0.613588965089243;
    public static final double SELECTION_PARENTS_PERCENTAGE_PACMAN = 0.2;
    public static final int k_tournament_PACMAN = 5;
    public static final int k_point_PACMAN = 3;
    public static final int seed_PACMAN = 1;


    // FILEMANAGER AND TESTER
    public static final int BREAKOUT = 1;
    public static final int PACMAN = 2;


}
