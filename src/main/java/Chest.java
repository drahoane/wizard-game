import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Chest extends GameObject {
    private static final Logger LOGGER = Logger.getLogger(Chest.class.getName());

    Handler handler;
    Game game;

    public Chest(int x, int y, ID id, Handler handler, Game game, Sheet sh) {
        super(x, y, id, sh);
        this.handler = handler;
        this.game = game;
        sheetX = 6; sheetY = 2; sheetSizeX = 32; sheetSizeY = 32;
    }


    @Override
    public void tick() {
        for (int i = 0; i < handler.objects.size(); i++) {
            GameObject tempObject = handler.objects.get(i);
            if (tempObject.getId() == ID.Player) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    if (game.ply.chestsLeft == 1) {
                        game.ply.inventory.add("Door key");
                        LOGGER.log(Level.INFO, "Door key has been added to the inventory");
                    }
                    game.ply.mana += 10;
                    handler.objectsToBeRemoved.add(this);
                    game.ply.chestsLeft -= 1;
                    LOGGER.log(Level.INFO, game.ply.chestsLeft + " chests left to loot");
                }
            }
        }
    }


    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }
}
