import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

public abstract class GameObject implements Serializable {

    protected int x, y, sheetX, sheetY, sheetSizeX, sheetSizeY;
    protected float speedX = 0, speedY = 0;     //speed of object
    protected ID id;


    public GameObject(int x, int y, ID id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public abstract void tick();


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
