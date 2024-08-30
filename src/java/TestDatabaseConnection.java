
import java.sql.Connection;
import java.sql.SQLException;
import util.DatabaseConnection;

public class TestDatabaseConnection {
    public static void main(String[] args) {
        try {
            DatabaseConnection dbConnection = DatabaseConnection.getInstance();
            Connection connection = dbConnection.getConnection();
            if (connection != null && !connection.isClosed()) {
                System.out.println("Database connection is successful!");
            } else {
                System.out.println("Database connection failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
