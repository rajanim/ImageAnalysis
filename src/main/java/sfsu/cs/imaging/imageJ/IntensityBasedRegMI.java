package sfsu.cs.imaging.imageJ;

import ij.IJ;
import ij.process.ImageProcessor;

import java.util.Scanner;

/**
 * Created by rajanishivarajmaski1 on 5/14/17.
 *
 * http://www.mathworks.com/matlabcentral/fileexchange/4145-automatic-image-registration-using--normalized--mutual-information-for-users-of-ip-toolbox?focused=5053827&tab=function
 */
public class IntensityBasedRegMI {


    public static void main(String[] args) {

        
        

    }

    public int[]  getHistogram(ImageProcessor ip){
        int[] h = new int[256]; // histogram array
        final int M = ip.getWidth();
        final int N = ip.getHeight();

        for (int v = 0; v < N; v++) {
            for (int u = 0; u < M; u++) {
                int i = ip.getPixel(u, v);
                h[i] = h[i] + 1;
            }
        }

        //for testing: logging
        for (Integer i : h)
            System.out.println(i);

        return h;
    }

}
