package loa;

import java.awt.*;
import static loa.Side.*;
import static loa.Piece.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Container;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JFrame;
import java.awt.Frame;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JComponent;


class GUI extends JFrame {

    JMenuBar menubar;
    JMenu menu;
    JMenuItem one, two, zero, reset, white, black;
    JButton[][] squares = new JButton[8][8];
    String[][] pieces = new String[8][8];
    JLabel text;
    Container box = getContentPane();
    Click pushed;
    Color checker;
    JPanel me = new JPanel();
    Board game;
    JPanel board;


    public static void main(String[] args) {
        GUI x = new GUI();
    }

    GUI() {
        box.setLayout(null);
        box.setBackground(Color.black);
        game = new Board(Board.getinitial(), BLACK);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(400, 400);
        setTitle("Lines of Action");
    }

    void makeBoard() {
        board.setLayout(new GridLayout(8, 8));
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squares[i][j] = new JButton();
                if ((j+i)%2 != 0) {
                    squares[i][j].setBackground(Color.lightGray);
                }
                else {
                    squares[i][j].setBackground(Color.white);
                }
                board.add(squares[i][j]);
            }
        }
        box.add(board, BorderLayout.CENTER);
    }

    public void makeMenu() {;
        Click c = new Click();
        menubar = new JMenuBar();
        menu = new JMenu("New Game");
        menu.setMnemonic('f');
        reset = menu.add(new JMenuItem("Restart"));
        reset.addActionListener(c);
        menu.add(reset);
        one = menu.add(new JMenuItem("One Player"));
        one.addActionListener(c);
        menu.add(one);
        two = menu.add(new JMenuItem("Two Player"));
        two.addActionListener(c);
        menu.add(two);
        zero = menu.add(new JMenuItem("AI vs AI"));
        zero.addActionListener(c);
        menu.add(zero);
        menubar.add(menu);
        menu = new JMenu("Pick a side");
        white = menu.add(new JMenuItem("Side White"));
        menu.add(white);
        white.addActionListener(c);
        black = menu.add(new JMenuItem("Side Black"));
        black.addActionListener(c);
        menu.add(black);
        menubar.add(menu);
        text = new JLabel();
        text.setText("Lines of Action");
        box.add(text, BorderLayout.SOUTH);
        box.add(menubar, BorderLayout.NORTH);
        box.add(menubar, BorderLayout.NORTH);
    }

    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.drawRect(10, 10, 15, 15);
        g.drawRect(10, 10, 15, 15);
        for (int i = 0; i < 8; i += 1) {
            for (int j = 0; j < 8; j += 1) {
                if (i % 2 == j % 2) {
                    g.setColor(Color.lightGray);
                } else {
                    g.setColor(Color.GRAY);
                }
                g.fillRect(40 + j * 20, 40 + i * 20, 20, 20);
                if (game.getcontents()[i][j] == BP) {
                    g.setColor(Color.black);
                    g.fillOval(40 + j * 20, 40 + i * 20, 15, 15);
                } else if (game.getcontents()[i][j] == WP) {
                    g.setColor(Color.white);
                    g.fillOval(40 + j * 20, 40 + i * 20, 15, 15);
                }
            }
        }
    }

    void makeListeners() {
        pushed = new Click();
        for (int i = 0; i < 8; i++ ) {
            for (int j = 0; j < 8; j++) {
                squares [i][j].addActionListener(pushed);
            }
        }
    }

    /** Checks for the click. */
    class Click implements ActionListener {
        GUI x;

        /** Checks the click.
         * @param e actionevent */
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            Side sid = BLACK;
            if (source == black) {
                sid = BLACK;
            }
            if (source == white) {
                sid = WHITE;
            }
            if (source == one) {
                Game game = new Game(1, sid, 0, 10);
            }
            if (source == two) {
                Game game = new Game(2, sid, 0, 10);
            }
            if (source == zero) {
                Game game = new Game(0, sid, 0, 10);
            }
            if (source == reset) {
                repaint();
            }
        }
    }
}
 
