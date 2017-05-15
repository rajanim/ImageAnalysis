package sfsu.cs.db;

/**
 * Created by rajanishivarajmaski1 on 5/7/17.
 * **********************************
 * DAO (data access object)
 * **********************************
 * dbPath : path to chinookdb
 * **********************************
 * getConnection()
 * albumTitles()
 * albumTracks()
 * purchaseHistory()
 * purchaseTracks()
 * updateSpecificTrackPrice()
 * updateAllTrackPrices()
 * **********************************
 */

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import static sfsu.cs.db.Utils.getDateTime;

public class DAO {

    String dbPath;

    /**
     * constructor accepting db path
     * @param dbPath
     */
    public DAO(String dbPath) {
        this.dbPath = dbPath;
    }

    /**
     *  q1 to get albumTitles
     * @throws Exception
     */
    public void albumTitles() throws Exception {
        Connection c = getConnection();
        // Setting up database connection
        Statement stmt = c.createStatement();
        Scanner scanner = new Scanner(System.in);
        // Message prompting use to enter the artist name
        System.out.print("Enter Artist Name: \n");
        // Accept user input for artist name
        String inputName = scanner.nextLine();

        // Query the table for given artist name
        ResultSet rs = stmt.executeQuery(
                "SELECT Ar.ArtistId, AlbumID, Title FROM Album Al, Artist Ar where Al.ArtistId = Ar.ArtistId AND Ar.Name like (\"%"
                        + inputName + "%\");");

        // Track number of rows
        int counter = 0;

        while (rs.next()) {
            if (counter == 0)
                System.out.println("\nShowing results for artist = " + inputName + "\n");
            // Store the artist id from the result set
            int artistId = rs.getInt("ArtistId");
            // Store the album id from the result set
            int albumID = rs.getInt("AlbumID");
            // Store the album title from the result set
            String albumTitle = rs.getString("Title");

            System.out.format("%6d\t%6d\t%48s\n", artistId, albumID, albumTitle + "\n");

            counter++;
        }

        // Informing user no albums were found for particular Artist entered
        if (counter == 0)
            System.out.println("There are no albums in our records for Artist = " + inputName);
        // Close connections
        rs.close();
        stmt.close();
        c.close();
        scanner.close();

    }

    /**
     * it will create connection and return
     * @return connection object
     * @throws Exception if connection fails
     */
    public Connection getConnection() throws Exception {
        // Database connection
        Connection c;
        //String url = "jdbc:sqlite:/Users/rajanishivarajmaski1/University/DB_775/Database-systems-master/SQL/chinook.db";
        String url = "jdbc:sqlite:" + dbPath;

        c = DriverManager.getConnection(url);
        System.out.println("Connection to SQLite has been established.");
        c.setAutoCommit(false);

        return c;
    }

    /**
     * albumTracks  queries the db tables for given album title and displays the results. It makes a call to purchaseTrack method
     * @throws Exception
     */
    public void albumTracks() throws Exception {
        Connection c = getConnection();
        // Setting up database connection
        Statement stmt = c.createStatement();
        System.out.print("Enter Album Title: \n");
        Scanner scanner = new Scanner(System.in);

        // Accept user input for album title
        String inputName = scanner.nextLine();

        // Query the table for given album title and store the results in
        // the result set rs.
        ResultSet rs = stmt.executeQuery(
                "SELECT T.Name, T.TrackId, G.Name, T.UnitPrice FROM Track T, Album Al, Genre G where T.AlbumId = Al.AlbumId and T.GenreId = G.GenreId and Al.Title like  (\"%"
                        + inputName + "%\");");

        // Track number of rows
        int counter = 0;

        // hash with key value pair relation between track_id and unit price
        Map<Integer, Float> trackDetails = new HashMap<Integer, Float>();

        while (rs.next()) {
            // Message for user to show that results are being processed
            if (counter == 0)
                System.out.println("\nShowing results for artist = " + inputName + "\n");

            // Store the track name
            String trackName = rs.getString(1);
            // Store the track id
            int trackId = rs.getInt(2);
            // Store the genre name
            String rsString = rs.getString(3);
            // Store the unit price
            float unitPrice = rs.getFloat(4);

            // Store the track_id and unit_price into the hashmap, to
            // be sent to the purchaseTracks() method.
            trackDetails.put(trackId, unitPrice);

            // Print the output to the screen
            System.out.format("%6d\t%64s\t%12s\t%5f\n", trackId, trackName, rsString, unitPrice);

            counter++;
        }

        // Close connections
        rs.close();
        stmt.close();
        c.close();
        //  scanner.close();
        if (counter > 0)
            purchaseTracks(trackDetails);

            // Prompt user a msg with no tracks were found for particular album entered
        else if (counter == 0)
            System.out.println("There are no albums in our records for Album = " + inputName);

    }

