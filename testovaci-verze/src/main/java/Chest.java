import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

public class Chest extends GameObject {
    private static final Logger LOGGER = Logger.getLogger(Chest.class.getName() );


    public Chest(int x, int y, ID id) {
        super(x, y, id);
    }

    @Override
    public void tick() {

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }
}
