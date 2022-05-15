import java.awt.*;

public class Block extends GameObject  {

    public Block(int x, int y, ID id, Sheet sh) {
        super(x, y, id, sh);
        sheetX = 5; sheetY = 2; sheetSizeX = 32; sheetSizeY = 32;
    }



    @Override
    public void tick() {

    }


    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }
}
