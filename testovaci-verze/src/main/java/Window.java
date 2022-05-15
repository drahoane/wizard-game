import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Window {

    /**
     * Create a window of the game.
     * Window cannot be resizable, on exit the window closes, window will appear in the center of the screen.
     * @param width
     * @param height
     * @param title
     * @param game
     */
    public Window(int width, int height, String title, Game game) {

        JFrame frame = new JFrame(title);
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));

        frame.add(game);

        frame.setResizable(false);      //cannot resize the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);     //game will appear in the center
        frame.setVisible(true);     //see the window
    }
}
