import java.io.*;
import java.sql.*;
import java.util.*;

public class Helper_JDBC
{
    private static final String RANDCHARS = ""
        + "CuWMOQvP7gWR9a4oAbsbsvaW2l2xLhLAlqtrymNTkOuQjq26fW63I0eY6B0YchZbD"
        + "R6fyxqaKJ4oAbsbsvaW2l2x9mS2TZpstrySgBLPgVy6s9V71VhLAlqEl8j0laZYNn"
        + "9iK4FcHaupB8sdY9hEH7laWJULZm69tacnXlfSaVpimy6JrsMPj1yVIpp2EqyboCL";
    private static FileWriter FWRITER;
    private Statement statemnt = null;

    /**
     * Run a timed insertion test of 25000 random entries
     * @return duration it takes to do 25000 inserts
     */
    public long runInsertion (Connection db_conn) throws SQLException
    {
        long start_time = System.nanoTime();
        Random rng = new Random();
        statemnt = db_conn.createStatement();
        String sql = null;
        String str = null;

        /** Run 25,000 random insertions */
        for (int loops = 0; loops < 25000; loops++) {
            sql = "INSERT INTO TEST_WEIGLE"
                + "(Integer1, Integer2, Statstr, Varstr, Dble)"
                + "VALUES (";
            str = Integer.toString(loops);
            sql += "'" + str + "',";
            sql += "'" + str + "',";
            str = getRandstr(rng, 10);
            sql += "'" + str + "',";
            str = getRandstr(rng, 30);
            sql += "'" + str + "',";
            str = Double.toString(rng.nextDouble());
            sql += "'" + str + "');";
            statemnt.executeUpdate(sql);
        }

        if (statemnt != null) {
            statemnt.close();
        }

        long end_time = System.nanoTime();
        //divide by 1000000000 to get seconds.
        long duration = (end_time - start_time);
        return duration;
    }

    /**
     * Get a random string of characters from RANDCHARS String
     */
    public String getRandstr(Random rng, int length)
    {
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = RANDCHARS.charAt(rng.nextInt(RANDCHARS.length()));
        }
        return new String(text);
    }

    /**
     * Writes the time duration to a file
     */
    public void timeToFile (long duration)
    {
        /** convert time to seconds */
        duration /= 1000000000;
        String entry = Long.toString(duration);
        openFile();
        try {
            FWRITER.append(entry);
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeFile();
    }

    /**
     * Uses FileWriter to open a file with the append setting
     * toggled to true so that a new entry may be added to the end
     */
    private static void openFile ()
    {
        try {
            FWRITER = new FileWriter("times.dat", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the file opened using openFile
     */
    private static void closeFile ()
    {
        try {
            FWRITER.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a connection to the database
     */
    public Connection getConnection(
        String db_url, String user, String pass) throws SQLException
    {
        return DriverManager.getConnection(db_url, user, pass);
    }

    /**
     * Create TEST table in database with or without a primary key
     */
    public void createTable (
        Connection db_conn, boolean primary) throws SQLException
    {
        System.out.println("Creating TEST table...");
        String sql;
        statemnt = db_conn.createStatement();
        sql = "CREATE TABLE TEST_WEIGLE"
            + "(Integer1   INT         NOT NULL,"
            +  "Integer2   INT         NOT NULL,"
            +  "Statstr    CHAR(10)    NOT NULL,"
            +  "Varstr     VARCHAR(30) NOT NULL,"
            +  "Dble       DOUBLE      NOT NULL";
        if (primary) {
                sql += ", PRIMARY KEY(Integer1) );";
        } else {
            sql += ");";
        }
        statemnt.executeUpdate(sql);
        System.out.println("Table created");
        if (statemnt != null) {
            statemnt.close();
        }
    }

    /**
     * Drop TEST table
     * @throws SQLException
     */
    public void dropTable (Connection db_conn) throws SQLException
    {
        System.out.println("Dropping TEST table...");
        String sql;
        statemnt = db_conn.createStatement();
        sql = "DROP TABLE TEST_WEIGLE;";
        statemnt.executeUpdate(sql);
        System.out.println("Table deleted");
        if (statemnt != null) {
            statemnt.close();
        }
    }

    /**
     * Setup to work with a MySQL JDBC driver.
     * @return true if it successfully sets up the driver.
     */
    public boolean activateJDBC()
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
