package sfsu.cs.db;

import java.util.Scanner;

/**
 * Main method calling data access methods of ECDAO.java to do specific db operations based on selected options.
 * <p>
 * Created by rajanishivarajmaski1 on 5/9/17.
 */
public class ECMainDbOps {

    public static void main(String args[]) throws Exception {

        Scanner scan = new Scanner(System.in);
        // Enter path of database chinook.db
        System.out.println("Please enter the path of the database chinook.db");
        String db_path = scan.nextLine();
        ECDAO ecDao = new ECDAO(db_path);
        // Set up exit logic to prompt user input until exit
        int exit = 1;
        do {
            System.out.println("Enter choice (1-5): \n");
            System.out.println("1. Identify marketable population and material");
            System.out.println("2. Track Recommender");
            System.out.println("3. Top sellers by revenue");
            System.out.println("4. Top sellers by Qty");
            System.out.println("5. Exit");
            int choice = scan.nextInt();
            switch (choice) {
                case 1:
                    //Q1
                    ecDao.marketablePopulation();
                    break;
                case 2:
                    //Q2
                    ecDao.trackRecommendations();
                    break;
                case 3:
                    //Q3
                    ecDao.topRevenueGenArtists();
                    break;
                case 4:
                    //Q4
                    ecDao.topQty();
                    break;
                case 5:
                    exit = 0;
                    break;
                default:
                    System.out.println("Invalid input, enter again");
            }
        } while (exit == 1);
        scan.close();

    }
}
