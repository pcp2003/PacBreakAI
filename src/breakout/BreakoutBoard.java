package breakout;

import utils.Commons;
import utils.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class BreakoutBoard extends JPanel {

    public static final int LEFT = 1;
    public static final int RIGHT = 2;

    private Timer timer;
    private String message = "Game Over";
    private Ball ball;
    private Paddle paddle;
    private Brick[] bricks;
    private boolean inGame = true;

    private GameController controller;
    private boolean withGui;
    private Random r = new Random();
    private double time;
    private int kills;

    public BreakoutBoard() {
        this.withGui = true;
        initBoard();
    }

    /**
     * Creates a new BreakoutBoard with a controller. If the boolean parameter
     * withGui is set to false, indicating the absence of a graphical user
     * interface, the program operates in headless mode; otherwise, it initiates
     * with a graphical user interface.
     *
     * @param controller controller used to get next action of the player
     * @param withGui    run with GUI
     * @param seed       the initial seed
     */

    public BreakoutBoard(GameController controller, boolean withGui, int seed) {
        this.controller = controller;
        this.withGui = withGui;
        r.setSeed(seed);

        initBoard();
    }

    private void initBoard() {
        if (withGui) {
            setFocusable(true);
            setPreferredSize(new Dimension(Commons.WIDTH, Commons.HEIGHT));
        }
        gameInit();
    }

    public void makeMove(int move) {
        paddle.makeMove(move);
    }

    private void gameInit() {

        bricks = new Brick[Commons.N_OF_BRICKS];

        ball = new Ball();
        paddle = new Paddle();

        bricks[0] = new Brick(r.nextInt(6) * 40 + 30, r.nextInt(5) * 10 + 50);

        if (withGui) {
            timer = new Timer(Commons.PERIOD, new GameCycle());
            timer.start();
        }
    }

    public void runSimulation() {
        while (inGame) {
            int move = controller.nextMove(getState());
            makeMove(move);
            ball.move();
            paddle.move();
            checkCollision();
            time++;
            if (time > 100000)
                break;
        }
    }

    public void setSeed(int seed) {
        r.setSeed(seed);
    }

    public double getFitness() {
        return kills * 100000 + 100000 - time;
    }

    private double[] getState() {
        double[] state = new double[Commons.BREAKOUT_STATE_SIZE];

        state[0] = ball.getX();
        state[1] = ball.getY();
        state[2] = ball.getYDir();

        state[3] = paddle.getX();
        state[4] = paddle.getY();

        state[5] = bricks[0].getX();
        state[6] = bricks[0].getY();

        return state;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        var g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        if (inGame) {
            drawObjects(g2d);
        } else {
            gameFinished(g2d);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawObjects(Graphics2D g2d) {
        g2d.drawImage(ball.getImage(), ball.getX(), ball.getY(), ball.getImageWidth(), ball.getImageHeight(), this);
        g2d.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(), paddle.getImageWidth(), paddle.getImageHeight(),
                this);
        for (int i = 0; i < Commons.N_OF_BRICKS; i++) {
            if (!bricks[i].isDestroyed()) {
                g2d.drawImage(bricks[i].getImage(), bricks[i].getX(), bricks[i].getY(), bricks[i].getImageWidth(),
                        bricks[i].getImageHeight(), this);
            }
        }
        g2d.drawString(getFitness() + "", 10, 10);
    }

    private void gameFinished(Graphics2D g2d) {

        var font = new Font("Verdana", Font.BOLD, 18);
        FontMetrics fontMetrics = this.getFontMetrics(font);

        g2d.setColor(Color.BLACK);
        g2d.setFont(font);
        g2d.drawString(message, (Commons.WIDTH - fontMetrics.stringWidth(message)) / 2, Commons.WIDTH / 2);
    }

    private class GameCycle implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            doGameCycle();
        }
    }

    private void doGameCycle() {
        paddle.makeMove(controller.nextMove(getState()));
        ball.move();
        paddle.move();
        checkCollision();
        time++;
        repaint();
    }

    private void stopGame() {
        inGame = false;
        if (withGui) {
            timer.stop();
        }


    }

    private void moveBrick() {
        bricks[0].setX(r.nextInt(6) * 40 + 30);
        bricks[0].setY(r.nextInt(5) * 10 + 50);
    }

    private void checkCollision() {
        if (ball.getRect().getMaxY() > Commons.BOTTOM_EDGE) {
            stopGame();
        }

        if ((ball.getRect()).intersects(paddle.getRect())) {

            int paddleLPos = (int) paddle.getRect().getMinX();
            int ballLPos = (int) ball.getRect().getMinX();

            int first = paddleLPos + 8;
            int second = paddleLPos + 16;
            int third = paddleLPos + 24;
            int fourth = paddleLPos + 32;

            if (ballLPos < first) {
                ball.setXDir(-1);
                ball.setYDir(-1);
            }

            if (ballLPos >= first && ballLPos < second) {
                ball.setXDir(-1);
                ball.setYDir(-1 * ball.getYDir());
            }

            if (ballLPos >= second && ballLPos < third) {
                ball.setXDir(0);
                ball.setYDir(-1);
            }

            if (ballLPos >= third && ballLPos < fourth) {
                ball.setXDir(1);
                ball.setYDir(-1 * ball.getYDir());
            }

            if (ballLPos > fourth) {
                ball.setXDir(1);
                ball.setYDir(-1);
            }
        }

        for (int i = 0; i < Commons.N_OF_BRICKS; i++) {

            if ((ball.getRect()).intersects(bricks[i].getRect())) {
                int ballLeft = (int) ball.getRect().getMinX();
                int ballHeight = (int) ball.getRect().getHeight();
                int ballWidth = (int) ball.getRect().getWidth();
                int ballTop = (int) ball.getRect().getMinY();

                var pointRight = new Point(ballLeft + ballWidth + 1, ballTop);
                var pointLeft = new Point(ballLeft - 1, ballTop);
                var pointTop = new Point(ballLeft, ballTop - 1);
                var pointBottom = new Point(ballLeft, ballTop + ballHeight + 1);

                if (bricks[i].getRect().contains(pointRight)) {
                    ball.setXDir(-1);
                } else if (bricks[i].getRect().contains(pointLeft)) {
                    ball.setXDir(1);
                }

                if (bricks[i].getRect().contains(pointTop)) {

                    ball.setYDir(1);
                } else if (bricks[i].getRect().contains(pointBottom)) {

                    ball.setYDir(-1);
                }
                moveBrick();
                kills++;
            }
        }
    }

}
