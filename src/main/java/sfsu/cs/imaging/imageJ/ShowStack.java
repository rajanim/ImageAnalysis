package sfsu.cs.imaging.imageJ;

import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

/**
 * Created by rajanishivarajmaski1 on 5/12/17.
 */
public class ShowStack implements PlugInFilter{


        ImagePlus im = null;	// keep a reference to the associated ImagePlus object

        public int setup(String args, ImagePlus im) {
            this.im = im;
            ImagePlus image = IJ.openImage("/Users/rajanishivarajmaski1/University/Bio_Img_821/ImageJ/Head-Neck Cetuximab-Demo/0522c0017/1.3.6.1.4.1.14519.5.2.1.5099.8010.170257203554553282493714874166/1.3.6.1.4.1.14519.5.2.1.5099.8010.850352604313978653978834449892/");
            this.im = image;
            return  DOES_ALL;
        }

        public void run(ImageProcessor ignored) {
            ImageStack stack = im.getImageStack();
            int K = stack.getSize();
            for (int k = 1; k <= K; k++) {	// NOTE: slices are numbered from 1,...,K !!
                ImageProcessor ip = stack.getProcessor(k);
                ip.invert();
            }
        }
}
