package sfsu.cs.imaging.imageJ;

/**
 * ComputingHistogram for MI implementation
 *
 */

import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

import java.util.Arrays;

public class ComputingHistogram implements PlugInFilter {

    public static void main(String[] args) {
        // set the plugins.dir property to make the plugin appear in the Plugins menu
        Class<?> clazz = sfsu.cs.imaging.imageJ.ComputingHistogram.class;
        String url = clazz.getResource("/" + clazz.getName().replace('.', '/') + ".class").toString();
        String pluginsDir = url.substring(5, url.length() - clazz.getName().length() - 6);
        System.setProperty("plugins.dir", pluginsDir);

        // start ImageJ
        new ImageJ();

        // open the Clown sample
        ImagePlus image = IJ.openImage("/Users/rajanishivarajmaski1/University/Bio_Img_821/einstein.jpg");
        System.out.println(image.getFileInfo());
        image.show();

        // run the plugin
        IJ.runPlugIn(clazz.getName(), "");
       // image.updateAndDraw();
    }

    public int setup(String arg, ImagePlus img) {
        return DOES_8G + NO_CHANGES;
    }

    public void run(ImageProcessor ip) {
        int[] h = new int[256]; // histogram array
        final int M = ip.getWidth();
        final int N = ip.getHeight();

        for (int v = 0; v < N; v++) {
            for (int u = 0; u < M; u++) {
                int i = ip.getPixel(u, v);
                h[i] = h[i] + 1;
            }
        }

       for (Integer i : h)
           System.out.println(i);
        // ... histogram h[] can now be used
        IJ.showMessage("computation done" + Arrays.toString(h));
    }
}

