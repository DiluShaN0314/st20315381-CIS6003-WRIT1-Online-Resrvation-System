
import java.sql.Connection;
import util.DatabaseConnection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

class MenuDAOTest {
private Connection connection;
    public MenuDAOTest() throws SQLException {
        connection = DatabaseConnection.getInstance().getConnection();
    }
    public static void main(String[] args) throws SQLException  {
       // Create an instance of MenuDAOTest
        MenuDAOTest menuDAOTest = new MenuDAOTest();
        
        // Call the non-static method using the instance
        menuDAOTest.testAddReservation();
    }
    public void testAddReservation() throws SQLException {
        // Arrange
         String sql = "INSERT INTO reservations (CustomerID, StaffID, ReservationType, ReservationTime, NumberOfGuests, Status, PaymentStatus, TableID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, 5);
        stmt.setObject(2, 21, Types.INTEGER);
        stmt.setString(3, "Delivery");
        stmt.setString(4, "2024-09-01T07:07");
        stmt.setInt(5, 2);
        stmt.setString(6, "Pending");
        stmt.setString(7, "Paid");
        stmt.setObject(8, null, Types.INTEGER);
        stmt.executeUpdate();
    }
}
