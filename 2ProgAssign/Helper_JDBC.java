import java.io.*;
import java.sql.*;
import java.util.*;

public class Helper_JDBC
{
    private Connection db_conn;
    Helper_JDBC(Connection db_conn)
    {
        this.db_conn = db_conn;
    }
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
            + " ('fisskyy', 40, 40, 80, 200, 1),"
            + " ('shoresy', 41, 41, 100, 200, 2),"
            + " ('jonesyy', 37, 55, 90, 200, 1234),"
            + " ('toblero', 97, 77, 100, 200, 1234),"
            + " ('jabbers', 87, 27, 85, 200, 1234),"
            + " ('bonbons', 20, 20, 95, 250, 4),"
            + " ('pepegaa', 39, 39, 234, 235, 1234);";
        execSQLUpdate(statemnt, sql);

        sql = "INSERT INTO CONTROLS"
            + " (Play_id, Char_name)"
            + "VALUES"
            + " (0, 'dunkers'),"
            + " (0, 'tibbers'),"
            + " (0, 'fisskyy'),"
            + " (0, 'shoresy'),"
            + " (0, 'jonesyy'),"
            + " (0, 'jabbers'),"
            + " (0, 'toblero'),"
            + " (0, 'bonbons'),"
            + " (0, 'pepegaa');";
        execSQLUpdate(statemnt, sql);

        sql = "INSERT INTO ITEM"
            + " (id, weight, volume, Loc_id)"
            + "VALUES"
            + " (0, 5, 5, 0),"
            + " (1, 15, 5, 0),"
            + " (2, 5, 15, 0),"
            + " (3, 10, 5, 0),"
            + " (4, 10, 5, 0),"
            + " (5, 10, 5, 0),"
            + " (6, 10, 5, 0),"
            + " (7, 10, 5, 0),"
            + " (8, 10, 5, 0),"
            + " (9, 10, 5, 0),"
            + " (10, 10, 5, 0),"
            + " (11, 10, 5, 0),"
            + " (12, 10, 10, 0);";
        execSQLUpdate(statemnt, sql);

        sql = "INSERT INTO CONTAINER"
            + " (id, weightLim, volumeLim, Item_id)"
            + "VALUES"
            + " (0, 10, 10, 0),"
            + " (1, 10, 10, 1),"
            + " (2, 15, 15, 2),"
            + " (4, 15, 15, 3),"
            + " (5, 20, 20, 4);";
        execSQLUpdate(statemnt, sql);

        sql = "INSERT INTO ABILITY"
            + " (id, type, execTime, targetStat, benePenal,"
            + "effectRate, effectDuration)"
            + "VALUES"
            + " (0, 'fire', 5, 'strength', true, 1.5, 10),"
            + " (1, 'water', 5, 'strength', true, 1.5, 10);";
        execSQLUpdate(statemnt, sql);

        sql = "INSERT INTO WEAPON"
            + " (id, equipLoc, Item_id, Abil_id)"
            + "VALUES"
            + " (0, 2, 5, 0),"
            + " (1, 2, 6, 0), "
            + " (2, 3, 7, 0),"
            + " (3, 4, 8, 0);";
        execSQLUpdate(statemnt, sql);

        sql = "INSERT INTO ARMOR"
            + " (id, protection, wearLoc, Item_id)"
            + "VALUES"
            + " (0, 2, 0, 9),"
            + " (1, 2, 1, 10), "
            + " (2, 3, 2, 11),"
            + " (3, 4, 3, 12);";
        execSQLUpdate(statemnt, sql);

        if (statemnt != null) {
            statemnt.close();
        }
    }

    public void updateCharAttrs (String[] columnNames, Object[] charInfo) throws SQLException
    {
        String sql = null;
        String sqlEnd = null;
        statemnt = db_conn.createStatement();

        sql = ""
            + "UPDATE CHARACTR"
            + "SET";
        for (int i = 0; i < columnNames.length; i++) {
            sqlEnd += " "
                + columnNames[i] + " = "
                + charInfo[i] + ",";
        }
        sqlEnd = sqlEnd.substring(0, sqlEnd.length()-1);
        sql += sqlEnd
            + "WHERE name = "
            + (String) charInfo[0]
            + ";";

        execSQLUpdate(statemnt, sql);
    }

    public Object[] getCharInfo (String charName) throws SQLException
    {
        ResultSet rs = null;
        String sql = null;
        statemnt = db_conn.createStatement();

        sql = "SELECT * FROM CHARACTR WHERE name ='"
            + charName
            + "';";

        try {
            rs = statemnt.executeQuery(sql);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            if (statemnt != null) {
                statemnt.close();
            }
            return null;
        }

        Object[] attributes = null;
        List<Object> attrList = new ArrayList<Object>();
        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
//            String colName = rs.getMetaData().getColumnName(i);
            attrList.add(rs.getObject(i));
        }
        attributes = attrList.toArray(attributes);
        return attributes;
    }

    public String[] getDBCharList(int playID) throws SQLException
    {
        ResultSet rs = null;
        String sql = null;
        statemnt = db_conn.createStatement();

        sql = "SELECT * FROM CHARACTR chr "
            + "JOIN CONTROLS cnt "
            + "ON chr.name = cnt.Char_name "
            + "WHERE cnt.Play_id = "
            + String.valueOf(playID)
            + ";";

        try {
            rs = statemnt.executeQuery(sql);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            if (statemnt != null) {
                statemnt.close();
            }
            return null;
        }

        String[] charNames = null;
        List<String> charNameList = new ArrayList<String>();
        while (rs.next()) {
            if (rs.getString("name") != null) {
                charNameList.add(rs.getString("name"));
                System.out.println("got " + rs.getString("name"));
            }
        }
        charNames = new String[charNameList.size()];
        charNames = charNameList.toArray(charNames);
        return charNames;
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
            + " PRIMARY KEY (Play_id, Char_name),"
            + " FOREIGN KEY (Play_id) REFERENCES PLAYER (id),"
            + " FOREIGN KEY (Char_name) REFERENCES CHARACTR (name) );";
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
     * Runs an sql update given by the string sql
     * @return true if sql executed successfully
     */
    private boolean execSQLUpdate (Statement statemnt, String sql)
    {
        try {
            statemnt.executeUpdate(sql);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        }
        return true;
    }
}
