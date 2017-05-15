package sfsu.cs.imaging.imageJ;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

/**
 * Created by rajanishivarajmaski1 on 5/12/17.
 */
public class SmoothRotateDemo implements PlugInFilter{

        ImagePlus im = null;

        public int setup(String arg, ImagePlus imp) {
            this.im = imp;		// keep reference to associated ImagePlus
            return DOES_ALL; 	// this plugin accepts any image
        }

        public void run(ImageProcessor ip) {
            for (int i = 0; i < 10; i++) {
                // modify this image:
                ip.smooth();
                ip.rotate(30);
                // redisplay this image:
                im.updateAndDraw();
                // sleep so user can watch this:
                IJ.wait(100);
            }
        }
    }

