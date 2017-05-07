package sfsu.cs;

import java.util.Scanner;

/**
 * Created by rajanishivarajmaski1 on 5/3/17.
 */
public class ArithmeticExceptionUseCase {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter two numbers ");
        int dividend = scanner.nextInt();
        int divisor = scanner.nextInt();

       // try{
          int quotient = dividend/divisor;
            System.out.println(quotient);

       // }catch (Exception e ){

         //   System.out.println(e);
       // }

        int sum = dividend+divisor;
        System.out.println("sum of two numbers exeucted after try-catch block: " +  sum);

    }
}
