import java.awt.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Enemy extends GameObject {

    private static final Logger LOGGER = Logger.getLogger(Enemy.class.getName());

    private final Handler handler;
    Random rnd = new Random();
    int choose = 0;
    int hp = 100;


    public Enemy(int x, int y, ID id, Handler handler, Sheet sh) {
        super(x, y, id, sh);
        sheetX = 4;
        sheetY = 1;
        sheetSizeX = 32;
        sheetSizeY = 32;
        this.handler = handler;
    }

    /**
     * Determine enemy's direction using random.
     * If enemy collides with block, slow it.
     * If enemy collides with spell, decrease its health by half, if health is equal to zero, handler will remove this object.
     */
    @Override
    public void tick() {
        x += speedX;
        y += speedY;

        choose = rnd.nextInt(10);

        for (int i = 0; i < handler.objects.size(); i++) {
            GameObject tempObject = handler.objects.get(i);

            if (tempObject.getId() == ID.Block) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    x += (speedX) * -1;
                    y += (speedY) * -1;

                    speedX *= -1;
                    speedY *= -1;

                } else if (choose == 0) {
                    speedX = (rnd.nextInt(4 - -4) + -4);
                    speedY = (rnd.nextInt(4 - -4) + -4);
                }
            }

            if(tempObject.getId() == ID.Spell) {
                if(getBounds().intersects(tempObject.getBounds())) {
                    hp -= 50;
                    LOGGER.log(Level.INFO,"Enemy has been hit");
                    handler.objectsToBeRemoved.add(this);
                }
            }
        }

        if(hp <= 0) {
            handler.objectsToBeRemoved.add(this);
        }
    }


    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }
}
