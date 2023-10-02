package Main;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        ImageIcon icon = new ImageIcon("resources/chessIcon.png");
        frame.setIconImage(icon.getImage());
        frame.setMinimumSize(new Dimension(1000, 1000));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(52, 52, 52));
        frame.setLayout(new GridBagLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        Board board = new Board();
        frame.add(board);

        frame.setVisible(true);
    }
}
