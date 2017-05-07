package sfsu.cs.imaging.imageJ;

/*******************************************************************************
 * This software is provided as a supplement to the authors' textbooks on digital
 *  image processing published by Springer-Verlag in various languages and editions.
 * Permission to use and distribute this software is granted under the BSD 2-Clause
 * "Simplified" License (see http://opensource.org/licenses/BSD-2-Clause).
 * Copyright (c) 2006-2016 Wilhelm Burger, Mark J. Burge. All rights reserved.
 * Visit http://imagingbook.com for additional details.
 *******************************************************************************/


        import ij.IJ;
        import ij.ImageJ;
        import ij.ImagePlus;
        import ij.WindowManager;
        import ij.gui.GenericDialog;
        import ij.plugin.filter.PlugInFilter;
        import ij.process.FloatProcessor;
        import ij.process.ImageProcessor;


/**
 * Template matching plugin based on the local correlation coefficient.
 * Slow because it uses getPixelValue() for pixel access.
 */
public class CorrelCoeff_Matching_Demo implements PlugInFilter {

    private ImagePlus refImg;

    public int setup(String arg, ImagePlus im) {
        return DOES_ALL + NO_CHANGES;
    }

    //--------------------------------------------------------------------

    public void run(ImageProcessor ip) {
        if (!showDialog()) return;

        FloatProcessor I = (FloatProcessor) ip.convertToFloatProcessor();
        CorrCoeffMatcher matcher = new CorrCoeffMatcher(I);

        ImageProcessor refIp = refImg.getProcessor();
        FloatProcessor R = (FloatProcessor) refIp.convertToFloatProcessor();

        float[][] C = matcher.getMatch(R);
        FloatProcessor Cp = new FloatProcessor(C);
        (new ImagePlus("Correlation Coefficient", Cp)).show();
    }

    //--------------------------------------------------------------------

    private boolean showDialog() {
       // int[] wList = WindowManager.getIDList();
        //if (wList==null) {
          //  IJ.error("No windows are open.");
            //return false;
       // }

        //String[] titles = new String[wList.length];
        //for (int i = 0; i < wList.length; i++) {
          //  ImagePlus imp = WindowManager.getImage(wList[i]);
            //if (imp!=null)
              //  titles[i] = imp.getTitle();
            //else
              //  titles[i] = "";
        //}
      //  GenericDialog gd = new GenericDialog("Select Reference Image");
      //  String title2;
       // refImg = IJ.openImage("/Users/rajanishivarajmaski1/University/Bio_Img_821/einstein.tif");
        refImg = IJ.openImage("/Users/rajanishivarajmaski1/University/Bio_Img_821/fixed_image/000000.tif");
        refImg.show();
       // if (refImg == null)
         //   title2 = titles[0];
        //else
       // System.out.println(refImg.getTitle());
          //  title2 = refImg.getTitle();
        //gd.addChoice("Template:", titles, title2);
        //gd.showDialog();
       // if (gd.wasCanceled())
         //   return false;
        //else {
            //int index2 = gd.getNextChoiceIndex();
            //title2 = titles[index2];
            //refImg = WindowManager.getImage(wList[index2]);
          //  return true;
        //}
        return true;
    }

    public static void main(String[] args) {
        // set the plugins.dir property to make the plugin appear in the Plugins menu
        Class<?> clazz = CorrelCoeff_Matching_Demo.class;
        String url = clazz.getResource("/" + clazz.getName().replace('.', '/') + ".class").toString();
        String pluginsDir = url.substring(5, url.length() - clazz.getName().length() - 6);
        System.setProperty("plugins.dir", pluginsDir);

        // start ImageJ
        new ImageJ();

        // open the Clown sample
     ImagePlus image = IJ.openImage("/Users/rajanishivarajmaski1/University/Bio_Img_821/fixed_image/000000.dcm");
     //  ImagePlus image = IJ.openImage("/Users/rajanishivarajmaski1/University/scala-practice/stock_vectorbanner.jpg");
    //    ImagePlus image = IJ.openImage("/Users/rajanishivarajmaski1/University/Bio_Img_821/einstein.jpg");

        //ImagePlus image = IJ.openImage("http://imagej.net/images/clown.jpg");
        image.show();

        // run the plugin
        IJ.runPlugIn(clazz.getName(), "");
    }
}
