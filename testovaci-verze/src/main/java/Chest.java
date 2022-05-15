import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

public class Chest extends GameObject {
    private static final Logger LOGGER = Logger.getLogger(Chest.class.getName() );

    //transient private final BufferedImage chestImg;

    public Chest(int x, int y, ID id) {
        super(x, y, id);
        sheetX = 6; sheetY = 2; sheetSizeX = 32; sheetSizeY = 32;
        //chestImg = sh.grab(6, 2, 32, 32);
    }

    @Override
    public void tick() {

    }

    /*@Override
    public void render(Graphics g) {
        g.drawImage(chestImg, x, y, null);
    }*/

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }
}
