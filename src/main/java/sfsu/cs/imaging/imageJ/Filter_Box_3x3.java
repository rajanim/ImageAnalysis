package sfsu.cs.imaging.imageJ;

import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

/**
 * Created by rajanishivarajmaski1 on 5/3/17.
 */
public class Filter_Box_3x3 implements PlugInFilter {

    public static void main(String[] args) {


        Class<?> clazz = sfsu.cs.imaging.imageJ.Filter_Box_3x3.class;
        String url = clazz.getResource("/" + clazz.getName().replace('.', '/') + ".class").toString();
        String pluginsDir = url.substring(5, url.length() - clazz.getName().length() - 6);
        System.setProperty("plugins.dir", pluginsDir);

        // start ImageJ
        new ImageJ();

        ImagePlus image = IJ.openImage("/Users/rajanishivarajmaski1/University/scala-practice/stock_vectorbanner.jpg");
        image.show();
        IJ.runPlugIn(clazz.getName(), "");
        image.updateAndDraw();
    }

    public int setup(String arg, ImagePlus imp) {

        return DOES_ALL;
    }

    public void run(ImageProcessor imageProcessor){

        int M = imageProcessor.getWidth();
        int N = imageProcessor.getHeight();
        ImageProcessor copy = imageProcessor.duplicate();
        for (int u = 1; u <= M - 2; u++) {
            for (int v = 1; v <= N - 2; v++) {

                int sum = 0;
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        int p = copy.getPixel(u + i, v + j);
                        sum = sum + p;

                    }
                }

                int q = (int) (sum / 9.0);
                imageProcessor.putPixel(u, v, q);
            }
        }

    }

}
