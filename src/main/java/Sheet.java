import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sheet implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(Game.class.getName());

    transient private BufferedImage image;
    private String pathToImage = "src/main/resources/all.png";


    public Sheet(BufferedImage image) {
        this.image = null;
    }

    /**
     * Get merely game object's corresponding image from the whole layout 'all.png' using its position and size within the layout.
     *
     * @param col
     * @param row
     * @param width
     * @param height
     * @return
     */
    public BufferedImage grab(int col, int row, int width, int height) {
        if(image == null){
            try{
                image = ImageIO.read(new File(this.pathToImage));
                LOGGER.log(Level.INFO,"Image has been successfully read");
            }catch(IOException e){
                LOGGER.log(Level.WARNING,"Could not read image");
                System.out.println(e);
                return null;
            }
        }
        return image.getSubimage((col*32)-32, (row*32)-32, width, height);
    }
}
