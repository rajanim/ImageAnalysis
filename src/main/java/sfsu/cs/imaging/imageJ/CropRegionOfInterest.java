package sfsu.cs.imaging.imageJ;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

import java.awt.*;

/**
 * Created by rajanishivarajmaski1 on 5/12/17.
 */
public class CropRegionOfInterest implements PlugInFilter {

    boolean showMask = true;

    public int setup(String arg, ImagePlus imp) {
        return  DOES_ALL+ ROI_REQUIRED;
    }

    public void run(ImageProcessor ip) {
        Rectangle roi = ip.getRoi();
        if (roi == null) {
            IJ.error("selection required!"); // this should not happen ever
            return;
        }

        ImageProcessor ip2 = ip.crop();
        new ImagePlus("Extracted image", ip2).show();
    }
}
