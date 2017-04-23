package imaging.apis;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by rajanishivarajmaski1 on 4/20/17.
 *
 * Read source in buffer image
 * get dimension of source image
 * create buffer image mimicing
 *
 */
public class MirrorImage {
    public static void main(String[] args) {

        BufferedImage simg = null;

        File f = null;

        try{
            f = new File("/Users/rajanishivarajmaski1/University/scala-practice/stock_vectorbanner.jpg");

            simg = ImageIO.read(f);

        }catch (IOException e ){
            System.out.println("Error" + e);
        }

        int width = simg.getWidth();
        int height = simg.getHeight();

        BufferedImage mimg = new BufferedImage(width*2, height,
                BufferedImage.TYPE_INT_ARGB );

        for (int i = 0; i < height ; i++) {
            for (int j = 0; j < width*2 -1 ; j++) {

            }
        }


    }
}
