import java.awt.*;

public class Wizard extends GameObject {

    Handler handler;

    public Wizard(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
    }

    /**
     * Handle the movement changes and check for collision.
     * After no occurrence of collision, based on pressed keys add up the speed in certain direction - move player.
     */
    @Override
    public void tick() {
        x += speedX;
        y += speedY;

        collision();

        //movement
        if(handler.isUp()) speedY = -5;
        else if(!handler.isDown()) speedY = 0;

        if(handler.isDown()) speedY = 5;
        else if(!handler.isUp()) speedY = 0;

        if(handler.isRight()) speedX = 5;
        else if(!handler.isLeft()) speedX = 0;

        if(handler.isLeft()) speedX = -5;
        else if(!handler.isRight()) speedX = 0;
    }

    /**
     * Iterate through the objects.
     * If game object is a 'block', check for collision between player and this block.
     * If collision appears, change player's speed.
     */
    private void collision() {
        for(int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if(tempObject.getId() == ID.Block) {
                if(getBounds().intersects(tempObject.getBounds())) {
                    x += speedX * -1;
                    y += speedY * -1;

                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, 32, 48);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 48);
    }
}
