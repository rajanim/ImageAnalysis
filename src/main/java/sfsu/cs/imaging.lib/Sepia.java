package imaging.apis;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by rajanishivarajmaski1 on 4/20/17.
 *
 * Get RGB of each pixel of source image
 * calc tr, tg, tb. get integer value
 * set new RGB of pixel
 * if tr > 255 r = 255 r = tr
 * tg> 255 then g =255, g = tg
 * tb > 255 then b =255, b = tb
 *
 *
 */
public class Sepia {

    public static void main(String[] args) {

        BufferedImage image = null;
        File f = null;

        try{
            f = new File("/Users/rajanishivarajmaski1/University/scala-practice/stock_vectorbanner.jpg");
            image = ImageIO.read(f);
        }catch (IOException e) {
            System.err.println("io exception " + e);
        }

        int width = image.getWidth();
        int height = image.getHeight();

        for (int i = 0; i < width ; i++) {
            for (int j = 0; j < height; j++) {

                int p = image.getRGB(i,j);

                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;

                int tr = (int) (0.393*r + 0.769*g + 0.189*b);
                int tg = (int) (0.349*r + 0.686*g + 0.168*b);
                int tb = (int) (0.272*r + 0.534*g + 0.131*b);

                if(tr > 255){
                    r = 255;
                }else{
                    r = tr;
                }
                if(tg > 255){
                    g = 255;
                }else{
                    g = tg;
                }
                if(tb > 255){
                    b = 255;
                }else{
                    b = tb;
                }

                p = (a<<24) | (r<<16) | (g<<8) | b;
                image.setRGB(i,j,p);

            }
        }

        try {
            f = new File("/Users/rajanishivarajmaski1/University/scala-practice/stock_vectorbanner_sepia.jpg");
            ImageIO.write(image, "jpg", f);
        }catch (IOException e) {
            System.err.println("io" + e);
        }


    }


}
