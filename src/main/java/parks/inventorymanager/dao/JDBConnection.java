package parks.inventorymanager.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Establishes a connection to the database.
 * @author Philip Parks */
public abstract class JDBConnection {

    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "inventory";
    private static final String jdbcUrl = protocol + vendor + location + databaseName;
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String userName = "sqlUser";
    private static final String password = "Passw0rd!";

    /** The database connection. */
    public static Connection connection;

    /** Opens a connection to the database. */
    public static void openConnection() {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
        } catch(SQLException | ClassNotFoundException e) {
            System.out.println("Error with the JDBC connection: " + e.getMessage());
        }
    }

    /** Gets A connection to the database.
     * @return A connection to the database. */
    public static Connection getConnection() {
        return connection;
    }

    /** Closes the connection to the database. */
    public static void closeConnection() {
        try {
            connection.close();
        } catch(Exception e) {
            // Do nothing
        }
    }

}
