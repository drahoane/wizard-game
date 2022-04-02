import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    Handler handler;

    public KeyInput(Handler handler) {
        this.handler = handler;
    }

    /**
     * Determine which key is pressed by its code.
     * Loop through all game objects, if the object is a player and WASD is pressed, move the player in certain direction.
     * @param e
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        for(int i = 0; i < handler.object.size(); i++) {       //loop through all game objects
            GameObject tempObject = handler.object.get(i);

            if(tempObject.getId() == ID.Player) {      //if WASD is pressed and the object is a player, move in certain direction
                if(key == KeyEvent.VK_W)  handler.setUp(true);
                if(key == KeyEvent.VK_S)  handler.setDown(true);
                if(key == KeyEvent.VK_A)  handler.setLeft(true);
                if(key == KeyEvent.VK_D)  handler.setRight(true);
            }
        }
    }

    /**
     * Determine which key is pressed by its code.
     * Loop through all game objects, if the object is a player and WASD is released, move the player in certain direction.
     * @param e
     */
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        for(int i = 0; i < handler.object.size(); i++) {       //loop through all game objects
            GameObject tempObject = handler.object.get(i);

            if(tempObject.getId() == ID.Player) {      //if WASD is released and the object is a player, stop moving in certain direction
                if(key == KeyEvent.VK_W)  handler.setUp(false);
                if(key == KeyEvent.VK_S)  handler.setDown(false);
                if(key == KeyEvent.VK_A)  handler.setLeft(false);
                if(key == KeyEvent.VK_D)  handler.setRight(false);
            }
        }
    }
}
