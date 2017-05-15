package sfsu.cs;

/**
 * Created by rajanishivarajmaski1 on 5/15/17.
 */
public class FreqCounter {

    public static void main(String[] args) {

        //input x that is data containing list of numbers. It could be  x[] = {97, 99, 98, 96, 98, 96, 2, 95, 96}
        int[] x = {4,4,4,2,1};
        int[] frequencyCounter = new int[100];
        printSeqCounts(x,frequencyCounter);


    }

    public static void printSeqCounts(int[] x, int[] frequencyCounter){

        for (int i=0; i<x.length; i++) {
            //update a count in frequencyCounter array for this number
            frequencyCounter[x[i]] ++;
        }

        for (int i=0; i <frequencyCounter.length; i++){
            if(frequencyCounter[i]>0){
                System.out.println(i + ": " + frequencyCounter[i]);
            }

        }



    }
}
