package imaging.apis;

import mark.lewis.GUI.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by rajanishivarajmaski1 on 4/20/17.
 */
public class RandomPixelImage {

    public static void main(String[] args) {

        int width = 100;
        int height = 100;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

        File file;

        for(int x = 0; x < height; x++){

            for (int y = 0; y < width ; y++) {

                int  a = (int) (Math.random()*256);
                int  r = (int) (Math.random()*256);

                int  g = (int) (Math.random()*256);
                int  b = (int) (Math.random()*256);

                int p = (a<<24) | (r<<16) | (g<<8) | b;

                image.setRGB(x,y,p);

            }
        }

        try {
            file = new File("/Users/rajanishivarajmaski1/University/scala-practice/test.png");
            javax.imageio.ImageIO.write(image, "png", file);
        }catch (IOException e){
            System.err.println("io exception");
        }


    }
}