    /**
     * Method to purchase tracks after choosing Album in question 2, Obtain Track(s) of
     an Album title.
     * @param trackDetails
     * @throws IOException
     */
    public void purchaseTracks(Map<Integer, Float> trackDetails) throws Exception {
        System.out.println("Would you like to purchase any of these tracks?");
        // Display all tracks of the album with its cost to the user
        System.out.println("Track Id: \tCost");

        // Create a set from the hashmap to be displayed
        Set set = trackDetails.entrySet();
        // Using an iterator to traverse through the set
        Iterator iterator = set.iterator();
        // Print the elements of the hashmap - track id and its associated
        // unit price
        while (iterator.hasNext()) {
            Map.Entry trackidPrice = (Map.Entry) iterator.next();
            System.out.print(trackidPrice.getKey() + ": ");
            System.out.println("\t\t" + trackidPrice.getValue());
        }

        // Variable for user input for quantity of tracks to be purchased
        Scanner scanInput = new Scanner(System.in);
        //  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the Track ID from the list above");
        //int trackToBePurchased = (new BufferedReader(new InputStreamReader(System.in))).read();
        int trackToBePurchased = 0;
        if (scanInput.hasNextInt())
            trackToBePurchased = scanInput.nextInt();
        else {
            System.out.println("couldn't  read track id");
            System.exit(0);
        }
        // Variable to calculate total cost of purchase = quantity * unit price
        float total = 0;
        // Variable to store quantity entered by user
        int trackQty = 0;

        // Check if entered track_id entered by user exists in the
        // hashmap. If true, prompt user to enter the required quantity of
        // the songs.
        if (trackDetails.containsKey(trackToBePurchased)) {
            // Print message confirming cost/song
            System.out.println("Track will cost $" + trackDetails.get(trackToBePurchased));
            // Accept quantity of tracks to be purchased
            System.out.println("Enter the quantity");
            trackQty = scanInput.nextInt();

            // Calculate total cost
            total = trackQty * trackDetails.get(trackToBePurchased);

            // Confirming quantity entered by user is positive
            if (trackQty > 0)
                System.out.println(
                        "Track(s) purchased for $" + trackQty * trackDetails.get(trackToBePurchased) + "!");
            else
                // Else prompt user to enter valid quantity
                System.out.println("Please enter valid quantity!");
        }

        // Message to inform user that entered track not present in retrieved
        // track list. Prompts the user to enter the trackId again, instead of
        // starting from scratch.
        else {
            System.out.println("Entered Track is not present in retrieved track list!");
            purchaseTracks(trackDetails);
        }

        // Once the user has chosen the track to be purchased along with a valid
        // quantity, we update the InvoiceLine and invoice tables to reflect purchases

        // Connection to database
        Connection c = getConnection();
        // Setting up database connection
        Statement stmt = c.createStatement();

        // Run a query to get the previous known address of the customer
        ResultSet rs = stmt.executeQuery(
                "SELECT Address, City, State, Country, PostalCode FROM Customer where CustomerId = 25;");

        String address = rs.getString(1);
        String city = rs.getString(2);
        String state = rs.getString(3);
        String country = rs.getString(4);
        String zip = rs.getString(5);

        // SQL statement for insertion in to the database table Invoice. Date entered is from the function getDateTime() from utils.
        String invoiceSql = "INSERT INTO Invoice(CustomerId,InvoiceDate,BillingAddress, BillingCity, BillingState, BillingCountry, BillingPostalCode, Total)"
                + "VALUES (25, '" + getDateTime() + "' ,'" + address + "','" + city + "','" + state + "','"
                + country + "'," + zip + "," + total + ");";

        // Execute the statement
        stmt.executeUpdate(invoiceSql);
        // Confirmation message displayed to User
        System.out.println("Invoice created successfully");

        // Getting the InvoiceID from the last inserted row into the Invoice
        // table
        ResultSet resultSet = c.prepareStatement("select last insert rowId();").executeQuery();

        // Store invoice id from the returned result set
        int lInvoiceid = resultSet.getInt(1);

        // SQL statement for insertion in to the database table InvoiceLine
        String invoicelineSql = "INSERT INTO Invoiceline (InvoiceId, TrackId, UnitPrice, Quantity)" + "VALUES ("
                + lInvoiceid + "," + trackToBePurchased + "," + trackDetails.get(trackToBePurchased)
                + "," + trackQty + ");";
        // Execute the statement
        stmt.executeUpdate(invoicelineSql);

        // Confirmation message displayed to User
        System.out.println("Invoice line entry created successfully");

        // Commit transactions, close statement and database.
        stmt.close();
        c.commit();
        c.close();

    }

