import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.logging.Logger;

public class Enemy extends GameObject {
    private static final Logger LOGGER = Logger.getLogger(Enemy.class.getName() );

    private final Handler handler;
    Random rnd = new Random();
    int choose = 0;
    int hp = 100;

    //transient private final BufferedImage monImg;

    public Enemy(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        sheetX = 4; sheetY = 1; sheetSizeX = 32; sheetSizeY = 32;
        this.handler = handler;

        //monImg = sh.grab(4, 1, 32, 32);

    }

    @Override
    public void tick() {
        x += speedX;
        y += speedY;

        choose = rnd.nextInt(10);

        for (int i = 0; i < handler.objects.size(); i++) {
            GameObject tempObject = handler.objects.get(i);

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


    /*@Override
    public void render(Graphics g) {
        g.drawImage(monImg, x, y, null);
    }*/

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }

    public Rectangle getBiggerBounds() {
        return new Rectangle(x-16, y-16, 64, 64);
    }
}
