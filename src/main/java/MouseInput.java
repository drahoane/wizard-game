import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MouseInput extends MouseAdapter {

    private static final Logger LOGGER = Logger.getLogger(Game.class.getName());

    private final Handler handler;
    private final Camera camera;
    private final Game game;
    private final Sheet sh;


    public MouseInput(Handler handler, Camera camera, Game game, Sheet sh) {
        this.handler = handler;
        this.camera = camera;
        this.game = game;
        this.sh = sh;
    }

    /**
     * Based on camera's focus and the directions of the mouse calculate spell's trace.
     * Iterate through game objects.
     * If the game object is a wizard, render spell's graphics coming from this object.
     * If the mouse is pressed while being on certain coordinates of the screen, manage this action by changing game state for invoking further operations.
     * @param e
     */
    public void mousePressed(MouseEvent e) {
        int mx = (int) (e.getX() + camera.getX());
        int my = (int) (e.getY() + camera.getY());

        for(int i = 0; i < handler.objects.size(); i++) {
            GameObject tempObject = handler.objects.get(i);

            if(tempObject.getId() == ID.Player && game.ply.mana >= 1) {     //cannot shoot the spell without mana
                handler.addObject(new Spell(tempObject.getX() +16, tempObject.getY() +24, ID.Spell, handler, sh, mx, my));    //+16 and +24 -> spell goes from the middle of the player object
                game.ply.mana--;
            }
        }


        int ax = e.getX();
        int ay = e.getY();


        if(game.gameState == Game.STATE.Game) {
            if (mouseOver(ax, ay, 400, 5, 600, 25)) {        //serialize
                LOGGER.log(Level.INFO,"Saving....");
                game.gameState = Game.STATE.Save;
            }
        }

        if(game.gameState == Game.STATE.Defeat || game.gameState == Game.STATE.Victory) {
            if (mouseOver(ax, ay, 390, 300, 590, 370)) {        //exit
                LOGGER.log(Level.INFO,"Terminating....");
                System.exit(0);
            }
        }
    }

    /**
     * Return boolean depending on if the mouse was pressed while being within a certain rectangle.
     *
     * @param ax
     * @param ay
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     */
    protected boolean mouseOver(int ax, int ay, int x, int y, int width, int height) {
        return ax > x && ax < width && ay > y && ay < height;
    }

}
