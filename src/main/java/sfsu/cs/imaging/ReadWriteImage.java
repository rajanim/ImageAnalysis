package sfsu.cs.imaging;


import ij.IJ;
import ij.ImagePlus;

import java.io.File;
import  java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
/**
 * Created by rajanishivarajmaski1 on 4/20/17.
 */
public class ReadWriteImage {

    public static void main(String[] args) {

        int width = 963;
        int height = 640;

        BufferedImage image = null;
        File file;

        try {
            file = new File("/Users/rajanishivarajmaski1/University/scala-practice/castle2.png");
            image = ImageIO.read(file);
            System.out.println("Read done");

            file = new File("/Users/rajanishivarajmaski1/University/scala-practice/castle2_copy.png");
            ImageIO.write(image, "png", file);
            ImagePlus imagePlus = IJ.openImage("/Users/rajanishivarajmaski1/University/scala-practice/castle2_copy.png");
            imagePlus.show();

            System.out.println("write done");

        }catch (IOException e){
            System.err.println("io exception");

        }




    }

}
