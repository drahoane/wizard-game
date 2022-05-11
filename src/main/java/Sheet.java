import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class Sheet implements Serializable {

    transient private BufferedImage image;
    private String pathToImage = "src/main/resources/all.png";

    public Sheet(BufferedImage image) {
        this.image = null;
    }

    public BufferedImage grab(int col, int row, int width, int height) {
        if(image == null){
            try{
                image = ImageIO.read(new File(this.pathToImage));
            }catch(IOException e){ //couldn't read image
                System.out.println(e); 
                return null;
            }
        }
        return image.getSubimage((col*32)-32, (row*32)-32, width, height);
    }
}
