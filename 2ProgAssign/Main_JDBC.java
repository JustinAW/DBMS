import java.sql.*;
import java.util.*;
import javax.swing.*;

public class Main_JDBC
{
    private static final String DB_URL = ""
        + "jdbc:mysql://192.168.0.38:3306/dbms_pa2";
//        + "jdbc:mysql://db.cs.ship.edu:3306/csc371_26";
////        + "?useTimezone=true"
////        + "?serverTimezon=EST";
//    private static final String USERNAME = "csc371_26";
//    private static final String PASSWORD = "Password26";
    private static final String USERNAME = "jw6858";
    private static final String PASSWORD = "lePassword";
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

        while (choice != 4) {
            System.out.println("What would you like to do?");
            System.out.println(
                      "1: Create tables in database\n"
                    + "2: Run inserts to populate database\n"
                    + "3: Open GUI\n"
                    + "4: Exit");
            choice = s.nextInt();
            s.nextLine(); // clear the newline from the buffer

            if (choice == 1) {
                helper.createTables(db_conn);
            } else if (choice == 2) {
                helper.runInsertion(db_conn);
            } else if (choice == 3) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            new View2_GUI(helper);
                        } catch (SQLException sqle) {
                            sqle.printStackTrace();
                        }
                    }
                });
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
