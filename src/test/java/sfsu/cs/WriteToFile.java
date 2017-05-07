package sfsu.cs;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;



public class WriteToFile {

    public static void main(String[] args)  {

        Scanner scanner = new Scanner(System.in);
        String enteredText = scanner.nextLine();
        try {
            PrintWriter  printWriter = new PrintWriter("my_file_from_java.txt");

            printWriter.println(enteredText);

            printWriter.close();

        }catch (FileNotFoundException e){
            System.err.println("file my_file_from_java.txt not found");
        }



    }
}
