package org.atc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public abstract class DatabaseConnector {
    private static String DB_URL_TIMETABLE = System.getenv("DB_TIMETABLE");
    private static String DB_URL_LAYOUT = System.getenv("DB_LAYOUT");
    private static String USER = System.getenv("DB_USER");
    private static String PASS = System.getenv("DB_PASS");

    private static Connection openConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL_TIMETABLE, USER, PASS);
    }

    public static boolean insertJob(Job job, Timetable timetable) {
        try (Connection connection = DatabaseConnector.openConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(timetable.generatePersistSqlString(job));
            int affectedRows = preparedStatement.executeUpdate();
            connection.close();
            if (affectedRows > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public static boolean updateJobPosition(Job job) {
        try (Connection connection = DatabaseConnector.openConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(job.generateUpdatedPositionSqlString());
            int affectedRows = preparedStatement.executeUpdate();
            connection.close();
            if (affectedRows > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
