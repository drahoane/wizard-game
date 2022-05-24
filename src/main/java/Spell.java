import java.awt.*;

public class Spell extends GameObject {

    private final Handler handler;


    public Spell(int x, int y, ID id, Handler handler, Sheet sh, int mx, int my) {
        super(x, y, id, sh);
        this.handler = handler;

        speedX = (mx - x) / 10;
        speedY = (my - y) / 10;
    }

    /**
     * Within time update the object's directions.
     * If the 'spell' object collides with 'block' object or has no speed, remove the spell.
     */
    @Override
    public void tick() {
        x += speedX;
        y += speedY;


        for(int i = 0; i < handler.objects.size(); i++) {
            GameObject tempObject = handler.objects.get(i);

            if(speedX == 0 || speedY == 0) {
                handler.objectsToBeRemoved.add(this);
            }

            if (tempObject.getId() == ID.Block) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    handler.objectsToBeRemoved.add(this);
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, 8, 8);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 8, 8);
    }
}