    /**
     *shows purchase History of provided customer Id
     * @throws Exception
     */

    public void purchaseHistory() throws Exception {
        Connection c = getConnection();
        // Setting up database connection
        Statement stmt = c.createStatement();
        Scanner scanner = new Scanner(System.in);

        // Message prompting use to enter the customer id
        System.out.print("Enter Customer ID: \n");

        // Accept user input for artist name
        String customerId = scanner.nextLine();
        // Query the table for given artist name
        ResultSet rs = stmt.executeQuery(
                "SELECT T.Name, A.Title, IL.Quantity, I.Invoicedate FROM Track T, Album A, InvoiceLine IL, Invoice I where I.customerid = "
                        + customerId
                        + " AND IL.InvoiceId = I.InvoiceId AND T.AlbumId = a.AlbumId AND IL.TrackId = T.TrackId");
        // Track number of rows
        int counter = 0;

        while (rs.next()) {
            // Update user about processing records
            if (counter == 0)
                System.out.println("\nShowing results for Customer ID = " + customerId + "\n");
            // Store the track name from the result set
            String trackName = rs.getString(1);
            // Store the album title of the track from the result set
            String albumTitle = rs.getString(2);
            // Store the quantity of songs purchased
            int quantity = rs.getInt(3);
            // Store the date on which the invoice was prepared/song was
            // purchased
            String invoiceDate = rs.getString(4);

            // Print the result to the screen
            System.out.format("%64s\t%48s\t%2d\t%12s\n", trackName, albumTitle, quantity, invoiceDate);
            counter++;
        }

        //if no records found
        if (counter == 0)
            System.out.println("There are no entries in records for Customer ID = " + customerId);

        // Close connections
        rs.close();
        stmt.close();
        c.close();
        scanner.close();

    }

