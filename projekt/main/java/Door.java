import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

public class Door extends GameObject {
    private static final Logger LOGGER = Logger.getLogger(Door.class.getName() );

    private BufferedImage chestImg;

    public Door(int x, int y, ID id, Sheet sh) {
        super(x, y, id, sh);

        chestImg = sh.grab(7, 2, 32, 32);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(chestImg, x, y, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }
}
