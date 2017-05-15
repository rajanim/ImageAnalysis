package sfsu.cs.db;

import java.util.Scanner;

/**
 * Created by rajanishivarajmaski1 on 5/7/17.
 *
 * Main method calling data access methods of DAO.java to do specific db operations based on selected options.
 *
 */
public class MainDbOps {

    public static void main(String args[]) throws Exception {
        Scanner scan = new Scanner(System.in);
        // capture path of database chinook.db
        System.out.println("Please enter the path of the database chinook.db");
        String db_path = scan.nextLine();
        DAO dao = new DAO(db_path);
        int choice;
        Scanner scanner = null;
        // Set up exit logic to prompt user input until exit
        int exit = 1;
        do {
            System.out.println("Enter your choice (1-6): \n");
            System.out.println("1. Obtain Album titles based on Artist name");
            System.out.println("2. Obtain Track(s) of an Album title");
            System.out.println("3. Purchase History for a Customer");
            System.out.println("4. Update Track Price – Individual");
            System.out.println("5. Update Track Price – Batch");
            System.out.println("6. Exit");
            scanner = new Scanner(System.in);
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    // Q1
                    dao.albumTitles();
                    break;
                case 2:
                    // Q2
                    dao.albumTracks();
                    break;
                case 3:
                    // Q3
                    dao.purchaseHistory();
                    break;
                case 4:
                    // Q4
                    dao.updateSpecificTrackPrice();
                    break;
                case 5:
                    // Q5
                    dao.updateAllTrackPrices();
                    break;
                case 6 :
                    exit = 0;
                    break;
                default:
                    System.out.println("Invalid input, choose 1-6 only");
            }

        } while (exit == 1);
    }
}
