import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Data extends JPanel implements ActionListener {
    private Font x, y;
    private Timer timer;
    private Dimension d;

    private int[] ghost_x, ghost_y, ghost_dx, ghost_dy, ghostSpeed;
    public int lives;
    public int scoreCount;
    public int instantSpeed = 3;
    public int[] dir_y, dir_x;
    public int cat_x, cat_y, cat_dy, cat_dx;
    public boolean dying = false;
    public boolean inGame = false;
    private final int validSpeeds[] = { 2, 4, 6, 8 };
    private final int maxSpeed = 8;
    private int n_ghosts;
    private short[] screenData;
    private final short levelData[] = { 19, 18, 18, 18, 18, 18, 18, 18, 18, 26, 26, 26, 26, 18, 18, 22, 0, 0, 0, 0, 17,
            16, 24, 24, 24, 24, 16, 16, 20, 0, 0, 0, 0, 17, 16, 16, 18, 18, 22, 0, 17, 20, 0, 0, 0, 0, 17, 16, 16, 18,
            18, 22, 0, 17, 16, 16, 16, 16, 20, 0, 17, 20, 0, 0, 0, 0, 17, 16, 16, 16, 16, 20, 0, 17, 24, 24, 16, 16, 20,
            0, 17, 20, 0, 0, 0, 0, 17, 16, 16, 24, 24, 20, 0, 21, 0, 0, 17, 16, 16, 22, 17, 20, 0, 0, 0, 0, 17, 16, 20,
            0, 0, 21, 0, 21, 0, 0, 17, 16, 16, 20, 17, 16, 18, 18, 18, 18, 16, 16, 20, 0, 0, 21, 0, 25, 26, 26, 24, 16,
            16, 20, 17, 24, 16, 16, 16, 16, 16, 16, 20, 0, 0, 21, 0, 0, 0, 0, 0, 17, 16, 20, 21, 0, 25, 16, 16, 16, 16,
            16, 20, 0, 0, 17, 18, 18, 18, 18, 18, 16, 16, 20, 21, 0, 0, 17, 24, 24, 24, 24, 28, 0, 0, 25, 24, 24, 24,
            24, 16, 16, 16, 20, 17, 18, 18, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 17, 16, 16, 20, 17, 16, 16, 20, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 17, 16, 16, 20, 17, 24, 16, 16, 18, 18, 18, 18, 22, 0, 0, 19, 18, 18, 18,
            18, 16, 16, 24, 20, 21, 0, 17, 16, 16, 16, 16, 16, 20, 0, 0, 17, 16, 16, 16, 16, 16, 20, 0, 21, 21, 0, 17,
            16, 16, 16, 16, 16, 20, 0, 0, 17, 16, 16, 16, 16, 16, 20, 0, 21, 21, 0, 25, 24, 24, 16, 16, 16, 20, 0, 0,
            17, 16, 16, 16, 24, 24, 28, 0, 21, 21, 0, 0, 0, 0, 17, 16, 16, 20, 0, 0, 17, 16, 16, 20, 0, 0, 0, 0, 21, 17,
            18, 26, 26, 26, 24, 24, 24, 24, 18, 18, 24, 24, 24, 24, 26, 26, 26, 18, 20, 17, 20, 0, 0, 0, 0, 0, 0, 0, 17,
            20, 0, 0, 0, 0, 0, 0, 0, 17, 20, 25, 24, 26, 26, 26, 26, 26, 26, 26, 24, 24, 26, 26, 26, 26, 26, 26, 26, 24,
            28 }; // Maze will be here
    private int n_blocks = 20;
    private int block_size = 36;
    private int req_dx, req_dy;
    private final int max_ghosts = 10;
    private final int screen_size = (n_blocks * block_size);
    public DrawingObjects loader = new DrawingObjects();
    public int levelcount = 0;

    public Data() {
        loader.imageLoader();
        variableSetter();
        addKeyListener(new TAdapter());
        setFocusable(true);
        gameCreater();
    }

    public void variableSetter() {
        screenData = new short[n_blocks * n_blocks];
        timer = new Timer(50, this);
        timer.start();
        d = new Dimension(720, 780);

        ghost_x = new int[max_ghosts];
        ghost_dx = new int[max_ghosts];
        ghost_y = new int[max_ghosts];
        ghost_dy = new int[max_ghosts];
        ghostSpeed = new int[max_ghosts];

        dir_x = new int[4];
        dir_y = new int[4];

    }

    public void playGame(Graphics2D obj) {
        if (dying) {
            death();
        } else {
            catAction();
            ghostAction(obj);
            loader.catDrawing(obj, req_dx, req_dy, cat_x, cat_y, this);
            checkMaze();
        }
    }

    public void IntroScreen(Graphics2D obj) {
        String intro = "Press ENTER to start";
        obj.setColor(new Color(58, 57, 87));
        obj.fillRoundRect((d.width / 4) + 10, d.height / 4, 350, 400, 60, 60);
        x = new Font("Impact", Font.PLAIN, 20);
        obj.setColor(Color.white);
        obj.drawString(intro, (d.width / 4) + 98, d.height / 2);
        obj.drawRect((d.width / 4) + 76, (d.height / 2) - 24, 200, 30);

        obj.drawImage(loader.catIntro, (d.width / 4) + 100, d.height / 2 + 40, this);
        String introName = "CATch";
        y = new Font("MV Boli", Font.BOLD, 75);
        obj.setFont(y);
        obj.setColor(new Color(254, 231, 97));
        obj.drawString(introName, (d.width / 4) + 60, (d.height / 2) - 75);

    }

    public void scoreCounter(Graphics2D obj) {
        String score = "SCORE: " + scoreCount;
        obj.setFont(x);
        obj.setColor(Color.PINK);
        obj.drawString(score, screen_size / 4 * 3, screen_size + 31);

        for (int k = 0; k < lives; k++) {
            obj.drawImage(loader.heart, k * 42 + 10, screen_size + 3, this);
        }

    }

    public void checkMaze() {
        int k = 0;
        boolean done = true;

        while (k < n_blocks * n_blocks && done) {
            if (screenData[k] != 0) {
                done = false;
            }
            k++;
        }

        if (done) {
            levelcount++;
            scoreCount += 10;
            if (n_ghosts < max_ghosts) {
                n_ghosts++;
            }
            if (instantSpeed < maxSpeed) {
                if (levelcount < validSpeeds.length)
                    instantSpeed++;
            } else {
                instantSpeed++;
            }
            levelCreater();
        }

    }

    public void mazeDrawer(Graphics2D obj) {

        int k, x, y;
        k = 0;

        for (y = 0; y < screen_size; y += block_size) {
            for (x = 0; x < screen_size; x += block_size) {

                obj.setColor(Color.DARK_GRAY);
                obj.setStroke(new BasicStroke(5));

                if (levelData[k] == 0) {
                    // obj.fillRect(x, y, block_size, block_size);
                    obj.drawRoundRect(x, y, block_size, block_size, 20, 20);
                    obj.fillRoundRect(x, y, block_size, block_size, 20, 20);

                }

                if ((screenData[k] & 16) != 0) {
                    obj.drawImage(loader.fish, x + 7, y + 5, this);

                }
                k++;

            }
        }
    }

    public void death() {
        lives--;

        if (lives == 0) {
            inGame = false;
        }
        levelCont();
    }

    public void ghostAction(Graphics2D obj) {
        int loc;
        int dirRef;

        for (int k = 0; k < n_ghosts; k++) {

            if (ghost_x[k] % block_size == 0 && ghost_y[k] % block_size == 0) {
                loc = ((ghost_x[k] / block_size)) + (n_blocks * ((int) (ghost_y[k] / block_size)));

                dirRef = 0;

                if ((screenData[loc] & 1) == 0 && ghost_dx[k] != 1) {
                    dir_x[dirRef] = -1;
                    dir_y[dirRef] = 0;
                    dirRef++;

                }

                if ((screenData[loc] & 2) == 0 && ghost_dy[k] != 1) {
                    dir_x[dirRef] = 0;
                    dir_y[dirRef] = -1;
                    dirRef++;

                }

                if ((screenData[loc] & 4) == 0 && ghost_dx[k] != -1) {
                    dir_x[dirRef] = 1;
                    dir_y[dirRef] = 0;
                    dirRef++;

                }

                if ((screenData[loc] & 8) == 0 && ghost_dy[k] != -1) {
                    dir_x[dirRef] = 0;
                    dir_y[dirRef] = 1;
                    dirRef++;

                }

                if (dirRef == 0) {

                    if ((screenData[loc] & 15) == 15) {
                        ghost_dx[k] = 0;
                        ghost_dy[k] = 0;
                    } else {
                        ghost_dx[k] = -1 * ghost_dx[k];
                        ghost_dy[k] = -1 * ghost_dy[k];
                    }

                } else {
                    dirRef = (int) (Math.random() * dirRef);
                    if (dirRef > 3) {
                        dirRef = 3;
                    }
                    ghost_dx[k] = dir_x[dirRef];
                    ghost_dy[k] = dir_y[dirRef];

                }

            }
            ghost_x[k] = ghost_x[k] + (ghost_dx[k] * ghostSpeed[k]);
            ghost_y[k] = ghost_y[k] + (ghost_dy[k] * ghostSpeed[k]);

            loader.ghostDrawing(obj, ghost_x[k], ghost_y[k], this);

            if (cat_x < (ghost_x[k] + 10) && cat_x > (ghost_x[k] - 10) && cat_y < (ghost_y[k] + 10)
                    && cat_y > (ghost_y[k] - 10) && inGame) {

                dying = true;

            }

        }

    }

    public void catAction() {

        int loc, num;

        if (cat_x % block_size == 0 && cat_y % block_size == 0) {

            loc = (cat_x / block_size) + (n_blocks * (int) (cat_y / block_size));
            num = screenData[loc];

            if ((num & 16) != 0) {
                screenData[loc] = (short) (num & 15);
                scoreCount++;
            }

            if (req_dx != 0 || req_dy != 0) {
                if ((req_dx == -1 && req_dy == 0 && (num & 1) == 0) || (req_dx == 1 && req_dy == 0 && (num & 4) == 0)
                        || (req_dx == 0 && req_dy == -1 && (num & 2) == 0)
                        || (req_dx == 0 && req_dy == 1 && (num & 8) == 0)) {

                    cat_dx = req_dx;
                    cat_dy = req_dy;

                }
            }

            if ((cat_dx == -1 && cat_dy == 0 && (num & 1) != 0) || (cat_dx == 1 && cat_dy == 0 && (num & 4) != 0)
                    || (cat_dx == 0 && cat_dy == -1 && (num & 2) != 0)
                    || (cat_dx == 0 && cat_dy == 1 && (num & 8) != 0)) {
                cat_dx = 0;
                cat_dy = 0;
            }
        }
        cat_x += instantSpeed * cat_dx;
        cat_y += instantSpeed * cat_dy;

    }

    public void gameCreater() {
        scoreCount = 0;
        lives = 3;
        levelCreater();
        instantSpeed = 6;
        n_ghosts = 4;

    }

    public void levelCreater() {
        for (int k = 0; k < n_blocks * n_blocks; k++) {
            screenData[k] = levelData[k];
        }
        levelCont();
    }

    public void levelCont() {
        dying = false;

        int dx = 1;
        int dy = 1;
        for (int k = 0; k < n_ghosts; k++) {
            ghost_x[k] = 0;
            ghost_y[k] = 0;
            if ((k % 2) == 0) {
                ghost_dx[k] = dx;
                ghost_dy[k] = 0;
                dx = -dx;
            } else {
                ghost_dx[k] = 0;
                ghost_dy[k] = dy;
                dy = -dy;
            }
            ghostSpeed[k] = instantSpeed;

        }

        cat_x = block_size * 16;
        cat_y = block_size * 14;

        cat_dx = 0;
        cat_dy = 0;
        // Sets the requests to zero so cat will not move after restart
        req_dx = 0;
        req_dy = 0;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    class TAdapter extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (inGame) {
                if (key == KeyEvent.VK_LEFT) {
                    req_dx = -1;
                    req_dy = 0;
                } else if (key == KeyEvent.VK_RIGHT) {
                    req_dx = 1;
                    req_dy = 0;
                } else if (key == KeyEvent.VK_UP) {
                    req_dx = 0;
                    req_dy = -1;
                } else if (key == KeyEvent.VK_DOWN) {
                    req_dx = 0;
                    req_dy = 1;
                }
            } else {
                if (key == KeyEvent.VK_ENTER) {
                    inGame = true;
                    gameCreater();
                }
            }

        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(new Color(58, 57, 87).darker());
        g2.fillRect(0, d.height - 60, d.width, d.height);

        g2.setColor(new Color(204, 206, 231));
        g2.fillRect(0, 0, d.width, d.height - 60);
        mazeDrawer(g2);
        scoreCounter(g2);

        if (inGame) {
            playGame(g2);
        } else {
            IntroScreen(g2);
        }
        Toolkit.getDefaultToolkit().sync();
        g2.dispose();

    }

}
