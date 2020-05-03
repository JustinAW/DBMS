import java.io.*;
import java.sql.*;
import java.util.*;

public class Helper_JDBC
{
    private Statement statemnt = null;

    /**
     * Runs insert statements to populate the database
     */
    public void runInsertion (Connection db_conn) throws SQLException
    {
        System.out.println("Inserting into tables...");
        Statement statemnt = db_conn.createStatement();
        String sql = null;

        sql = "INSERT INTO PERSON"
            + " (login, email, password)"
            + "VALUES"
            + " ('dingding','dingding@mail.com', 'bestpw'),"
            + " ('beepboop','beepboop@mail.com', 'yermum'),"
            + " ('pickless','pickless@mail.com', 'pickle'),"
            + " ('sirbarty','sirbarty@mail.com', 'bartyy'),"
            + " ('sk8erboi','sk8erboi@mail.com', 'sk8erb');";
        execSQLUpdate(statemnt, sql);

        sql = "INSERT INTO PLAYER"
            + " (id, Per_login)"
            + "VALUES"
            + " (0, 'dingding'),"
            + " (1, 'beepboop'),"
            + " (2, 'pickless'),"
            + " (3, 'sirbarty'),"
            + " (4, 'sk8erboi');";
        execSQLUpdate(statemnt, sql);

        sql = "INSERT INTO LOCATION"
            + " (id, areaType)"
            + "VALUES"
            + " (0, 'forest'),"
            + " (1, 'mountain'),"
            + " (2, 'river'),"
            + " (3, 'valley'),"
            + " (4, 'swamp'),"
            + " (1234, 'hut');";
        execSQLUpdate(statemnt, sql);

        sql = "INSERT INTO CHARACTR"
            + " (name, strength, stamina, curHP, maxHP, Loc_id)"
            + "VALUES"
            + " ('dunkers', 55, 55, 100, 200, 1234),"
            + " ('tibbers', 75, 75, 100, 200, 0),"
            + " ('fisskyy', 40, 40, 100, 200, 1),"
            + " ('shoresy', 41, 41, 100, 200, 2),"
            + " ('jonesyy', 87, 87, 100, 200, 1234),"
            + " ('bonbons', 20, 20, 100, 250, 4),"
            + " ('pepegaa', 39, 39, 234, 235, 1234);";
        execSQLUpdate(statemnt, sql);

        sql = "INSERT INTO CONTROLS"
            + " (Play_id, Char_name)"
            + "VALUES"
            + " (0, 'dunkers')"
            + " (1, 'tibbers')"
            + " (2, 'fisskyy')"
            + " (2, 'shoresy')"
            + " (3, 'jonesyy')"
            + " (4, 'bonbons')"
            + " (4, 'pepegaa')"
        execSQLUpdate(statemnt, sql);

        sql = "INSERT INTO ITEM"
            + " (id, weight, volume, Loc_id)"
            + "VALUES"
            + " (0, 5, 5, 0),"
            + " (1, 15, 5, 0),"
            + " (2, 5, 15, 0),"
            + " (3, 10, 5, 0),"
            + " (4, 10, 10, 0);";
        execSQLUpdate(statemnt, sql);

        sql = "INSERT INTO CONTAINER"
            + " (ID, weightLim, volumeLim, item_id, weight, volume)"
            + "VALUES"
            + " (0, 10, 10, 0, 5, 5),"
            + " (1, 10, 10, 1, 5, 5),"
            + " (2, 15, 15, 2, 10, 10),"
            + " (4, 15, 15, 3, 10, 10),"
            + " (5, 20, 20, 4, 15, 15);";
        execSQLUpdate(statemnt, sql);

        sql = "INSERT INTO ABILITY"
            + " (id, type, exectime, targetstat, benepenal,"
            + "effectrate, effectduration)"
            + "VALUES"
            + " (0, 'fire', 5, 'strength', true, 1.5, 10),"
            + " (1, 'water', 5, 'strength', true, 1.5, 10);";
        execSQLUpdate(statemnt, sql);

        sql = "INSERT INTO WEAPON"
            + " (id, equipLoc, Item_id, Abil_id)"
            + "VALUES"
            + " (0, 2, 0, 0, 5, 5),"
            + " (1, 2, 1, 0, 10, 10), "
            + " (2, 3, 2, 0, 5, 5),"
            + " (3, 4, 3, 0, 10, 10);";
        execSQLUpdate(statemnt, sql);

        sql = "INSERT INTO ARMOR"
            + " (id, protection, wearloc, item_id, weight, volume)"
            + "VALUES"
            + " (0, 2, 0, 0, 5, 5),"
            + " (1, 2, 1, 2, 10, 10), "
            + " (2, 3, 2, 2, 5, 5),"
            + " (3, 4, 3, 1, 10, 10);";
        execSQLUpdate(statemnt, sql);

        if (statemnt != null) {
            statemnt.close();
        }
    }

