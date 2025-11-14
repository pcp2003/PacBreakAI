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
    public static final double MUTATION_CHANCE_BREAKOUT = 0.45938064251444644;
    public static final double MUTATION_PERCENTAGE_BREAKOUT = 0.23492480629308726;
    public static final double CUTOFF_BREAKOUT = 0.69890818426316;
    public static final double SELECTION_PARENTS_PERCENTAGE_BREAKOUT = 0.41859519615986784;
    public static final int k_tournament_BREAKOUT = 6;
    public static final int k_point_BREAKOUT = 3;
    public static final int seed_BREAKOUT = 296;

    // AUX
    public static final int NrOfSeedsTested = 1;
    public static final int NrOfGATested = 1;
    public static final int BreakoutLeastPointsAccepted = 900000;
    public static final int PacmanLeastPointsAccepted = 70000;


    public static final int PACMAN_STATE_SIZE = PacmanBoard.N_BLOCKS * PacmanBoard.N_BLOCKS * 2 + 2 + PacmanBoard.MAX_GHOSTS * 2;
    public static final int PACMAN_NUM_ACTIONS = 5;
    public static final int PACMAN_HIDDEN_LAYER = 7;//(int) ((PACMAN_STATE_SIZE+PACMAN_NUM_ACTIONS)/2); (240)
    public static final int PACMAN_NETWORK_SIZE = (PACMAN_STATE_SIZE * PACMAN_HIDDEN_LAYER) + PACMAN_HIDDEN_LAYER + (PACMAN_HIDDEN_LAYER * PACMAN_NUM_ACTIONS) + PACMAN_NUM_ACTIONS;


    //PACMAN PARAMETERES
    public static final double MUTATION_CHANCE_PACMAN = 0.3552731334995557;
    public static final double MUTATION_PERCENTAGE_PACMAN = 0.10036581938853349;
    public static final double CUTOFF_PACMAN = 0.6956000771509764;
    public static final double SELECTION_PARENTS_PERCENTAGE_PACMAN = 0.6864309766991387;
    public static final int k_tournament_PACMAN = 5;
    public static final int k_point_PACMAN = 3;
    public static final int seed_PACMAN = 3113;

    /*

    Mutation chance : 0.3552731334995557
    Mutation Percentage : 0.10036581938853349
    Cutoff : 0.6956000771509764
    Selection Parents Percentage : 0.6864309766991387
    K_Tournament : 5
    K_Point : 3
    Seed : 591
    Avarage Fitness = 76004.0

    Mutation chance : 0.3910988967413098
    Mutation Percentage : 0.03433024644607651
    Cutoff : 0.28083108602930307
    Selection Parents Percentage : 0.8691060599107486
    K_Tournament : 6
    K_Point : 1
    Seed : 472
    Avarage Fitness = 72002.0


     */


    // FILEMANAGER AND TESTER
    public static final int BREAKOUT = 1;
    public static final int PACMAN = 2;


}
