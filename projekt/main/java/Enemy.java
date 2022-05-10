import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.logging.Logger;

public class Enemy extends GameObject{
    private static final Logger LOGGER = Logger.getLogger(Enemy.class.getName() );

    Handler handler;
    Random rnd = new Random();
    int choose = 0;
    int hp = 100;


    public Enemy(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;

    }


    @Override
    public void tick() {
        x += speedX;
        y += speedY;

        choose = rnd.nextInt(10);

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ID.Block) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    x += (speedX*5) * -1;
                    y += (speedY*5) * -1;

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
                    handler.removeObject(tempObject);
                }
            }
        }

        if(hp <= 0) {
            handler.removeObject(this);
        }



    }


    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }

    public Rectangle getBoundsBig() {
        return new Rectangle(x-16, y-16, 64, 64);
    }
}
