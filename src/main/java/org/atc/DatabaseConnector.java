package org.atc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public abstract class DatabaseConnector {
    private static String DB_URL_TIMETABLE = System.getenv("DB_TIMETABLE");
    private static String DB_URL_LAYOUT = System.getenv("DB_LAYOUT");
    private static String USER = System.getenv("DB_USER");
    private static String PASS = System.getenv("DB_PASS");

    private static Connection openConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL_TIMETABLE, USER, PASS);
    }

    public static boolean insertJob(Job job) {
        try (Connection connection = DatabaseConnector.openConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(job.getInsertStatement());
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

    public static boolean insertSqlEntity(SQLEntity entity) {
        try (Connection connection = DatabaseConnector.openConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(entity.getInsertStatement());
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
            PreparedStatement preparedStatement = connection.prepareStatement(job.getPositionUpdateStatement());
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

    private String readCreationFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }
}
