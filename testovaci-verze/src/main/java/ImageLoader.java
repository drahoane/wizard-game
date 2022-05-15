import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageLoader {
    private BufferedImage image;

    /**
     * Load an image of the level using its directory path.
     * In case of an exception, print the stack trace of the instance to System.err.
     * @param path
     * @return
     */
    public BufferedImage loadImage(String path) {
        try {
            image = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
