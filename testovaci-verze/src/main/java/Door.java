import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

public class Door extends GameObject {
    private static final Logger LOGGER = Logger.getLogger(Door.class.getName() );

    private BufferedImage chestImg;

    public Door(int x, int y, ID id) {
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
