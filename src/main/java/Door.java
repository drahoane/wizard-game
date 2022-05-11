import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

public class Door extends GameObject  {
    private static final Logger LOGGER = Logger.getLogger(Door.class.getName() );


    public Door(int x, int y, ID id, Sheet sh) {
        super(x, y, id, sh);
        sheetX = 7; sheetY = 2; sheetSizeX = 32; sheetSizeY = 32;
        //doorImg = sh.grab(7, 2, 32, 32);
    }

    @Override
    public void tick() {

    }


    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }
}
