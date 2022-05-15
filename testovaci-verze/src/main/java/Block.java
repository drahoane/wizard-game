import java.awt.*;
import java.awt.image.BufferedImage;

public class Block extends GameObject  {

    //transient private final BufferedImage blockImg;

    public Block(int x, int y, ID id) {
        super(x, y, id);
        sheetX = 5; sheetY = 2; sheetSizeX = 32; sheetSizeY = 32;
        //blockImg = sh.grab(5, 2, 32, 32);

    }



    @Override
    public void tick() {

    }

    /*@Override
    public void render(Graphics g) {
        g.drawImage(blockImg, x, y, null);
    }*/

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }
}
