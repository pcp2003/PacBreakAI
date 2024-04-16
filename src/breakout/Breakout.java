package breakout;

import utils.GameController;

import javax.swing.*;

public class Breakout extends JFrame {

    private static final long serialVersionUID = 1L;

    public Breakout(GameController network, int i) {
        add(new BreakoutBoard(network, true, i));
        setTitle("Breakout");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        pack();
        setVisible(true);
    }

}
