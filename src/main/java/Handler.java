import java.awt.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.logging.Logger;

public class Handler implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(Handler.class.getName());

    LinkedList<GameObject> objects = new LinkedList<>();
    LinkedList<GameObject> objectsToBeRemoved = null;

    private boolean up = false, down = false, right = false, left = false;

    public void tick() {
        objectsToBeRemoved =  new LinkedList<>();
        for (int i = 0; i < objects.size(); i++) {
            GameObject tempObject = objects.get(i);
            tempObject.tick();
        }

        for (int i = 0; i < objectsToBeRemoved.size(); i++) {
            GameObject tempObject = objectsToBeRemoved.get(i);
            LOGGER.info("vymazu" + tempObject);
            removeObject(tempObject);
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < objects.size(); i++) {
            GameObject tempObject = objects.get(i);
            tempObject.render(g);
        }
    }

    public void addObject(GameObject tempObject) {
        objects.add(tempObject);
    }

    public void removeObject(GameObject tempObject) {
        objects.remove(tempObject);
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }
}
