import java.sql.*;
import java.util.*;

public class Insertion
{
    public static final String RANDCHARS = ""
        + "CuWMOQvP7gWR9a4oAbsbsvaW2l2xLhLAlqtrymNTkOuQjq26fW63I0eY6B0YchZbD"
        + "R6fyxqaKJ4oAbsbsvaW2l2x9mS2TZpstrySgBLPgVy6s9V71VhLAlqEl8j0laZYNn"
        + "9iK4FcHaupB8sdY9hEH7laWJULZm69tacnXlfSaVpimy6JrsMPj1yVIpp2EqyboCL";

    public static final String DB_URL = ""
        + "jdbc:mysql://db.cs.ship.edu:3306/csc371_26";
    public static final String USERNAME = "csc371_26";
    public static final String PASSWORD = "Password26";
    protected static Connection db_conn = null;

    public static void main (String args[]) throws SQLException
    {
        /** Check if JDBC driver will work */
        if (!activateJDBC()) {
            System.err.println("Could not activate JDBC driver in main");
            System.exit(-1);
        }
        /** Create a connection to the database */
        System.out.println("Connecting to database...");
        db_conn = get_connection(DB_URL, USERNAME, PASSWORD);
        if (db_conn != null) {
            System.out.println("Connected successfully");
        }

        Scanner s = new Scanner(System.in);
        int choice = 0;

        while (choice != 3) {
            System.out.println("What would you like to do?");
            System.out.println(
                      "1: Run timed insertion test\n"
                    + "2: Drop TEST Table\n"
                    + "3: Exit");
            choice = s.nextInt();
            s.nextLine(); // clear the newline from the buffer

            if (choice == 1) {
                int runs = 0;
                int prim = 0;

                System.out.println(
                        "How many times do you want to run the test?");
                runs = s.nextInt();
                s.nextLine(); // clear newline from buffer

                System.out.println("Primary key test?\n1: yes\n0: no");
                prim = s.nextInt();
                s.nextLine(); // clear newline from buffer

                if (prim == 1) {
                    /** Create Table TEST_WEIGLE w/primary key*/
                    create_table(true);
                } else if (prim == 0) {
                    /** Create Table TEST_WEIGLE w/o primary key*/
                    create_table(false);
                } else {
                    break;
                }

                if (runs >= 1) {
                    for (; runs > 0; runs--) {
                        run_insertion();
                        if (runs > 1) {
                            drop_table();
                            if (prim == 1) {
                                create_table(true);
                            } else {
                                create_table(false);
                            }
                        }
                    }
                }
            } else if (choice == 2) {
                /** Drop Table TEST_WEIGLE */
                drop_table();
            }
        }

        /** Close scanner */
        s.close();

        /** Close connection */
        if (db_conn != null) {
            db_conn.close();
        }

        /** Get the meta data for the DB */
        //DatabaseMetaData meta = m_dbConn.getMetaData();
    }

    private static void run_insertion () throws SQLException
    {
        long startTime = System.nanoTime();

        /** Run 25,000 random insertions */
        for (int loops = 0; loops < 25000; loops++) {
            //TODO
            //get_randstr(Random r = new Random();, 10/30)
        }

        long endTime = System.nanoTime();
        //divide by 1000000 to get milliseconds.
        long duration = (endTime - startTime);
        // TODO append duration to file
    }

    /** Get a random string of characters from RANDCHARS String */
    private static String get_randstr(Random rng, int length)
    {
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = RANDCHARS.charAt(rng.nextInt(RANDCHARS.length()));
        }
        return new String(text);
    }

    /** Create a connection to the database */
    private static Connection get_connection(
            String db_url, String user, String pass) throws SQLException
    {
        return DriverManager.getConnection(db_url, user, pass);
    }

    /** Create TEST table in database with or without a primary key */
    private static void create_table (boolean primary) throws SQLException
    {
        System.out.println("Creating TEST table...");
        String sql;
        Statement statemnt = null;
        statemnt = db_conn.createStatement();
        if (primary) {
            sql = "CREATE TABLE TEST_WEIGLE"
                + "(Integer1   INT         NOT NULL,"
                +  "Integer2   INT         NOT NULL,"
                +  "Statstr    CHAR(10)    NOT NULL,"
                +  "Varstr     VARCHAR(30) NOT NULL,"
                +  "Dble       DOUBLE      NOT NULL,"
                + "PRIMARY KEY(Integer1) );";
        } else {
            sql = "CREATE TABLE TEST_WEIGLE"
                + "(Integer1   INT         NOT NULL,"
                +  "Integer2   INT         NOT NULL,"
                +  "Statstr    CHAR(10)    NOT NULL,"
                +  "Varstr     VARCHAR(30) NOT NULL,"
                +  "Dble       DOUBLE      NOT NULL);";
        }
        statemnt.executeUpdate(sql);
        System.out.println("Table created");
        if (statemnt != null) {
            statemnt.close();
        }
    }

    /** Drop TEST table
     * @throws SQLException */
    private static void drop_table () throws SQLException
    {
        System.out.println("Dropping TEST table...");
        String sql;
        Statement statemnt = null;
        statemnt = db_conn.createStatement();
        sql = "DROP TABLE TEST_WEIGLE;";
        statemnt.executeUpdate(sql);
        System.out.println("Table deleted");
        if (statemnt != null) {
            statemnt.close();
        }
    }

    /** Setup to work with a MySQL JDBC driver.
     * @return true if it successfully sets up the driver.
     */
    private static boolean activateJDBC()
    {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        }
        return true;
    }
}
