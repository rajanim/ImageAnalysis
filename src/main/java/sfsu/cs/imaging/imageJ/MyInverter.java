package sfsu.cs.imaging.imageJ;

import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class MyInverter implements PlugInFilter {

    public static void main(String[] args) {
        // set the plugins.dir property to make the plugin appear in the Plugins menu
        Class<?> clazz = sfsu.cs.imaging.imageJ.MyInverter.class;
        String url = clazz.getResource("/" + clazz.getName().replace('.', '/') + ".class").toString();
        String pluginsDir = url.substring(5, url.length() - clazz.getName().length() - 6);
        System.setProperty("plugins.dir", pluginsDir);

        // start ImageJ
        new ImageJ();

        // open the Clown sample
       // ImagePlus image = IJ.openImage("/Users/rajanishivarajmaski1/University/Bio_Img_821/fixed_image/000000.dcm");
        ImagePlus image = IJ.openImage("/Users/rajanishivarajmaski1/University/scala-practice/stock_vectorbanner.jpg");

        image.show();
        System.out.println(image.getFileInfo());


        // run the plugin
        IJ.runPlugIn(clazz.getName(), "");
        image.updateAndDraw();
    }

    public int setup(String args, ImagePlus im) {
        System.out.println("here");

        return DOES_ALL;
    }

    public void run(String args) {
        ImagePlus im = IJ.getImage();
        run(im.getProcessor());
        im.updateAndDraw();
    }

    public void run(ImageProcessor ip) {
        int M = ip.getWidth();
        int N = ip.getHeight();
        for (int i = 0; i < M; i++) {

            for (int j = 0; j < N; j++) {

                int p = ip.getPixel(i, j);
                ip.putPixel(i, j, 255 - p);

            }
        }
    }

}