    /**
     * update Specific Track Price for provided input price on console
     *
     * @throws Exception
     */
    public void updateSpecificTrackPrice() throws Exception {
        // Database connection and Setting up database connection
        Connection c = getConnection();
        Statement stmt = c.createStatement();

        // Accept user input
        Scanner scanner = new Scanner(System.in);
        // Message prompting use to enter the track id
        System.out.print("Enter Track ID: \n");
        // Accept user input for track id
        String trackId = scanner.nextLine();

        // Query the table for given track id
        ResultSet rs = stmt
                .executeQuery("SELECT T.Name, T.UnitPrice FROM Track T WHERE T.TrackId = " + trackId + ";");
        // Track number of rows
        int counter = 0;
        while (rs.next()) {
            //  Message to user when processing results
            if (counter == 0)
                System.out.println("\nShowing results for Track ID = " + trackId + "\n");
            // Store the track name from the result set
            String trackName = rs.getString(1);
            // Store the unit price of the track from the result set
            Float unitPrice = rs.getFloat(2);

            // Print the result to the screen
            System.out.println("Track Name = " + trackName);
            System.out.println("Unit Price = " + unitPrice + "\n");

            counter++;
        }

        // Close the result set
        rs.close();

        // Informing user no albums were found for particular track entered
        if (counter == 0)
            System.out.println("There are no entries in the database for Track ID = " + trackId);

        if (counter > 0) {
            System.out.println("Enter the new price of this track");

            float newPrice = scanner.nextFloat();

            String updateSql = "UPDATE Track SET UnitPrice = " + newPrice + " WHERE TrackId = " + trackId + ";";

            // Execute the statement
            stmt.executeUpdate(updateSql);

            // Confirmation message displayed to User
            System.out.println(
                    "Unit price of Track updated successfully. \nConfirming entry with database with updated unit price - ");

            // Commit the changes to the database
            c.commit();

            // Query the table for given track id
            rs = stmt.executeQuery("SELECT T.Name, T.UnitPrice FROM Track T WHERE T.TrackId = " + trackId + ";");

            while (rs.next()) {
                //  message for user when first displaying
                // results
                if (counter == 0)
                    System.out.println("\nShowing results for Track ID = " + trackId + "\n");
                // Store the track name from the result set
                String trackName = rs.getString(1);
                // Store the unit price of the track from the result set
                Float aFloat = rs.getFloat(2);

                // Print the result to the screen
                System.out.println("Track Name = " + trackName);
                System.out.println("Unit Price = " + aFloat + "\n");

            }
        }

        rs.close();
        stmt.close();
        c.close();
        scanner.close();

    }

    /**
     * update All Track Prices for given percentage
     * @throws Exception
     */

    public void updateAllTrackPrices() throws Exception {
        // Database connection and Setting up database connection
        Connection c = getConnection();
        Statement stmt = c.createStatement();

        // Message prompting use to enter the percent change
        System.out.print("Enter the percentage by which track prices should be updated (-100 to +100) : \n");
        Scanner scanner = new Scanner(System.in);
        // Accept user input for percent change
        double percent = scanner.nextFloat();

        if (percent >= -100 && percent <= 100) {
            percent = (percent + 100) / 100;

            // Prompt for user confirmation before updating all values in a database
            System.out.println("Confirm update of ALL tracks? [Y/n]");
            String confirm = scanner.next();
            if (confirm.equals("Y")) {
                // Update SQL command
                String updateSql = "UPDATE Track SET UnitPrice = UnitPrice * " + percent + ";";

                // Execute the statement
                stmt.executeUpdate(updateSql);

                // Commit the changes to the database
                try {
                    c.commit();
                    System.out.println("Update successful.");
                } catch (Exception e) {
                    System.out.println("Update not committed to database");
                }

                //Track	ID,	Name,Previous Unit price, Updated Unit price.

                // Query the table for displaying the changes to the user
                ResultSet rs = stmt.executeQuery("SELECT T.TrackId, T.Name, T.UnitPrice FROM Track T ");

                int counter = 0;
                while (rs.next()) {

                    //  message for user when first displaying
                    // results
                    if (counter == 0)
                        System.out.println("\nShowing updated unit price for Track with percent change of  = " + percent + "\n");
                    int trackId = rs.getInt(1);
                    // Store the track name from the result set
                    String trackName = rs.getString(2);
                    // Store the unit price of the track from the result set
                    Float unitPrice = rs.getFloat(3);

                    //TODO : need to calculate previous unit price from current updated unit price for display

                    // Print the result to the screen
                    System.out.format("%6d\t%64s\t%12s\t%5f\n", trackId, trackName, unitPrice);
                    counter++;

                }

                rs.close();
                stmt.close();
                c.close();
                scanner.close();

            } else {
                System.out.println("Prices not updated.");
            }
        } else {
            System.out.println("Invalid percentage!");
        }

    }
}
