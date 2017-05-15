package sfsu.cs.imaging.imageJ.registraion;


import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ByteProcessor;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;
import imagingbook.pub.matching.ChamferMatcher;
import imagingbook.pub.matching.DistanceTransform.Norm;

/**
 *  ImageJ plugin demonstrates binary images distance transform
 */
public class Chamfer_Matching_Test implements PlugInFilter {

    private ImagePlus imgI = null;
    private ImagePlus imgR = null;
    public static void main(String[] args) {
        // set the plugins.dir property to make the plugin appear in the Plugins menu
        Class<?> clazz = Chamfer_Matching_Test.class;
        String url = clazz.getResource("/" + clazz.getName().replace('.', '/') + ".class").toString();
        String pluginsDir = url.substring(5, url.length() - clazz.getName().length() - 6);
        System.setProperty("plugins.dir", pluginsDir);

        // start ImageJ
        new ImageJ();

        // ImagePlus image = IJ.openImage("/Users/rajanishivarajmaski1/University/Bio_Img_821/fixed_image/000000.dcm");
        //    ImagePlus image = IJ.
        //        openImage("//Users/rajanishivarajmaski1/University/Bio_Img_821/ImageJ/Head-Neck Cetuximab-Demo/0522c0014/1.3.6.1.4.1.22213.2.26561/1.3.6.1.4.1.22213.2.26561.2/000003.dcm");
        ImagePlus image = IJ.openImage("/Users/rajanishivarajmaski1/University/Bio_Img_821/000018.jpg");

        // ImagePlus image = IJ.openImage("/Users/rajanishivarajmaski1/University/Bio_Img_821/fixed_image/numbers.jpeg");
        image.show();

        // run the plugin
        IJ.runPlugIn(clazz.getName(), "");
    }
    public int setup(String arg, ImagePlus imp) {
        this.imgI = imp;
        return DOES_ALL + NO_CHANGES;
    }
    public void run(ImageProcessor ipI) {
        imgR = IJ.openImage("/Users/rajanishivarajmaski1/University/Bio_Img_821/fixed_image/000000_bi.tif");
        imgR.show();

        ByteProcessor I = ipI.convertToByteProcessor();
        ByteProcessor R = imgR.getProcessor().convertToByteProcessor();

        ChamferMatcher matcher = new ChamferMatcher(I, Norm.L2);
        float[][] Qa = matcher.getMatch(R);

        FloatProcessor Q = new FloatProcessor(Qa);
        (new ImagePlus("Match" + imgI.getTitle(), Q)).show();
    }

    public static class CrossCorrelationCoeffMatcher {
        private final FloatProcessor I; // search image
        private final int MI, NI; 		// wi/h of image

        private FloatProcessor R; 		// reference image
        private int MR, NR; 			// w/h of reference
        private int K;
        private double meanR; 			// mean value of template
        private double varR;  			// square root of template variance

        public CrossCorrelationCoeffMatcher(FloatProcessor I) {
            this.I = I;
            this.MI = this.I.getWidth();
            this.NI = this.I.getHeight();
        }

        public float[][] getMatch(FloatProcessor R) {
            this.R = R;
            this.MR = R.getWidth();
            this.NR = R.getHeight();
            this.K = MR * NR;

            double sumR = 0;
            double sumR2 = 0;
            for (int j = 0; j < NR; j++) {
                for (int i = 0; i < MR; i++) {
                    float aR = R.getf(i,j);
                    sumR  += aR;
                    sumR2 += aR * aR;
                }
            }

            this.meanR = sumR / K;
            this.varR = Math.sqrt(sumR2 - K * meanR * meanR);

            float[][] C = new float[MI - MR + 1][NI - NR + 1];
            for (int r = 0; r <= MI - MR; r++) {
                for (int s = 0; s <= NI - NR; s++) {
                    float d = (float) getMatchingValue(r, s);
                    C[r][s] = d;
                }
            }
            this.R = null;
            return C;
        }

        private double getMatchingValue(int r, int s) {
            double sumI = 0, sumI2 = 0, sumIR = 0;
            for (int j = 0; j < NR; j++) {
                for (int i = 0; i < MR; i++) {
                    float aI = I.getf(r + i, s + j);
                    float aR = R.getf(i, j);
                    sumI  += aI;
                    sumI2 += aI * aI;
                    sumIR += aI * aR;
                }
            }
            double meanI = sumI / K;
            return (sumIR - K * meanI * meanR) /
                    (Math.sqrt(sumI2 - K * meanI * meanI) * varR);
        }

    }
}