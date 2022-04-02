import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {
    private Handler handler;
    private Camera camera;

    public MouseInput(Handler handler, Camera camera) {
        this.handler = handler;
        this.camera = camera;
    }

    /**
     * Based on camera's focus and the directions of the mouse calculate spell's trace.
     * Iterate through game objects.
     * If the game object is a player, render spell's graphics coming from this object - player.
     * @param e
     */
    public void mousePressed(MouseEvent e) {
        int mx = (int) (e.getX() + camera.getX());
        int my = (int) (e.getY() + camera.getY());

        for(int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if(tempObject.getId() == ID.Player) {
                handler.addObject(new Spell(tempObject.getX() +16, tempObject.getY() +24, ID.Spell, handler, mx, my));
            }
        }
    }
}
