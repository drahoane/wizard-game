import java.awt.*;
import java.util.logging.Logger;

public class Spell extends GameObject {
    private static final Logger LOGGER = Logger.getLogger(Spell.class.getName() );


    private final Handler handler;

    public Spell(int x, int y, ID id, Handler handler, int mx, int my) {
        super(x, y, id);
        this.handler = handler;

        speedX = (mx - x) / 10;
        speedY = (my - y) / 10;
    }

    /**
     * Within time update the object's directions.
     * If the 'spell' object collides with 'block' object, remove the spell.
     */
    @Override
    public void tick() {
        x += speedX;
        y += speedY;

        if(speedX == 0 || speedY == 0) {
            handler.removeObject(this);
        }

        for(int i = 0; i < handler.objects.size(); i++) {
            GameObject tempObject = handler.objects.get(i);

            if (tempObject.getId() == ID.Block) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    handler.removeObject(this);
                }
            }
        }
    }



    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 8, 8);
    }
}
