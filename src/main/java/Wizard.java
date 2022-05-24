import java.awt.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Wizard extends GameObject {

    private static final Logger LOGGER = Logger.getLogger(Wizard.class.getName());

    Handler handler;
    Game game;

    int hp = 100;
    public int mana = 100;


    public ArrayList<String> inventory = new ArrayList<>();
    int chestsLeft = 9;

    public Wizard(int x, int y, ID id, Handler handler, Game game, Sheet sh) {
        super(x, y, id, sh);
        this.handler = handler;
        this.game = game;
        sheetX = 1; sheetY = 1; sheetSizeX = 32; sheetSizeY = 48;

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
    void collision() {

        for(int i = 0; i < handler.objects.size(); i++) {
            GameObject tempObject = handler.objects.get(i);

            if (tempObject.getId() == ID.Block) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    x += speedX * -1;
                    y += speedY * -1;

                }
            }


            if (tempObject.getId() == ID.Enemy) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    hp -= 1;

                    if (hp == 0) {
                        handler.objectsToBeRemoved.add(this);

                    }
                }
            }

            if (handler.objects.get(i).getId() == ID.Door) {
                if (getBounds().intersects(handler.objects.get(i).getBounds()) && inventory.contains("Door key")) {
                    //announce the player as a winner
                    game.gameState = Game.STATE.Victory;
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
    }


    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 48);
    }
}
