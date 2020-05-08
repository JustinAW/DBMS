import java.sql.*;
import java.util.*;
import javax.swing.*;

public class Main_JDBC
{
    private static final String DB_URL = ""
        + "jdbc:mysql://db.cs.ship.edu:3306/csc371_26";
      //  + "?useTimezone=true"
//        + "?serverTimezon=EST";
    private static final String USERNAME = "csc371_26";
    private static final String PASSWORD = "Password26";
    public static Connection db_conn = null;

    public static void main (String args[]) throws SQLException
    {
        /** Check if JDBC driver will work */
        if (!activateJDBC()) {
            System.err.println("Could not activate JDBC driver in main");
            System.exit(-1);
        }
        /** Create a connection to the database */
        System.out.println("Connecting to database...");
        db_conn = getConnection(DB_URL, USERNAME, PASSWORD);
        if (db_conn != null) {
            System.out.println("Connected successfully");
        }

        Helper_JDBC helper = new Helper_JDBC(db_conn);

        Scanner s = new Scanner(System.in);
        int choice = 0;

        while (choice != 6) {
            System.out.println("What would you like to do?");
            System.out.println(
                      "1: Create tables in database\n"
                    + "2: Run inserts to populate database\n"
                    + "3: Open GUI view 1\n"
                    + "4: Open GUI view 2\n"
                    + "5: Open GUI view 3\n"
                    + "6: Exit");
            choice = s.nextInt();
            s.nextLine(); // clear the newline from the buffer

            if (choice == 1) {
                helper.createTables(db_conn);
            } else if (choice == 2) {
                helper.runInsertion(db_conn);
            } else if (choice == 3) {
            } else if (choice == 4) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            new View2_GUI(helper);
                        } catch (SQLException sqle) {
                            sqle.printStackTrace();
                        }
                    }
                });
            } else if (choice == 5) {
            }
        }

        /** Close scanner */
        s.close();

        /** Close connection */
        if (db_conn != null) {
            db_conn.close();
        }

        /** Get the meta data for the DB */
        //DatabaseMetaData meta = db_conn.getMetaData();
    }

    /**
     * Setup to work with a MySQL JDBC driver.
     * @return true if it successfully sets up the driver.
     */
    private static boolean activateJDBC()
    {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Create a connection to the database
     */
    private static Connection getConnection(
        String db_url, String user, String pass) throws SQLException
    {
        return DriverManager.getConnection(db_url, user, pass);
    }
}
