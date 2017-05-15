package sfsu.cs;

/**
 * Created by rajanishivarajmaski1 on 5/10/17.
 */
public class StringPractice {

    public static void main(String[] args) {

        String value = "SFSU.CSC210";

        //get the length of string
        System.out.println(value.length());

        //get the first character
        System.out.println(value.charAt(0));

        //get index of F
        System.out.println(value.indexOf("F"));
        System.out.println(value.substring(value.indexOf(".")+1));

    }
}
