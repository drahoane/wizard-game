import java.awt.*;
import java.awt.image.BufferedImage;

public class Block extends GameObject{


    public Block(int x, int y, ID id) {
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
