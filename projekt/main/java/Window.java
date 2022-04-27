import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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


        JButton btnNewGame = new JButton("New game");
        btnNewGame.setBounds(390, 200, 200, 64);
        JButton btnLoadGame = new JButton("Load game");
        btnLoadGame.setBounds(390, 300, 200, 64);

        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.gameState = Game.STATE.Game;

                if(e.getSource() == btnLoadGame) {
                    //load game//////////////
                }
                btnNewGame.setVisible(false);
                btnLoadGame.setVisible(false);
            }
        };

        btnNewGame.addActionListener(al);
        btnLoadGame.addActionListener(al);


        frame.add(btnNewGame);
        frame.add(btnLoadGame);

        frame.add(game);

        frame.setResizable(false);      //cannot resize the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);     //game will appear in the center
        frame.setVisible(true);     //see the window
    }
}
