import java.sql.*;
import java.util.*;

public class Main_JDBC
{
    private static final String DB_URL = ""
        + "jdbc:mysql://db.cs.ship.edu:3306/csc371_26";
    private static final String USERNAME = "csc371_26";
    private static final String PASSWORD = "Password26";
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
                      "1: Run timed insertion test\n"
                    + "2: Create TEST Table w/o primary key\n"
                    + "3: Create TEST Table w/primary key\n"
                    + "4: Drop TEST Table\n"
                    + "5: Run timed selection test on HW4_DATA\n"
                    + "6: Exit");
            choice = s.nextInt();
            s.nextLine(); // clear the newline from the buffer

            if (choice == 1) {
                long time_result;
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
                    helper.createTable(db_conn, true);
                } else if (prim == 0) {
                    /** Create Table TEST_WEIGLE w/o primary key*/
                    helper.createTable(db_conn, false);
                } else {
                    break;
                }

                if (runs >= 1) {
                    for (; runs > 0; runs--) {
                        time_result = 0;
                        time_result = helper.runInsertion(db_conn);
                        if (time_result < 0) {
                            System.out.println("Error during insertion");
                            break;
                        }
                        if (prim == 1) {
                            helper.timeToFile(time_result, "primtime.dat");
                        } else {
                            helper.timeToFile(time_result, "nonprimtime.dat");
                        }
                        if (runs > 1) {
                            helper.dropTable(db_conn);
                            helper.createTable(db_conn, prim == 1);
                        }
                    }
                }
            } else if (choice == 2) {
                /** Create Table TEST_WEIGLE w/o primary key*/
                helper.createTable(db_conn, false);
            } else if (choice == 3) {
                /** Create Table TEST_WEIGLE w/primary key*/
                helper.createTable(db_conn, true);
            } else if (choice == 4) {
                /** Drop Table TEST_WEIGLE */
                helper.dropTable(db_conn);
            } else if (choice == 5) {
                long time_result;
                int runs = 0;
                int prim = 0;

                System.out.println(
                        "How many times do you want to run the test?");
                runs = s.nextInt();
                s.nextLine(); // clear newline from buffer

                System.out.println("Primary key test?\n1: yes\n0: no");
                prim = s.nextInt();
                s.nextLine(); // clear newline from buffer

                if (runs >= 1) {
                    for (; runs > 0; runs--) {
                        time_result = 0;
                        time_result = helper.runSelect(db_conn, prim == 1);
                        if (time_result < 0) {
                            System.out.println("Error during insertion");
                            break;
                        }
                        if (prim == 1) {
                            helper.timeToFile(time_result, "sprimtime.dat");
                        } else {
                            helper.timeToFile(time_result, "snonprimtime.dat");
                        }
                    }
                }
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
