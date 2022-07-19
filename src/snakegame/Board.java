/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.util.ArrayList;
import java.util.Collections;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private final int B_WIDTH = 300;
    private final int B_HEIGHT = 300;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 900;
    private final int RANDOM_APPLE_POS = 29;
    private final int DELAY = 200;

    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    ArrayList<Integer> distances = new ArrayList<Integer>();

    private int moveX = 0;
    private int moveY = 0;
    private int dist1;
    private int dist2;
    private int dist3;
    private int apple_x1;
    private int apple_y1;
    private int apple_x2;
    private int apple_y2;
    private int apple_x3;
    private int apple_y3;

    private boolean inGame = true;

    private Timer timer;
    private Image apple;
    private Image head;

    public Board() {
        
        initBoard();
    }
    
    private void initBoard() {

        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initGame();
    }

    private void loadImages() {
        ImageIcon iia = new ImageIcon("src/resources/apple.png");
        apple = iia.getImage();

        ImageIcon iih = new ImageIcon("src/resources/head.png");
        head = iih.getImage();
    }

    private void initGame() {
        x[0] = 150;
        y[0] = 150;

        apple_x1 = ((int) (Math.random() * RANDOM_APPLE_POS)) * DOT_SIZE;
        apple_y1 = ((int) (Math.random() * RANDOM_APPLE_POS)) * DOT_SIZE;
        apple_x2 = ((int) (Math.random() * RANDOM_APPLE_POS)) * DOT_SIZE;
        apple_y2 = ((int) (Math.random() * RANDOM_APPLE_POS)) * DOT_SIZE;
        apple_x3 = ((int) (Math.random() * RANDOM_APPLE_POS)) * DOT_SIZE;
        apple_y3 = ((int) (Math.random() * RANDOM_APPLE_POS)) * DOT_SIZE;

        distances.add(0);
        distances.add(0);
        distances.add(0);
        updateDistance();
        
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void updateDistance() {
        dist1 = checkDistance(apple_x1, apple_y1);
        dist2 = checkDistance(apple_x2, apple_y2);
        dist3 = checkDistance(apple_x3, apple_y3);

        distances.set(0, dist1);
        distances.set(1, dist2);
        distances.set(2, dist3);
        Collections.sort(distances);
    }

    private int checkDistance(int x1, int y1) {
        return (Math.abs(x1-x[0]) + Math.abs(y1-y[0]));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
    
    private void doDrawing(Graphics g) {
        if (!inGame) {
            gameOver(g);
        } else {
            g.drawImage(apple, apple_x1, apple_y1, this);
            g.drawImage(apple, apple_x2, apple_y2, this);
            g.drawImage(apple, apple_x3, apple_y3, this);
            g.drawImage(head, x[0], y[0], this);
    
            Toolkit.getDefaultToolkit().sync();
        }
    }

    private void checkApple() {
        if ((x[0] == apple_x1) && (y[0] == apple_y1)) {
            apple_x1 = 99999;
            apple_y1 = 99999;
        }
        if ((x[0] == apple_x2) && (y[0] == apple_y2)) {
            apple_x2 = 99999;
            apple_y2 = 99999;
        }
        if ((x[0] == apple_x3) && (y[0] == apple_y3)) {
            apple_x3 = 99999;
            apple_y3 = 99999;
        }
    }

    private void move() {
        if (moveX == 0 && moveY == 0) {
            updateDistance();
            if (dist1 == distances.get(0)) {
                moveX = apple_x1 - x[0];
                moveY = apple_y1 - y[0];
            } else if (dist2 == distances.get(0)) {
                moveX = apple_x2 - x[0];
                moveY = apple_y2 - y[0];
            } else if (dist3 == distances.get(0)) {
                moveX = apple_x3 - x[0];
                moveY = apple_y3 - y[0];
            }
            System.out.println(dist1);
            System.out.println(dist2);
            System.out.println(dist3);
            System.out.println(distances.get(0));
            // System.out.println(apple_x1);
            // System.out.println(apple_y1);
            // System.out.println(apple_x2);
            // System.out.println(apple_y2);
            // System.out.println(apple_x3);
            // System.out.println(apple_y3);
            // if (moveX == 0 && moveY == 0) {
            //     inGame = false;
            // }
        }
        if (moveX > 0) {
            x[0] += DOT_SIZE;
            moveX -= DOT_SIZE;
        } else if (moveX < 0) {
            x[0] -= DOT_SIZE;
            moveX += DOT_SIZE;
        } else {
            if (moveY > 0) {
                y[0] += DOT_SIZE;
                moveY -= DOT_SIZE;
            } else if (moveY < 0) {
                y[0] -= DOT_SIZE;
                moveY += DOT_SIZE;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (inGame) {
            checkApple();
            move();
        }

        repaint();
    }

    private void gameOver(Graphics g) {
        
        String msg = "Success!!";
        Font small = new Font("Helvetica", Font.BOLD, 20);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }
}
