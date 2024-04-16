//package pacman;
//
//import utils.Commons;
//import utils.GameController;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
//import java.util.Random;
//
//public class PacmanBoard extends JPanel implements ActionListener {
//
//    /**
//     *
//     */
//    private static final long serialVersionUID = 1L;
//
//    private Random r = new Random();
//
//    private static final int NONE = 0;
//    private static final int LEFT = 1;
//    private static final int RIGHT = 2;
//    private static final int UP = 3;
//    private static final int DOWN = 4;
//
//    private Dimension d;
//    private final Font smallFont = new Font("Helvetica", Font.BOLD, 14);
//
//    private Image ii;
//    private final Color dotColor = new Color(192, 192, 0);
//    private Color mazeColor;
//
//    private boolean inGame = false;
//    private boolean dying = false;
//
//    private final int BLOCK_SIZE = 24;
//    public final static int N_BLOCKS = 15;
//    private final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;
//    private final int PAC_ANIM_DELAY = 2;
//    private final int PACMAN_ANIM_COUNT = 4;
//    public final static int MAX_GHOSTS = 12;
//
//    private int pacAnimCount = PAC_ANIM_DELAY;
//    private int pacAnimDir = 1;
//    private int pacmanAnimPos = 0;
//    private int N_GHOSTS = 6;
//    private int pacsLeft, score;
//    private int[] dx, dy;
//    private int[] ghost_x, ghost_y, ghost_dx, ghost_dy, ghostSpeed;
//
//    private Image ghost;
//    private Image pacman1, pacman2up, pacman2left, pacman2right, pacman2down;
//    private Image pacman3up, pacman3down, pacman3left, pacman3right;
//    private Image pacman4up, pacman4down, pacman4left, pacman4right;
//
//    private int pacman_x, pacman_y, pacmand_x, pacmand_y;
//    private int req_dx, req_dy, view_dx, view_dy;
//
//    private int[] state = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
//            1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
//            1, 1, 1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, 1,
//            1, 1, 1, 1, -1, 1, 1, 1, 1, 1, -1, -1, -1, -1, -1, -1, -1, 1, 1, -1, 1, 1, 1, 1, 1, 1, 1, 1, -1, 1, 1, 1, 1,
//            1, -1, 1, 1, 1, 1, 1, 1, 1, 1, -1, 1, 1, 1, 1, 1, -1, 1, 1, 1, 1, 1, 1, 1, 1, -1, 1, 1, 1, 1, 1, -1, 1, 1,
//            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, 1, 1, 1, 1, 1, 1, 1,
//            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
//
//    private final short[] levelData = {19, 26, 26, 26, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 22, 21, 0, 0, 0, 17, 16,
//            16, 16, 16, 16, 16, 16, 16, 16, 20, 21, 0, 0, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20, 21, 0, 0, 0,
//            17, 16, 16, 24, 16, 16, 16, 16, 16, 16, 20, 17, 18, 18, 18, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 20, 17,
//            16, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 16, 24, 20, 25, 16, 16, 16, 24, 24, 28, 0, 25, 24, 24, 16, 20, 0,
//            21, 1, 17, 16, 20, 0, 0, 0, 0, 0, 0, 0, 17, 20, 0, 21, 1, 17, 16, 16, 18, 18, 22, 0, 19, 18, 18, 16, 20, 0,
//            21, 1, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 20, 0, 21, 1, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16,
//            20, 0, 21, 1, 17, 16, 16, 16, 16, 16, 18, 16, 16, 16, 16, 20, 0, 21, 1, 17, 16, 16, 16, 16, 16, 16, 16, 16,
//            16, 16, 20, 0, 21, 1, 25, 24, 24, 24, 24, 24, 24, 24, 24, 16, 16, 16, 18, 20, 9, 8, 8, 8, 8, 8, 8, 8, 8, 8,
//            25, 24, 24, 24, 28};
//
//    private final int validSpeeds[] = {1, 2, 3, 4, 6, 8};
//    private final int maxSpeed = 6;
//
//    private int currentSpeed = 3;
//    private short[] screenData;
//    private Timer timer;
//    private GameController controller;
//    private boolean withGui;
//
//    private int steps = 0;
//
//    private int previous_x;
//
//    private int previous_y;
//
//    public PacmanBoard(GameController controller, boolean withGui, int seed) {
//        this.controller = controller;
//        this.withGui = withGui;
//        r.setSeed(seed);
//        initVariables();
//    }
//
//    private void initBoard() {
//
//        addKeyListener(new TAdapter());
//
//        setFocusable(true);
//
//        setBackground(Color.black);
//    }
//
//    private void initVariables() {
//
//        screenData = new short[N_BLOCKS * N_BLOCKS];
//        ghost_x = new int[MAX_GHOSTS];
//        ghost_dx = new int[MAX_GHOSTS];
//        ghost_y = new int[MAX_GHOSTS];
//        ghost_dy = new int[MAX_GHOSTS];
//        ghostSpeed = new int[MAX_GHOSTS];
//        dx = new int[4];
//        dy = new int[4];
//
//        if (withGui) {
//            mazeColor = new Color(5, 100, 5);
//            d = new Dimension(400, 400);
//
//            loadImages();
//            initBoard();
//
//            timer = new Timer(100, this);
//            timer.start();
//        }
//    }
//
//    public void runSimulation() {
//        initGame();
//        inGame = true;
//        while (inGame) {
//            int action = controller.nextMove(getState());
//            makeMove(action);
//
//            playGame();
//            steps++;
//        }
//    }
//
//    @Override
//    public void addNotify() {
//        super.addNotify();
//
//        initGame();
//    }
//
//    public void makeMove(int move) {
//
//        if (move == NONE) {
//            req_dx = 0;
//            req_dy = 0;
//        } else if (move == LEFT) {
//            req_dx = -1;
//            req_dy = 0;
//        } else if (move == RIGHT) {
//            req_dx = 1;
//            req_dy = 0;
//        } else if (move == UP) {
//            req_dx = 0;
//            req_dy = -1;
//        } else if (move == DOWN) {
//            req_dx = 0;
//            req_dy = 1;
//        }
//
//    }
//
//    private void doAnim() {
//
//        pacAnimCount--;
//
//        if (pacAnimCount <= 0) {
//            pacAnimCount = PAC_ANIM_DELAY;
//            pacmanAnimPos = pacmanAnimPos + pacAnimDir;
//
//            if (pacmanAnimPos == (PACMAN_ANIM_COUNT - 1) || pacmanAnimPos == 0) {
//                pacAnimDir = -pacAnimDir;
//            }
//        }
//    }
//
//    private void playGame() {
//
//        if (dying) {
//
//            death();
//
//        } else {
//
//            movePacman();
//            moveGhosts();
//            checkMaze();
//        }
//    }
//
//    private void playGame(Graphics2D g2d) {
//
//        if (dying) {
//
//            death();
//
//        } else {
//
//            movePacman();
//            drawPacman(g2d);
//            moveGhosts();
//            drawGhost(g2d);
//            checkMaze();
//        }
//    }
//
//    private void showIntroScreen(Graphics2D g2d) {
//        timer.stop();
//        g2d.setColor(new Color(0, 32, 48));
//        g2d.fillRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);
//        g2d.setColor(Color.white);
//        g2d.drawRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);
//
//        String s = "Press s to start.";
//        Font small = new Font("Helvetica", Font.BOLD, 14);
//        FontMetrics metr = this.getFontMetrics(small);
//
//        g2d.setColor(Color.white);
//        g2d.setFont(small);
//        g2d.drawString(s, (SCREEN_SIZE - metr.stringWidth(s)) / 2, SCREEN_SIZE / 2);
//    }
//
//    private void drawScore(Graphics2D g) {
//
//        int i;
//        String s;
//
//        g.setFont(smallFont);
//        g.setColor(new Color(96, 128, 255));
//        s = "Score: " + getScore();
//        g.drawString(s, SCREEN_SIZE / 2 + 96, SCREEN_SIZE + 16);
//
//        for (i = 0; i < pacsLeft; i++) {
//            g.drawImage(pacman3left, i * 28 + 8, SCREEN_SIZE + 1, this);
//        }
//    }
//
//    private void checkMaze() {
//
//        short i = 0;
//        boolean finished = true;
//
//        while (i < N_BLOCKS * N_BLOCKS && finished) {
//
//            if ((screenData[i] & 48) != 0) {
//                finished = false;
//            }
//
//            i++;
//        }
//
//        if (finished) {
//
//            score += 50;
//
//            if (N_GHOSTS < MAX_GHOSTS) {
//                N_GHOSTS++;
//            }
//
//            if (currentSpeed < maxSpeed) {
//                currentSpeed++;
//            }
//
//            initLevel();
//        }
//    }
//
//    private void death() {
//
//        pacsLeft--;
//
//        if (pacsLeft == 0) {
//            inGame = false;
//        }
//
//        continueLevel();
//    }
//
//    private void moveGhosts() {
//
//        short i;
//        int pos;
//        int count;
//
//        for (i = 0; i < N_GHOSTS; i++) {
//            if (ghost_x[i] % BLOCK_SIZE == 0 && ghost_y[i] % BLOCK_SIZE == 0) {
//                if (Math.abs(ghost_x[i] - pacman_x) < 12 + BLOCK_SIZE && Math.abs(ghost_y[i] - pacman_y) < 12) {
//                    ghost_x[i] = pacman_x;
//                } else if (Math.abs(ghost_y[i] - pacman_y) == 12 + BLOCK_SIZE && Math.abs(ghost_x[i] - pacman_x) < 12) {
//                    ghost_y[i] = pacman_y;
//                } else {
//                    pos = ghost_x[i] / BLOCK_SIZE + N_BLOCKS * (int) (ghost_y[i] / BLOCK_SIZE);
//
//                    count = 0;
//
//                    if ((screenData[pos] & 1) == 0 && ghost_dx[i] != 1) {
//                        dx[count] = -1;
//                        dy[count] = 0;
//                        count++;
//                    }
//
//                    if ((screenData[pos] & 2) == 0 && ghost_dy[i] != 1) {
//                        dx[count] = 0;
//                        dy[count] = -1;
//                        count++;
//                    }
//
//                    if ((screenData[pos] & 4) == 0 && ghost_dx[i] != -1) {
//                        dx[count] = 1;
//                        dy[count] = 0;
//                        count++;
//                    }
//
//                    if ((screenData[pos] & 8) == 0 && ghost_dy[i] != -1) {
//                        dx[count] = 0;
//                        dy[count] = 1;
//                        count++;
//                    }
//
//                    if (count == 0) {
//
//                        if ((screenData[pos] & 15) == 15) {
//                            ghost_dx[i] = 0;
//                            ghost_dy[i] = 0;
//                        } else {
//                            ghost_dx[i] = -ghost_dx[i];
//                            ghost_dy[i] = -ghost_dy[i];
//                        }
//
//                    } else {
//
//                        count = r.nextInt(count);
//
//                        if (count > 3) {
//                            count = 3;
//                        }
//
//                        ghost_dx[i] = dx[count];
//                        ghost_dy[i] = dy[count];
//                    }
//
//                    ghost_x[i] = ghost_x[i] + (ghost_dx[i] * BLOCK_SIZE);
//                    ghost_y[i] = ghost_y[i] + (ghost_dy[i] * BLOCK_SIZE);
//                }
//            }
//
//            if (pacman_x > (ghost_x[i] - 12) && pacman_x < (ghost_x[i] + 12) && pacman_y > (ghost_y[i] - 12)
//                    && pacman_y < (ghost_y[i] + 12) && inGame) {
//
//                dying = true;
//            }
//        }
//    }
//
//    private void drawGhost(Graphics2D g2d) {
//        for (int i = 0; i < N_GHOSTS; i++) {
//
//            g2d.drawImage(ghost, ghost_x[i] + 1, ghost_y[i] + 1, this);
//
//        }
//    }
//
//    private void movePacman() {
//
//        int pos;
//        short ch;
//        pos = pacman_x / BLOCK_SIZE + N_BLOCKS * (int) (pacman_y / BLOCK_SIZE);
//
//        if (req_dx == -pacmand_x && req_dy == -pacmand_y) {
//            pacmand_x = req_dx;
//            pacmand_y = req_dy;
//            view_dx = pacmand_x;
//            view_dy = pacmand_y;
//        }
//
//        if (pacman_x % BLOCK_SIZE == 0 && pacman_y % BLOCK_SIZE == 0) {
//
//            pos = pacman_x / BLOCK_SIZE + N_BLOCKS * (int) (pacman_y / BLOCK_SIZE);
//            ch = screenData[pos];
//
//            if ((ch & 16) != 0) {
//                screenData[pos] = (short) (ch & 15);
//                state[pos] = 0;
//                score++;
//            }
//
//            if (req_dx != 0 || req_dy != 0) {
//                if (!((req_dx == -1 && req_dy == 0 && (ch & 1) != 0) || (req_dx == 1 && req_dy == 0 && (ch & 4) != 0)
//                        || (req_dx == 0 && req_dy == -1 && (ch & 2) != 0)
//                        || (req_dx == 0 && req_dy == 1 && (ch & 8) != 0))) {
//                    pacmand_x = req_dx;
//                    pacmand_y = req_dy;
//                    view_dx = pacmand_x;
//                    view_dy = pacmand_y;
//                }
//            }
//
//            // Check for standstill
//            if ((pacmand_x == -1 && pacmand_y == 0 && (ch & 1) != 0)
//                    || (pacmand_x == 1 && pacmand_y == 0 && (ch & 4) != 0)
//                    || (pacmand_x == 0 && pacmand_y == -1 && (ch & 2) != 0)
//                    || (pacmand_x == 0 && pacmand_y == 1 && (ch & 8) != 0)) {
//                pacmand_x = 0;
//                pacmand_y = 0;
//            }
//        }
//        pacman_x = pacman_x + BLOCK_SIZE * pacmand_x;
//        pacman_y = pacman_y + BLOCK_SIZE * pacmand_y;
//
//    }
//
//    private void drawPacman(Graphics2D g2d) {
//
//        if (view_dx == -1) {
//            drawPacnanLeft(g2d);
//        } else if (view_dx == 1) {
//            drawPacmanRight(g2d);
//        } else if (view_dy == -1) {
//            drawPacmanUp(g2d);
//        } else {
//            drawPacmanDown(g2d);
//        }
//    }
//
//    private void drawPacmanUp(Graphics2D g2d) {
//
//        switch (pacmanAnimPos) {
//            case 1:
//                g2d.drawImage(pacman2up, pacman_x + 1, pacman_y + 1, this);
//                break;
//            case 2:
//                g2d.drawImage(pacman3up, pacman_x + 1, pacman_y + 1, this);
//                break;
//            case 3:
//                g2d.drawImage(pacman4up, pacman_x + 1, pacman_y + 1, this);
//                break;
//            default:
//                g2d.drawImage(pacman1, pacman_x + 1, pacman_y + 1, this);
//                break;
//        }
//    }
//
//    private void drawPacmanDown(Graphics2D g2d) {
//
//        switch (pacmanAnimPos) {
//            case 1:
//                g2d.drawImage(pacman2down, pacman_x + 1, pacman_y + 1, this);
//                break;
//            case 2:
//                g2d.drawImage(pacman3down, pacman_x + 1, pacman_y + 1, this);
//                break;
//            case 3:
//                g2d.drawImage(pacman4down, pacman_x + 1, pacman_y + 1, this);
//                break;
//            default:
//                g2d.drawImage(pacman1, pacman_x + 1, pacman_y + 1, this);
//                break;
//        }
//    }
//
//    private void drawPacnanLeft(Graphics2D g2d) {
//
//        switch (pacmanAnimPos) {
//            case 1:
//                g2d.drawImage(pacman2left, pacman_x + 1, pacman_y + 1, this);
//                break;
//            case 2:
//                g2d.drawImage(pacman3left, pacman_x + 1, pacman_y + 1, this);
//                break;
//            case 3:
//                g2d.drawImage(pacman4left, pacman_x + 1, pacman_y + 1, this);
//                break;
//            default:
//                g2d.drawImage(pacman1, pacman_x + 1, pacman_y + 1, this);
//                break;
//        }
//    }
//
//    private void drawPacmanRight(Graphics2D g2d) {
//
//        switch (pacmanAnimPos) {
//            case 1:
//                g2d.drawImage(pacman2right, pacman_x + 1, pacman_y + 1, this);
//                break;
//            case 2:
//                g2d.drawImage(pacman3right, pacman_x + 1, pacman_y + 1, this);
//                break;
//            case 3:
//                g2d.drawImage(pacman4right, pacman_x + 1, pacman_y + 1, this);
//                break;
//            default:
//                g2d.drawImage(pacman1, pacman_x + 1, pacman_y + 1, this);
//                break;
//        }
//    }
//
//    private void drawMaze(Graphics2D g2d) {
//
//        short i = 0;
//        int x, y;
//
//        for (y = 0; y < SCREEN_SIZE; y += BLOCK_SIZE) {
//            for (x = 0; x < SCREEN_SIZE; x += BLOCK_SIZE) {
//
//                g2d.setColor(mazeColor);
//                g2d.setStroke(new BasicStroke(2));
//
//                if ((screenData[i] & 1) != 0) {
//                    g2d.drawLine(x, y, x, y + BLOCK_SIZE - 1);
//                }
//
//                if ((screenData[i] & 2) != 0) {
//                    g2d.drawLine(x, y, x + BLOCK_SIZE - 1, y);
//                }
//
//                if ((screenData[i] & 4) != 0) {
//                    g2d.drawLine(x + BLOCK_SIZE - 1, y, x + BLOCK_SIZE - 1, y + BLOCK_SIZE - 1);
//                }
//
//                if ((screenData[i] & 8) != 0) {
//                    g2d.drawLine(x, y + BLOCK_SIZE - 1, x + BLOCK_SIZE - 1, y + BLOCK_SIZE - 1);
//                }
//
//                if ((screenData[i] & 16) != 0) {
//                    g2d.setColor(dotColor);
//                    g2d.fillRect(x + 11, y + 11, 2, 2);
//                }
//
//                i++;
//            }
//        }
//    }
//
//    private void initGame() {
//
//        score = 0;
//
//        pacsLeft = 2;
//        score = 0;
//        initLevel();
//        N_GHOSTS = 6;
//        currentSpeed = 3;
//    }
//
//    private void initLevel() {
//
//        int i;
//        for (i = 0; i < N_BLOCKS * N_BLOCKS; i++) {
//            screenData[i] = levelData[i];
//        }
//
//        continueLevel();
//    }
//
//    private void continueLevel() {
//
//        short i;
//        int dx = 1;
//        int random;
//
//        for (i = 0; i < N_GHOSTS; i++) {
//
//            ghost_y[i] = 4 * BLOCK_SIZE;
//            ghost_x[i] = 4 * BLOCK_SIZE;
//            ghost_dy[i] = 0;
//            ghost_dx[i] = dx;
//            dx = -dx;
//            random = (int) (r.nextDouble() * (currentSpeed + 1));
//
//            if (random > currentSpeed) {
//                random = currentSpeed;
//            }
//
//            ghostSpeed[i] = validSpeeds[random];
//        }
//
//        pacman_x = 7 * BLOCK_SIZE;
//        pacman_y = 11 * BLOCK_SIZE;
//        pacmand_x = 0;
//        pacmand_y = 0;
//        req_dx = 0;
//        req_dy = 0;
//        view_dx = -1;
//        view_dy = 0;
//        dying = false;
//    }
//
//    private void loadImages() {
//
//        ghost = new ImageIcon("images/ghost.png").getImage();
//        pacman1 = new ImageIcon("images/pacman.png").getImage();
//        pacman2up = new ImageIcon("images/up1.png").getImage();
//        pacman3up = new ImageIcon("images/up2.png").getImage();
//        pacman4up = new ImageIcon("images/up3.png").getImage();
//        pacman2down = new ImageIcon("images/down1.png").getImage();
//        pacman3down = new ImageIcon("images/down2.png").getImage();
//        pacman4down = new ImageIcon("images/down3.png").getImage();
//        pacman2left = new ImageIcon("images/left1.png").getImage();
//        pacman3left = new ImageIcon("images/left2.png").getImage();
//        pacman4left = new ImageIcon("images/left3.png").getImage();
//        pacman2right = new ImageIcon("images/right1.png").getImage();
//        pacman3right = new ImageIcon("images/right2.png").getImage();
//        pacman4right = new ImageIcon("images/right3.png").getImage();
//
//    }
//
//    @Override
//    public void paintComponent(Graphics g) {
//        super.paintComponent(g);
//
//        doDrawing(g);
//    }
//
//    private void doDrawing(Graphics g) {
//
//        Graphics2D g2d = (Graphics2D) g;
//
//        g2d.setColor(Color.black);
//        g2d.fillRect(0, 0, d.width, d.height);
//
//        drawMaze(g2d);
//        drawScore(g2d);
//        doAnim();
//
//        if (inGame) {
//            playGame(g2d);
//        } else {
//            showIntroScreen(g2d);
//        }
//
//        g2d.drawImage(ii, 5, 5, this);
//        Toolkit.getDefaultToolkit().sync();
//        g2d.dispose();
//    }
//
//    class TAdapter extends KeyAdapter {
//
//        @Override
//        public void keyPressed(KeyEvent e) {
//
//            int key = e.getKeyCode();
//
//            if (key == 's' || key == 'S') {
//                inGame = true;
//                timer.start();
//                initGame();
//            }
//        }
//    }
//
//    public int getScore() {
//        return score * 1000 + steps / 100;
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (inGame)
//            steps++;
//
//        int action = controller.nextMove(getState());
//        makeMove(action);
//        repaint();
//    }
//
//    private int[] getState() {
//        int[] state = new int[Commons.PACMAN_STATE_SIZE];
//        for (int i = 0; i < N_BLOCKS * N_BLOCKS; i++) {
//            state[i] = this.state[i];
//        }
//        for (int i = 0; i < N_GHOSTS; i++) {
//            int pos = ghost_x[i] / BLOCK_SIZE + N_BLOCKS * (int) (ghost_y[i] / BLOCK_SIZE);
//            state[pos] = 2;
//        }
//
//        previous_x = pacman_x;
//        previous_y = pacman_y;
//
//        int pos = pacman_x / BLOCK_SIZE + N_BLOCKS * (int) (pacman_y / BLOCK_SIZE);
//        state[pos] = 3;
//
//        return state;
//    }
//
//    public void setSeed(int seed) {
//        r.setSeed(seed);
//    }
//
//    public double getFitness() {
//        return getScore();
//    }
//}
