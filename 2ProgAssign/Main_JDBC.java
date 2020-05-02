import java.sql.*;
import java.util.*;

public class Main_JDBC
{
    private static final String DB_URL = ""
        + "jdbc:mysql://192.168.0.38:3306/dbms_pa2"
        + "?useTimezone=true"
        + "&serverTimezone=EST";
//        + "jdbc:mysql://db.cs.ship.edu:3306/csc371_26";
    private static final String USERNAME = "disssonance";
    private static final String PASSWORD = "ligma";
    public static Connection db_conn = null;

    public static void main (String args[]) throws SQLException
    {
        Helper_JDBC helper = new Helper_JDBC();
        /** Check if JDBC driver will work */
        if (!helper.activateJDBC()) {
            System.err.println("Could not activate JDBC driver in main");
            System.exit(-1);
        }
        /** Create a connection to the database */
        System.out.println("Connecting to database...");
        db_conn = helper.getConnection(DB_URL, USERNAME, PASSWORD);
        if (db_conn != null) {
            System.out.println("Connected successfully");
        }

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
                createTables(db_conn);
            } else if (choice == 2) {
                runInsertion(db_conn);
            } else if (choice == 3) {
            } else if (choice == 4) {
                Swing_GUI gui = new Swing_GUI();
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
}
