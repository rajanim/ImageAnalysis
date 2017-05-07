package sfsu.cs.imaging.imageJ;

import ij.IJ;
import ij.ImagePlus;

/**
 * Created by rajanishivarajmaski1 on 4/26/17.
 */
public class Utils {

    public static void showImage(String url){
        ImagePlus imagePlus = IJ.openImage(url);
        imagePlus.show();

    }
}
