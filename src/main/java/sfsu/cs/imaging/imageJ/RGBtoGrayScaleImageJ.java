package sfsu.cs.imaging.imageJ;

import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ByteProcessor;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;

/**
 * Created by rajanishivarajmaski1 on 5/3/17.
 */
public class RGBtoGrayScaleImageJ implements PlugInFilter {

    public static void main(String[] args) {

        // set the plugins.dir property to make the plugin appear in the Plugins menu
        Class<?> clazz = sfsu.cs.imaging.imageJ.RGBtoGrayScaleImageJ.class;
        String url = clazz.getResource("/" + clazz.getName().replace('.', '/') + ".class").toString();
        String pluginsDir = url.substring(5, url.length() - clazz.getName().length() - 6);
        System.setProperty("plugins.dir", pluginsDir);

        // start ImageJ
        new ImageJ();

        ImagePlus image = IJ.openImage("/Users/rajanishivarajmaski1/University/scala-practice/stock_vectorbanner.jpg");
        image.show();
        IJ.runPlugIn(clazz.getName(), "");

    }
    public  int setup(String arg, ImagePlus imp){

        return DOES_ALL;
    }

    public void run(ImageProcessor ip){
        ColorProcessor colorProcessor = (ColorProcessor) ip;
        colorProcessor.setRGBWeights(0.2126, 0.7152, 0.0722);
        ByteProcessor   byteProcessor = colorProcessor.convertToByteProcessor();
        (new ImagePlus( " (gray)", byteProcessor)).show();

    }



}
