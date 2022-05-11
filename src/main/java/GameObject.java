import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

public abstract class GameObject implements Serializable {

    protected int x, y, sheetX, sheetY, sheetSizeX, sheetSizeY;
    protected float speedX = 0, speedY = 0;     //speed of object
    protected ID id;
    protected Sheet sh;
    private transient BufferedImage sprite = null;


    public GameObject(int x, int y, ID id, Sheet sh) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.sh = sh;
    }

    public abstract void tick();

    /**
     * Render object's color and it's size.
     * @param g
     */
    public void render(Graphics g){
        if(sprite == null){ //1. render nové hry nebo po načtení
            sprite = sh.grab(sheetX, sheetY, sheetSizeX, sheetSizeY);
        }
        g.drawImage(sprite, x, y, null);
    };

    /**
     * Returns object's size for calculating occurrence of collision.
     * @return
     */
    public abstract Rectangle getBounds();


    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public float getVelX() {
        return speedX;
    }

    public void setVelX(float velX) {
        this.speedX = speedX;
    }

    public float getVelY() {
        return speedY;
    }

    public void setVelY(float velY) {
        this.speedY = speedY;
    }
}
