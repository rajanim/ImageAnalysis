package sfsu.cs.imaging.imageJ;

import ij.plugin.filter.PlugInFilter;
import ij.*;
import ij.process.*;
/**
 * Created by rajanishivarajmaski1 on 5/3/17.
 */


public class FilterSmooth implements PlugInFilter {
    public int setup(String arg, ImagePlus imp) {
        return DOES_ALL;
    }

    public static void main(String[] args) {
        Class<?> clazz = sfsu.cs.imaging.imageJ.FilterSmooth.class;
        String url = clazz.getResource("/" + clazz.getName().replace('.', '/') + ".class").toString();
        String pluginsDir = url.substring(5, url.length() - clazz.getName().length() - 6);
        System.setProperty("plugins.dir", pluginsDir);

        // start ImageJ
        new ImageJ();
         ImagePlus image = IJ.openImage("/Users/rajanishivarajmaski1/University/Bio_Img_821/fixed_image/000000.dcm");

//        ImagePlus image = IJ.openImage("/Users/rajanishivarajmaski1/University/scala-practice/stock_vectorbanner.jpg");
       image.show();
        IJ.runPlugIn(clazz.getName(), "");
    }

    public void run(ImageProcessor ip) {
        int M = ip.getWidth();
        int N = ip.getHeight();

        //3x3 filter matrix:
        double[][] H = {
                {1, 1, 1},
                {1, 1, 1},
                {1, 1, 1}};

        ImageProcessor copy = ip.duplicate();

        for (int u = 1; u <= M - 2; u++) {
            for (int v = 1; v <= N - 2; v++) {
                // compute filter result for position (u,v):
                double sum = 0;
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        int p = copy.getPixel(u + i, v + j);
                        // get the corresponding filter coefficient:
                        double c = H[j + 1][i + 1];
                        sum = sum + c * p;
                    }
                }
                int q = (int) Math.round(sum);
                ip.putPixel(u, v, q);
            }
        }

        (new ImagePlus( " (filter)", copy)).show();
    }
}
