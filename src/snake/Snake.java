package snake;

import javax.swing.*;


public class Snake extends JFrame {


    public Snake() {

        initUI();

    }


    private void initUI() {

        add(new Board());

        setResizable(false);

        pack();


        setTitle("Snake");

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public static void main(String[] args) {

            JFrame snake = new Snake();

            snake.setVisible(true);

    }
}