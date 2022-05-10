import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {
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
     * If the game object is a player, render spell's graphics coming from this object - player.
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


        int ax = e.getXOnScreen();
        int ay = e.getYOnScreen();

        if(game.gameState == Game.STATE.Game) {
            if (mouseOver(ax, ay, 675, 162, 875, 182)) {        //serialize
                System.out.println("Saving....");
                game.gameState = Game.STATE.Save;
            }
        }

        if(game.gameState == Game.STATE.Defeat || game.gameState == Game.STATE.Victory) {
            if (mouseOver(ax, ay, 665, 455, 865, 520)) {        //exit
                System.out.println("Terminating....");
                System.exit(0);
            }
        }
    }

    private boolean mouseOver(int ax, int ay, int x, int y, int width, int height) {
        return ax > x && ax < width && ay > y && ay < height;
    }

}
