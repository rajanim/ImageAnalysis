package sfsu.cs.imaging.imageJ;

import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;

/**
 * Created by rajanishivarajmaski1 on 5/12/17.
 */
public class RunImageJPlugins {

    public static void main(String[] args) {
        // set the plugins.dir property to make the plugin appear in the Plugins menu
        Class<?> clazz = Chamfer_Matching_Demo.class;
        String url = clazz.getResource("/" + clazz.getName().replace('.', '/') + ".class").toString();
        String pluginsDir = url.substring(5, url.length() - clazz.getName().length() - 6);
        System.setProperty("plugins.dir", pluginsDir);

        // start ImageJ
        new ImageJ();

      //  ImagePlus image = IJ.openImage("/Users/rajanishivarajmaski1/University/Bio_Img_821/fixed_image/000000.dcm");
   ImagePlus image = IJ.openImage("/Users/rajanishivarajmaski1/University/Bio_Img_821/ImageJ/Head-Neck Cetuximab-Demo/0522c0017/1.3.6.1.4.1.14519.5.2.1.5099.8010.170257203554553282493714874166/1.3.6.1.4.1.14519.5.2.1.5099.8010.850352604313978653978834449892/000000.dcm");

  //ImagePlus image = IJ.openImage("/Users/rajanishivarajmaski1/University/Bio_Img_821/fixed_image/einstein_selected.tif");
image.show();

        // run the plugin
        IJ.runPlugIn(clazz.getName(), "");
    }
}
