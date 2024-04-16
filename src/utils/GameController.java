package utils;

// The GameController interface defines a method that allows a client to obtain the next move
// from the game controller.

public interface GameController {

    /*
     * This method takes in an array of integers values as input and returns an integer value
     * as output. The input array represents the current state of the game, and the
     * output value represents the next move to be made by the player. The exact
     * format and meaning of these values will depend on the specific game being
     * played.
     */

    public int nextMove(double[] currentState);

}
