package sfsu.cs.db;

/**
 * Created by rajanishivarajmaski1 on 5/9/17.
 * <p>
 * <p>
 * /**
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ECDAO {

    String dbPath;

    /**
     * constructor accepting db path
     *
     * @param dbPath
     */
    public ECDAO(String dbPath) {
        this.dbPath = dbPath;
    }

    /**
     * it will create connection and return
     *
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
     * marketable population Q1
     *
     * @throws Exception
     */
    public void marketablePopulation() throws Exception {

        Connection c = getConnection();
        // Setting up database connection
        Statement stmt = c.createStatement();

        System.out.print("Enter State to be searched, [ex: CA, NY etc] : \n");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String state = br.readLine();

        // Query the table for given state
        ResultSet rs = stmt.executeQuery(
                "WITH allalbumssold AS (select al.albumid, al.title from album al order by al.albumid), statealbumsold as (select al.albumid, al.title from track t, invoice i, invoiceline il, album al where il.trackid = t.trackid and il.invoiceid = i.invoiceid and al.albumid = t.albumid and i.billingstate = '"
                        + state
                        + "' group by al.albumid order by al.albumid) select * from allalbumssold except select * from statealbumsold;");

        // Track number of rows
        int count = 0;

        while (rs.next()) {
            // status msg
            if (count == 0)
                System.out.println(
                        "\nShowing results for Marketable population and material in the state of = " + state);

            // Store the album id
            String albumId = rs.getString(1);
            // Store the album name
            String albumName = rs.getString(2);

            // Print the output to the console
            System.out.println();
            System.out.println("Album ID: " + albumId);
            System.out.println("Album Name : " + albumName);
            // Increment count of results
            count++;
        }

        System.out.println("\nNumber of albums returned = " + count);

        // Query the table for given state using java sql exeucte query.
        rs = stmt.executeQuery(
                "SELECT c.customerid, c.firstname, c.lastname, c.phone, c.email from customer c where state = '"
                        + state + "';");
        count = 0;

        while (rs.next()) {
            // status msg
            if (count == 0)
                System.out.println("\nShowing results for Customers in the state of = \n" + state);

            //  the customer id
            Integer customerId = rs.getInt(1);
            //  customer's first name
            String firstName = rs.getString(2);
            // Store the customer's last name
            String lastName = rs.getString(3);
            //  the customer's phone number
            String phone = rs.getString(4);
            //  the customer's email
            String email = rs.getString(5);

            System.out.println("Customer ID: " + customerId);
            System.out.println("Customer Name : " + firstName + " " + lastName);
            System.out.println("Customer Phone: " + phone);
            System.out.println("Customer Email: " + email);

            count++;
        }
        System.out.println("\nNumber of customers in the state of " + state + " are - " + count);
        rs.close();
        stmt.close();
        c.close();

    }
    /**
     * Track Recommendations
     *
     * @throws Exception
     */
    public void trackRecommendations() throws Exception {
        Connection c = getConnection();
        // Setting up database connection
        Statement stmt = c.createStatement();

        System.out.print("Enter customer ID to be searched: \n");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int customerId = Integer.parseInt(br.readLine());

        ResultSet rs = stmt.executeQuery(
                "select t.albumid, group_concat(t.trackid) as 'Tracks' from track t, invoiceline il, invoice i where il.invoiceid = i.invoiceid and i.customerid = "
                        + customerId
                        + " and t.trackid = il.trackid group by t.albumid having count(t.albumid)>=3");
        int count = 0;

        StringBuffer sbAlbum = new StringBuffer();
        StringBuffer sbTracks = new StringBuffer();

        while (rs.next()) {
            // status msg
            if (count != 0) {
                sbAlbum.append(",");
                sbTracks.append(",");
            }

            String albumId = rs.getString(1);
            // Store the album name
            String tracks = rs.getString(2);
            // Generate list of album ids
            sbAlbum.append(albumId);
            // Generate list of tracks purchased by the user
            sbTracks.append(tracks);
            // Increment count of results
            count++;
        }

        rs = stmt.executeQuery("select t.trackid, t.name from track t where t.albumid in (" + sbAlbum.toString()
                + ") and t.trackid not in (" + sbTracks.toString() + ");");
        count = 0;
        while (rs.next()) {
            // status msg
            if (count == 0) {
                System.out.println("Recommended tracks for user are");
                System.out.println("Track ID \tTrack Name ");
            }
            int albumId = rs.getInt(1);
            //  the album name
            String trackNames = rs.getString(2);

            System.out.println(albumId + "\t\t" + trackNames);
            // Increment count of results
            count++;
        }
        // Close connections
        rs.close();
        stmt.close();
        c.close();

    }

    /**
     * Top Revenue
     *
     * @throws Exception
     */
    public void topRevenueGenArtists() throws Exception {

        Connection c = getConnection();
        // Setting up database connection
        Statement stmt = c.createStatement();

        // Results of artists names with top revenue generated
        ResultSet rs = stmt.executeQuery(
                "SELECT ar.name, SUM(il.quantity), SUM(il.unitprice*il.quantity) as Revenue FROM album al, artist ar, track t, invoiceline il WHERE il.trackid = t.trackid AND ar.artistid = al.artistid AND t.albumid = al.albumid GROUP by ar.artistid order by revenue DESC;");

        // Track number of rows
        int count = 0;

        while (rs.next()) {
            // status msg
            if (count == 0) {
                System.out.println("\nShowing results for highest revenue generating Artists = \n");
                System.out.println("\t\t\t\t\t\t\t\t\t\tArtist Name\t Quantity Sold\tRevenue generated");
            }

            //  the artist name
            String artistName = rs.getString(1);
            //  the genre name
            int quantity = rs.getInt(2);
            //  the unit price
            float revenue = rs.getFloat(3);

            // Print the output to the close
            System.out.format("%96s\t%3d\t%8.2f\n", artistName, quantity, revenue);

            // Increment count of results
            count++;
        }

        // Close connections
        rs.close();
        stmt.close();
        c.close();

    }
    /**
     * Top Volume
     *
     * @throws Exception
     */
    public void topQty() throws Exception {

        // Setting up database connection
        Connection c = getConnection();
        Statement stmt = c.createStatement();

        // Results of artists names with top volume
        ResultSet rs = stmt.executeQuery(
                "SELECT ar.name, SUM(il.quantity) as Quantity FROM album al, artist ar, track t, invoiceline il WHERE il.trackid = t.trackid AND ar.artistid = al.artistid AND t.albumid = al.albumid GROUP by ar.artistid order by Quantity DESC;");

        // Track number of rows
        int count = 0;
        while (rs.next()) {
            // message for user when first displaying results
            if (count == 0) {
                System.out.println("\nShowing results for high selling Artists = \n");
                System.out.println("\t\t\t\t\t\t\t\tArtist Name\t\t Qty Sold");
            }

            //  the artist name
            String artistName = rs.getString(1);
            // the quantity of tracks sold
            int quantity = rs.getInt(2);

            // Print the output to the console
            System.out.format(artistName, "  ", quantity);

            count++;
        }

        rs.close();
        stmt.close();
        c.close();
    }
}
