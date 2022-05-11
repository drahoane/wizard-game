public class Camera {

    private float x, y;

    public Camera(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Update object's directions and change the variables types to float.
     * Set the boundaries of the directions x and y so that the camera's focus won't leave the level's rendered image.
     * @param object
     */
    public void tick(GameObject object) {
        x += ((object.getX() - x) - 500) * 0.05f;
        y += ((object.getY() - y) - 563/2) * 0.05f;

        if(x <= 0) x = 0;           //camera will not go outside the lvl
        if(x >= 1032) x = 1032;
        if(y <= 0) y = 0;
        if(y >= 611) y = 611;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
