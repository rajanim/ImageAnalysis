package sfsu.cs.imaging.imageJ.registraion;

import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;

/**
 * Template matching plugin based on the correlation coefficient.
 * Using getPixelValue() for pixel access.
 */
public class CorrelCoeff_Matching implements PlugInFilter {

    private ImagePlus refImg;

    /**
     * Main method uses Java reflection to run this class after having deployed this as plugin in ImageJ
     * @param args
     */
    public static void main(String[] args) {
        // set the plugins.dir property to make the plugin appear in the Plugins menu
        Class<?> clazz = CorrelCoeff_Matching.class;
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

    public int setup(String arg, ImagePlus im) {
        return DOES_ALL + NO_CHANGES;
    }

    /**
     * run method initiated by imageJ plugin filter
     * @param ip
     */
    public void run(ImageProcessor ip) {
        refImg = IJ.openImage("/Users/rajanishivarajmaski1/University/Bio_Img_821/fixed_image/000000.tif");
        refImg.show();
        FloatProcessor I = (FloatProcessor) ip.convertToFloatProcessor();
        Chamfer_Matching_Test.CrossCorrelationCoeffMatcher matcher = new Chamfer_Matching_Test.CrossCorrelationCoeffMatcher(I);

        ImageProcessor refIp = refImg.getProcessor();
        FloatProcessor R = (FloatProcessor) refIp.convertToFloatProcessor();

        float[][] C = matcher.getMatch(R);
        FloatProcessor Cp = new FloatProcessor(C);
        (new ImagePlus("Correlation Coefficient", Cp)).show();
    }


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
}
