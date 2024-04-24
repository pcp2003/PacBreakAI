package pacman;

import utils.GameController;

import javax.swing.*;
import java.awt.*;

public class Pacman extends JFrame {

    public Pacman(GameController c, boolean b, int seed) {
        EventQueue.invokeLater(() -> {
            add(new PacmanBoard(c, b, seed));

            setTitle("Pacman");
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setSize(380, 420);
            setLocationRelativeTo(null);
            setVisible(true);
        });
    }
}
