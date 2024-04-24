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

    public static final int BREAKOUT_STATE_SIZE = 7;
    public static final int BREAKOUT_NUM_ACTIONS = 2;
    public static final int BREAKOUT_HIDDENDIM_SIZE = 7;

    // AUX
    public static final int NrOfSeedsTested = 1;
    public static final int NrOfGATested = 1;
    public static final int LeastPointsAccepted = 1000000;


    public static final int PACMAN_STATE_SIZE = PacmanBoard.N_BLOCKS * PacmanBoard.N_BLOCKS * 2 + 2 + PacmanBoard.MAX_GHOSTS * 2;
    public static final int PACMAN_NUM_ACTIONS = 4;
    public static final int PACMAN_HIDDEN_LAYER = 2;//(int) ((PACMAN_STATE_SIZE+PACMAN_NUM_ACTIONS)/2);s
    public static final int PACMAN_NETWORK_SIZE = (PACMAN_STATE_SIZE * PACMAN_HIDDEN_LAYER) + PACMAN_HIDDEN_LAYER +
                                               (PACMAN_HIDDEN_LAYER * PACMAN_NUM_ACTIONS) + PACMAN_NUM_ACTIONS;


}
