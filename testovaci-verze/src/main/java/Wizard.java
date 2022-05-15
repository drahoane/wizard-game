import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Wizard extends GameObject {
    private static final Logger LOGGER = Logger.getLogger(Wizard.class.getName());

    FileHandler fileHandler = null;

    Handler handler;
    Game game;

    int hp = 100;
    public ArrayList<String> inventory = new ArrayList<>();

    int chestsLeft = 9;

    public Wizard(int x, int y, ID id, Handler handler, Game game) {
        super(x, y, id);
        this.handler = handler;
        this.game = game;


        try {
            fileHandler = new FileHandler("status.log");
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.addHandler(fileHandler);

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

        for(int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ID.Block) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    x += speedX * -1;
                    y += speedY * -1;

                }
            }

            if (tempObject.getId() == ID.Chest) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    if (chestsLeft == 1) {
                        inventory.add("Door key");
                        LOGGER.log(Level.FINER,"Door key has been added to the inventory");
                    }
                    game.mana += 10;
                    handler.removeObject(tempObject);
                    chestsLeft -= 1;
                    /*
                    System.out.println("chestka sebrana");
                    if (chestsLeft == 0) {
                        System.out.println("tahle byla posledni");
                    } else {
                        System.out.println("zbyva: " + chestsLeft);
                    }
                     */
                }
            }

            if (tempObject.getId() == ID.Enemy) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    hp -= 1;

                    if (hp == 0) {
                        handler.removeObject(this);

                    }
                }
            }

            if (tempObject.getId() == ID.Door) {
                if (getBounds().intersects(tempObject.getBounds()) && inventory.contains("Door key")) {
                    //announce the player as a winner
                    game.gameState = Game.STATE.Victory;
                }
                /*if (game.inventory.contains("Door key")){
                    System.out.println("je tam");
                } else {
                    System.out.println("neni tu");
                }*/
            }
        }
    }


    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 48);
    }
}