    /**
     * Run a timed selection test of 100 different selects
     * @return duration it takes to do 100 selects
     */
    public long runSelect (Connection db_conn, boolean prim) throws SQLException
    {
        System.out.println("Starting timed select test...");
        long start_time = System.nanoTime();
        statemnt = db_conn.createStatement();

        int start, end;
        String sql = null;
        for (int loops = 0; loops < 100; loops++) {
            start = loops * 400;
            end = (loops+1) * 400;

            if (prim) {
                sql = "SELECT * FROM HW4_DATA WHERE number1>="
                    + Integer.toString(start)
                    + " AND number1<"
                    + Integer.toString(end)
                    + ";";
            } else {
                sql = "SELECT * FROM HW4_DATA WHERE number2>="
                    + Integer.toString(start)
                    + " AND number2<"
                    + Integer.toString(end)
                    + ";";
            }
            try {
                statemnt.executeQuery(sql);
            } catch (SQLException sqle) {
                sqle.printStackTrace();
                if (statemnt != null) {
                    statemnt.close();
                }
                return -1;
            }
        }

        long end_time = System.nanoTime();
        //divide by 1000000000 to get seconds.
        // or 1000000 to get milliseconds
        long duration = (end_time - start_time);
        System.out.println(
                "Done! Time: " + (duration/1000000) + " milliseconds\n");

        if (statemnt != null) {
            statemnt.close();
        }

        return duration;
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
     * Create all necessary tables in the database
     */
    public void createTables ( Connection db_conn) throws SQLException
    {
        System.out.println("Creating tables...");
        statemnt = db_conn.createStatement();
        String sql = null;

        sql = "CREATE TABLE PERSON"
            + " (login      VARCHAR(40) NOT NULL,"
            + "  email      VARCHAR(40) NOT NULL,"
            + "  password   VARCHAR(40) NOT NULL,"
            + " PRIMARY KEY(login),"
            + " UNIQUE(email) );";
        execSQLUpdate(statemnt, sql);

        sql = "CREATE TABLE PLAYER"
            + " (id         INT         NOT NULL,"
            + "  Per_login  VARCHAR(40) NOT NULL,"
            + " PRIMARY KEY (id),"
            + " FOREIGN KEY (Per_login) REFERENCES PERSON (login) );";
        execSQLUpdate(statemnt, sql);

        sql = "CREATE TABLE LOCATION"
            + " (id             INT             NOT NULL,"
            + "  areaType       VARCHAR(20)     NOT NULL,"
            + " PRIMARY KEY(id) );";
        execSQLUpdate(statemnt, sql);

        sql = "CREATE TABLE CHARACTR"
            + " (name      VARCHAR(40) NOT NULL,"
            + "  strength  INT         NOT NULL,"
            + "  stamina   INT         NOT NULL,"
            + "  curHP     INT         NOT NULL,"
            + "  maxHP     INT         NOT NULL,"
            + "  Loc_id    INT         NOT NULL,"
            + " PRIMARY KEY (name),"
            + " FOREIGN KEY (Loc_id) REFERENCES LOCATION (id) );";
        execSQLUpdate(statemnt, sql);

        sql = "CREATE TABLE CONTROLS"
            + " (Play_id        INT             NOT NULL,"
            + "  Char_name      VARCHAR(40)     NOT NULL,"
            + " FOREIGN KEY (Player_id) REFERENCES PLAYER (id),"
            + " FOREIGN KEY (Char_id) REFERENCES CHARACTR (id) );";
        execSQLUpdate(statemnt, sql);

        sql = "CREATE TABLE ABILITY"
            + " (id                 INT            NOT NULL,"
            + "  type               VARCHAR(10)    NOT NULL,"
            + "  execTime           INT            NOT NULL,"
            + "  targetStat         VARCHAR(10)    NOT NULL,"
            + "  benePenal          BOOL           NOT NULL,"
            + "  effectRate         DOUBLE(3,2)    NOT NULL,"
            + "  effectDuration     INT            NOT NULL,"
            + " PRIMARY KEY (id) );";
        execSQLUpdate(statemnt, sql);

        sql = "CREATE TABLE ITEM"
            + " (id        INT         NOT NULL,"
            + "  weight    INT         NOT NULL,"
            + "  volume    INT         NOT NULL,"
            + "  Loc_id    INT         NOT NULL,"
            + " PRIMARY KEY (id),"
            + " FOREIGN KEY (Loc_id) REFERENCES LOCATION (id) );";
        execSQLUpdate(statemnt, sql);

        sql = "CREATE TABLE CONTAINER"
            + " (id         INT        NOT NULL,"
            + "  weightLim  INT        NOT NULL,"
            + "  volumeLim  INT        NOT NULL,"
            + "  Item_id    INT        NOT NULL,"
            + " PRIMARY KEY(id),"
            + " FOREIGN KEY (Item_id) REFERENCES ITEM (id) );";
        execSQLUpdate(statemnt, sql);

        sql = "CREATE TABLE WEAPON"
            + " (id         INT        NOT NULL,"
            + "  equipLoc   INT        NOT NULL,"
            + "  Item_id    INT        NOT NULL,"
            + "  Abil_id    INT        NOT NULL,"
            + " PRIMARY KEY (id),"
            + " FOREIGN KEY (Item_id) REFERENCES ITEM (id),"
            + " FOREIGN KEY (Abil_id) REFERENCES ABILITY (id) );";
        execSQLUpdate(statemnt, sql);

        sql = "CREATE TABLE ARMOR"
            + " (id            INT        NOT NULL,"
            + "  protection    INT        NOT NULL,"
            + "  wearLoc       INT        NOT NULL,"
            + "  Item_id       INT        NOT NULL,"
            + " PRIMARY KEY (id),"
            + " FOREIGN KEY (Item_id) REFERENCES ITEM (id) );";
        execSQLUpdate(statemnt, sql);

        System.out.println("Tables created");

        if (statemnt != null) {
            statemnt.close();
        }
    }

    /**
     * Create a single table by name
     */
    public void createTable (Connection db_conn, String tblName)
    {
        System.out.println("Creating " + tblName + " table...");
    }

    /**
     * Drop TEST table
     * @throws SQLException
     */
    public void dropTable (Connection db_conn,
            String tableName) throws SQLException
    {
        System.out.println("Dropping table" + tableName + "...");
        String sql;
        statemnt = db_conn.createStatement();
        sql = "DROP TABLE " + tableName+ ";";
        try {
            statemnt.executeUpdate(sql);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return;
        }
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

    /**
     * Runs an sql update given by the string sql
     * @return true if sql executed successfully
     */
    private boolean execSQLUpdate (Statement statemnt, String sql)
    {
        try {
            statemnt.executeUpdate(sql);
        } catch (SQLException e) {
            sqle.printStackTrace();
            return false;
        }
        return true;
    }
}
