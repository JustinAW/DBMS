import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;
import java.util.*;

public class DB_Connection
{
    private static final String DB_URL = ""
            +"jdbc:mysql://127.0.0.1:3306/mysql";
            //+ "?useTimezone=true"
            //+ "&serverTimezone=EST";
//            + "jdbc:mysql://db.cs.ship.edu:3306/csc371_26";
        private static final String USERNAME = "jb7656";
        private static final String PASSWORD = "Shipswim22";
        public static Connection db_conn = null;

        public static void main (String args[]) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
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

            try
            {
                insertIntoTables();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

           /* //Tables are already created so this is no longer necessary at runtime
            try
            {
                createTables();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            */
        }
        public static void insertIntoTables() throws Exception
        {
            Statement statemnt = db_conn.createStatement();
            String sql = ""
                    + "INSERT INTO PERSON"
                    + " (login, email, password)"
                    + "VALUES"
                    + " ('dingding','dingding@mail.com', 'bestpw'),"
                    + " ('beepboop','beepboop@mail.com', 'yermum'),"
                    + " ('pickless','pickless@mail.com', 'pickle'),"
                    + " ('sirbarty','sirbarty@mail.com', 'bartyy'),"
                    + " ('sk8erboi','sk8erboi@mail.com', 'sk8erb');";
            try {
                statemnt.executeUpdate(sql);
            } catch (SQLException e) {
            }

            sql = ""
                    + "INSERT INTO PLAYER"
                    + " (id, Per_login)"
                    + "VALUES"
                    + " (0, 'dingding'),"
                    + " (1, 'beepboop'),"
                    + " (2, 'pickless'),"
                    + " (3, 'sirbarty'),"
                    + " (4, 'sk8erboi');";
            try {
                statemnt.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            sql = ""
                    + "INSERT INTO LOCATION"
                    + " (id, area_Type)"
                    + "VALUES"
                    + " (0, 'forest'),"
                    + " (1, 'mountain'),"
                    + " (2, 'river'),"
                    + " (3, 'valley'),"
                    + " (4, 'swamp'),"
                    + " (1234, 'hut');";
            try {
                statemnt.executeUpdate(sql);
            } catch (SQLException e) {
            }

            sql = ""
                    + "INSERT INTO CHARACTR"
                    + " (name, strength, stamina, curHP, maxHP, Loc_id)"
                    + "VALUES"
                    + " ('dunkers', 55, 55, 100, 200, 1234),"
                    + " ('tibbers', 75, 75, 100, 200, 0),"
                    + " ('fisskyy', 40, 40, 100, 200, 1),"
                    + " ('shoresy', 41, 41, 100, 200, 2),"
                    + " ('jonesyy', 87, 87, 100, 200, 1234),"
                    + " ('bonbons', 20, 20, 100, 250, 4),"
                    + " ('pepegaa', 39, 39, 234, 235, 1234);";
            try {
                statemnt.executeUpdate(sql);
            } catch (SQLException e) {
            }

            sql = ""
                    + "INSERT INTO ITEM"
                    + " (id, weight, volume, Loc_id)"
                    + "VALUES"
                    + " (0, 5, 5, 0), "
                    + " (1, 15, 5, 0), "
                    + " (2, 5, 15, 0), "
                    + " (3, 10, 5, 0), "
                    + " (4, 10, 10, 0); ";
            try {
                statemnt.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            sql = ""
                    + "INSERT INTO CONTAINER"
                    + " (ID, weightLim, volumeLim, item_id, weight, volume)"
                    + "VALUES"
                    + " (0, 10, 10, 0, 5, 5),"
                    + " (1, 10, 10, 1, 5, 5),"
                    + " (2, 15, 15, 2, 10, 10),"
                    + " (4, 15, 15, 3, 10, 10),"
                    + " (5, 20, 20, 4, 15, 15);";
            try {
                statemnt.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            sql = ""
                    + "INSERT INTO ABILITY"
                    + " (id, type, exectime, targetstat, benepenal,"
                    + "effectrate, effectduration)"
                    + "VALUES"
                    + " (0, 'fire', 5, 'strength', true, 1.5, 10),"
                    + " (1, 'water', 5, 'strength', true, 1.5, 10);";

            try {
                statemnt.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            sql = ""
                    + "INSERT INTO WEAPON"
                    + " (id, equiploc, item_id, abil_id, weight, volume)"
                    + "VALUES"
                    + " (0, 2, 0, 0, 5, 5),"
                    + " (1, 2, 1, 0, 10, 10), "
                    + " (2, 3, 2, 0, 5, 5),"
                    + " (3, 4, 3, 0, 10, 10);";
            try {
                statemnt.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            sql = ""
                    + "INSERT INTO ARMOR"
                    + " (id, protection, wearloc, item_id, weight, volume)"
                    + "VALUES"
                    + " (0, 2, 0, 0, 5, 5),"
                    + " (1, 2, 1, 2, 10, 10), "
                    + " (2, 3, 2, 2, 5, 5),"
                    + " (3, 4, 3, 1, 10, 10);";
            try {
                statemnt.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        /**Creates all necessary tables in DB
         *
         * @throws Exception
         */
        public static void createTables() throws Exception
        {
            Statement statemnt = db_conn.createStatement();
            String sql = ""
                    + "CREATE TABLE PERSON"
                    + " (login      VARCHAR(40) NOT NULL,"
                    + "  email      VARCHAR(40) NOT NULL,"
                    + "  password   VARCHAR(40) NOT NULL,"
                    + " PRIMARY KEY(login),"
                    + " UNIQUE(email) );";
            try {
                statemnt.executeUpdate(sql);
            } catch (SQLException e) {
            }

            sql = ""
                    + "CREATE TABLE PLAYER"
                    + " (id         INT         NOT NULL,"
                    + "  Per_login  VARCHAR(40) NOT NULL,"
                    + " PRIMARY KEY (id),"
                    + " FOREIGN KEY (Per_login) REFERENCES PERSON (login) );";
            try {
                statemnt.executeUpdate(sql);
            } catch(SQLException e) {
            }

            sql = ""
                    + "CREATE TABLE CHARACTR"
                    + "  (name      VARCHAR(40) NOT NULL,"
                    + "   strength  INT         NOT NULL,"
                    + "   stamina   INT         NOT NULL,"
                    + "   curHP     INT         NOT NULL,"
                    + "   maxHP     INT         NOT NULL,"
                    + "   Loc_id    INT         NOT NULL,"
                    + " PRIMARY KEY (name),"
                    + " FOREIGN KEY (Loc_id) REFERENCES LOCATION (id) );";
            try {
                statemnt.executeUpdate(sql);
            } catch(SQLException e) {
            }

            sql = ""
                    + "CREATE TABLE CONTROLS"
                    + " (Play_id        INT             NOT NULL,"
                    + "  Char_name      VARCHAR(40)     NOT NULL,"
                    + " FOREIGN KEY (Player_id) REFERENCES PLAYER (id),"
                    + " FOREIGN KEY (Char_id) REFERENCES CHARACTR (id) );";
            try {
                statemnt.executeUpdate(sql);
            }
            catch(SQLException e) {
            }

            sql = ""
                    + "CREATE TABLE LOCATION"
                    + " (id             INT             NOT NULL,"
                    + "  area_type      VARCHAR(20)     NOT NULL,"
                    + " PRIMARY KEY(id) );";
            try {
                statemnt.executeUpdate(sql);
            } catch(SQLException e) {
            }

            sql = ""
                    + "CREATE TABLE ABILITY"
                    + " (id                 INT            NOT NULL,"
                    + "  type               VARCHAR(10)    NOT NULL,"
                    + "  execTime           INT            NOT NULL,"
                    + "  targetStat         VARCHAR(10)    NOT NULL,"
                    + "  benePenal          BOOL           NOT NULL,"
                    + "  effectRate         DOUBLE(3,2)    NOT NULL,"
                    + "  effectDuration     INT            NOT NULL,"
                    + " PRIMARY KEY (id) );";
            try {
                statemnt.executeUpdate(sql);
            } catch(SQLException e) {
            }

            sql = ""
                    + "CREATE TABLE ITEM"
                    + " (id        INT         NOT NULL,"
                    + "  weight    INT         NOT NULL,"
                    + "  volume    INT         NOT NULL,"
                    + "  Loc_id    INT         NOT NULL,"
                    + " PRIMARY KEY (id),"
                    + " FOREIGN KEY (Loc_id) REFERENCES LOCATION (id) );";
            try {
                statemnt.executeUpdate(sql);
            } catch(SQLException e) {
            }

            sql = ""
                    + "CREATE TABLE CONTAINER"
                    + " (id         INT        NOT NULL,"
                    + "  weightLim  INT        NOT NULL,"
                    + "  volumeLim  INT        NOT NULL,"
                    + "  Item_id    INT        NOT NULL,"
                    + " PRIMARY KEY(id),"
                    + " FOREIGN KEY (Item_id) REFERENCES ITEM (id) );";
            try {
                statemnt.executeUpdate(sql);
            } catch(SQLException e) {
            }

            sql = ""
                    + "CREATE TABLE WEAPON"
                    + " (id         INT        NOT NULL,"
                    + "  equipLoc   INT        NOT NULL,"
                    + "  Item_id    INT        NOT NULL,"
                    + "  Abil_id    INT        NOT NULL,"
                    + " PRIMARY KEY (id),"
                    + " FOREIGN KEY (Item_id) REFERENCES ITEM (id),"
                    + " FOREIGN KEY (Abil_id) REFERENCES ABILITY (id) );";
            try {
                statemnt.executeUpdate(sql);
            } catch(SQLException e) {
            }

            sql = ""
                    + "CREATE TABLE ARMOR"
                    + "  (id            INT        NOT NULL,"
                    + "   protection        INT        NOT NULL,"
                    + "   wearLoc        INT        NOT NULL,"
                    + "   Item_id        INT        NOT NULL,"
                    + "  PRIMARY KEY (id),"
                    + "  FOREIGN KEY (Item_id) REFERENCES ITEM (id) );";
            try {
                statemnt.executeUpdate(sql);
            } catch(SQLException e) {
            }
        }
}
