package snake;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;


public class Board extends JPanel implements ActionListener {


    private final int B_WIDTH = 500;
    private final int B_HEIGHT = 500;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 1000;
    private final int RAND_POS = 29;

    private int DELAY = 140;

    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private int dots;
    private int apple_x;
    private int apple_y;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;

    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;

    public Board() {

        initBoard();

    }

    private void initBoard() {

        addKeyListener(new TAdapter());

        setBackground(Color.black);

        setFocusable(true);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        loadImages();

        initGame();

    }

    private void loadImages() {

        ImageIcon iid = new ImageIcon("src/resources/dot.png");
        ball = iid.getImage();

        ImageIcon iia = new ImageIcon("src/resources/apple.png");
        apple = iia.getImage();

        ImageIcon iih = new ImageIcon("src/resources/head.png");
        head = iih.getImage();
    }


    private void initGame() {

        dots = 3;

        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }

        locateApple();

        timer = new Timer(DELAY, this);

        timer.start();

    }

    private void restartGame() {

        inGame = true;

        initGame();

    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        doDrawing(g);

    }

    private void doDrawing(Graphics g) {

        if (inGame) {

            g.drawImage(apple, apple_x, apple_y, this);

            for (int z = 0; z < dots; z++) {

                if (z == 0) {

                    g.drawImage(head, x[z], y[z], this);

                }

                else {

                    g.drawImage(ball, x[z], y[z], this);

                }
            }

            Toolkit.getDefaultToolkit().sync();
        }

        else {

            gameOver(g);
        }
    }

    private void gameOver(Graphics g) {

        String msg = "Game Over";
        String msg2 = "  Score:  "+ dots;

        Font small = new Font("Helvetica", Font.BOLD, 25);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);

        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, (B_HEIGHT / 2));
        g.drawString(msg2, (B_WIDTH - metr.stringWidth(msg)) / 2, (int) (B_HEIGHT / 1.5));
    }


    private void checkApple() {

        if ((x[0] == apple_x) && (y[0] == apple_y)) {

            dots++;

            locateApple();
        }
    }

    private void move() {

        for (int z = dots; z > 0; z--) {

            x[z] = x[(z - 1)];

            y[z] = y[(z - 1)];
        }

        if (leftDirection) {

            x[0] -= DOT_SIZE;
        }

        if (rightDirection) {

            x[0] += DOT_SIZE;
        }

        if (upDirection) {

            y[0] -= DOT_SIZE;
        }

        if (downDirection) {

            y[0] += DOT_SIZE;
        }
    }

    private void checkCollision() {

        for (int z = dots; z > 0; z--) {

            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {

                inGame = false;
            }
        }

        if (y[0] >= B_HEIGHT) {

            inGame = false;
        }

        if (y[0] < 0) {

            inGame = false;
        }

        if (x[0] >= B_WIDTH) {

            inGame = false;
        }

        if (x[0] < 0) {

            inGame = false;
        }

        if (!inGame) {

            timer.stop();
        }
    }

    private void locateApple() {

        int r = (int) (Math.random() * RAND_POS);

        apple_x = ((r * DOT_SIZE));

        r = (int) (Math.random() * RAND_POS);

        apple_y = ((r * DOT_SIZE));
    }

    class gra extends AbstractAction {

        public void actionPerformed(ActionEvent e) {

            removeAll();

            revalidate();

            repaint();

            restartGame();
        }
    }

    class easy extends AbstractAction {

        public void actionPerformed(ActionEvent e) {

            DELAY = 140;
        }
    }

    class medium extends AbstractAction {

        public void actionPerformed(ActionEvent e) {

            DELAY = 90;
        }
    }

    class hard extends AbstractAction {

        public void actionPerformed(ActionEvent e) {

            DELAY = 50;
        }
    }

    class exit extends AbstractAction {

        public void actionPerformed(ActionEvent e) {

            System.exit(0);
        }
    }

    private void menu(){

        JButton b1 = new JButton("new game");
        b1.setBackground(Color.white);
        b1.setPreferredSize(new Dimension(350,30));

        JButton b2 = new JButton("easy");
        b2.setBackground(Color.white);
        b2.setPreferredSize(new Dimension(350,30));

        JButton b3 = new JButton("medium");
        b3.setBackground(Color.white);
        b3.setPreferredSize(new Dimension(350,30));

        JButton b4 = new JButton("hard");
        b4.setBackground(Color.white);
        b4.setPreferredSize(new Dimension(350,30));

        JButton b5 = new JButton("exit");
        b5.setBackground(Color.white);
        b5.setPreferredSize(new Dimension(350,30));

        gra gra = new gra();
        easy easy = new easy();
        medium medium = new medium();
        hard hard = new hard();
        exit exit = new exit();

        this.add(b1);
        b1.setAction(gra);
        b1.setText("new game");

        this.add(b2);
        b2.setAction(easy);
        b2.setText("easy");

        this.add(b3);
        b3.setAction(medium);
        b3.setText("medium");

        this.add(b4);
        b4.setAction(hard);
        b4.setText("hard");

        this.add(b5);
        b5.setAction(exit);
        b5.setText("exit");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (inGame) {

            checkApple();

            checkCollision();

            move();
        }

        repaint();

        if(!inGame){

             menu();

        }
    }


    private class TAdapter extends KeyAdapter {

        @Override

        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ( ((key == KeyEvent.VK_LEFT) | (key == KeyEvent.VK_A)) && (!rightDirection)) {

                leftDirection = true;

                upDirection = false;

                downDirection = false;
            }

            if ( ((key == KeyEvent.VK_RIGHT) | (key == KeyEvent.VK_D)) && (!leftDirection)) {

                rightDirection = true;

                upDirection = false;

                downDirection = false;
            }

            if ( ((key == KeyEvent.VK_UP) | (key == KeyEvent.VK_W)) && (!downDirection)) {

                upDirection = true;

                rightDirection = false;

                leftDirection = false;
            }

            if ( ((key == KeyEvent.VK_DOWN) | (key == KeyEvent.VK_S)) && (!upDirection)) {

                downDirection = true;

                rightDirection = false;

                leftDirection = false;
            }
        }
    }
}
