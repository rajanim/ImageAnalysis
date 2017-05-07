package sfsu.cs.imaging;

import ij.IJ;
import ij.ImagePlus;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by rajanishivarajmaski1 on 4/20/17.
 */
public class GrayScale {

    public static void main(String[] args) {
        BufferedImage image = null;
        File f = null;

        try {

            f = new File("/Users/rajanishivarajmaski1/University/scala-practice/stock_vectorbanner.jpg");
            image = ImageIO.read(f);
            ImagePlus imagePlus = IJ.openImage("/Users/rajanishivarajmaski1/University/scala-practice/stock_vectorbanner.jpg");
            imagePlus.show();

        } catch (IOException e) {

        }

        int width = image.getWidth();
        System.out.println(width);
        int height = image.getHeight();
        System.out.println(height);
        for (int i = 0; i < width; i++) {

            for (int j = 0; j < height; j++) {
                int p = image.getRGB(i, j);

                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;
                int avg = (r + g + b) / 3;

                p = (a << 24) | (avg << 16) | (avg << 8) | avg;

                image.setRGB(i, j, p);

            }
        }
/*
        for (int i = 0; i <width ; i++) {
            for (int j = 0; j < height; j++) {

                Color color = new Color(image.getRGB(i,j));

                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();
                int a = color.getAlpha();


                int avg  = (r+g+b)/3;
                Color grey = new Color(avg, avg, avg, a);
                image.setRGB(i,j, grey.getRGB());
            }
        }*/

        try {
            f = new File("/Users/rajanishivarajmaski1/University/scala-practice/avg_stock_vectorbanner.jpg");
            ImageIO.write(image, "jpg", f);
            ImagePlus imagePlus = IJ.openImage("/Users/rajanishivarajmaski1/University/scala-practice/avg_stock_vectorbanner.jpg");
            imagePlus.show();


        } catch (IOException e) {

        }
    }

}


