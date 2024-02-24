package org.atc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConnector {
    public enum Database {
        TIMETABLE,
        LAYOUT
    }

    private static String DB_URL = System.getenv("DB_TIMETABLE");
    private static String USER = System.getenv("DB_USER");
    private static String PASS = System.getenv("DB_PASS");

    private Database database;

    public DatabaseConnector(Database database) {
        this.database = database;
    }

    public Connection openConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
