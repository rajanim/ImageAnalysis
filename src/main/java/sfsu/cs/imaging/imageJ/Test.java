package sfsu.cs.imaging.imageJ;

/**
 * Created by rajanishivarajmaski1 on 5/9/17.
 */

import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;

/*******************************************************************************
 * This software is provided as a supplement to the authors' textbooks on digital
 *  image processing published by Springer-Verlag in various languages and editions.
 * Permission to use and distribute this software is granted under the BSD 2-Clause
 * "Simplified" License (see http://opensource.org/licenses/BSD-2-Clause).
 * Copyright (c) 2006-2016 Wilhelm Burger, Mark J. Burge. All rights reserved.
 * Visit http://imagingbook.com for additional details.
 *******************************************************************************/

/**
 * Template matching plugin based on the local correlation coefficient.
 * Slow because it uses getPixelValue() for pixel access.
 */
public class Test implements PlugInFilter {

    private ImagePlus refImg;

    public int setup(String arg, ImagePlus im) {
        return DOES_ALL + NO_CHANGES;
    }


    public void run(ImageProcessor ip) {
        refImg = IJ.openImage("/Users/rajanishivarajmaski1/University/Bio_Img_821/fixed_image/000000.tif");
        refImg.show();
        FloatProcessor I = (FloatProcessor) ip.convertToFloatProcessor();
        CorrCoeffMatcher matcher = new CorrCoeffMatcher(I);

        ImageProcessor refIp = refImg.getProcessor();
        FloatProcessor R = (FloatProcessor) refIp.convertToFloatProcessor();

        float[][] C = matcher.getMatch(R);
        FloatProcessor Cp = new FloatProcessor(C);
        (new ImagePlus("Correlation Coefficient", Cp)).show();
    }



    public static void main(String[] args) {
        Class<?> clazz = CorrelCoeff_Matching_Demo.class;
        String url = clazz.getResource("/" + clazz.getName().replace('.', '/') + ".class").toString();
        String pluginsDir = url.substring(5, url.length() - clazz.getName().length() - 6);
        System.setProperty("plugins.dir", pluginsDir);
        // start ImageJ
        new ImageJ();
        ImagePlus inputImage = IJ.openImage("/Users/rajanishivarajmaski1/Bio_Img_821/fixed_image/000000.dcm");
        inputImage.show();
        // run the plugin
        IJ.runPlugIn(clazz.getName(), "");
    }
}
