package dao;

import model.Reservation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import model.User;
import util.DatabaseConnection;

public class ReservationDAO {

    
    private Connection connection;
    private UserDAO userDAO;
    public ReservationDAO() throws SQLException {
        connection = DatabaseConnection.getInstance().getConnection();
        userDAO = new UserDAO();
    }

    public void addReservation(Reservation reservation) throws SQLException {
        String sql = "INSERT INTO reservations (CustomerID, StaffID, ReservationType, ReservationTime, NumberOfGuests, Status, PaymentStatus, TableID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, reservation.getCustomerID());
        stmt.setObject(2, reservation.getStaffID(), Types.INTEGER);
        stmt.setString(3, reservation.getReservationType());
        stmt.setString(4, reservation.getReservationTime());
        stmt.setInt(5, reservation.getNumberOfGuests());
        stmt.setString(6, reservation.getStatus());
        stmt.setString(7, reservation.getPaymentStatus());
        System.out.println("Table ID: " + reservation.getTableID());
        if (reservation.getTableID() != null) {
            stmt.setObject(8, reservation.getTableID(), Types.INTEGER);
        } else {
            stmt.setNull(8, Types.INTEGER);
        }

        int affectedRows = stmt.executeUpdate();
//        System.out.println("Executing SQL: " + sql);
//
//        if (affectedRows > 0) {
//            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
//                if (generatedKeys.next()) {
//                    reservation.setReservationID(generatedKeys.getInt(1));
//                } else {
//                    throw new SQLException("Creating reservation failed, no ID obtained.");
//                }
//            }
//        } else {
//            throw new SQLException("Creating reservation failed, no rows affected.");
//        }
    }

    public List<Reservation> getAllReservations(int CustomerID) throws SQLException {
        userDAO = new UserDAO();
        User user = userDAO.getUser(CustomerID);
        List<Reservation> reservations = new ArrayList<>();
        String sql = user.getRoleId() == 3 ? "SELECT * FROM reservations WHERE CustomerID = ? " : "SELECT * FROM reservations";
        PreparedStatement stmt = connection.prepareStatement(sql);
        if(user.getRoleId() == 3) { stmt.setInt(1, CustomerID);}
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Reservation reservation = new Reservation();
            reservation.setReservationID(rs.getInt("ReservationID"));
            reservation.setCustomerID(rs.getInt("CustomerID"));
            reservation.setStaffID(rs.getObject("StaffID", Integer.class));
            reservation.setReservationType(rs.getString("ReservationType"));
            reservation.setReservationTime(rs.getString("ReservationTime"));
            reservation.setNumberOfGuests(rs.getInt("NumberOfGuests"));
            reservation.setStatus(rs.getString("Status"));
            reservation.setPaymentStatus(rs.getString("PaymentStatus"));
            reservations.add(reservation);
        }

        return reservations;
    }
    
    public Reservation getReservation(int id) throws SQLException {
        String sql = "SELECT * FROM reservations WHERE ReservationID = ? ";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Reservation reservation = new Reservation();
            reservation.setReservationID(rs.getInt("ReservationID"));
            reservation.setCustomerID(rs.getInt("CustomerID"));
            reservation.setStaffID(rs.getObject("StaffID", Integer.class));
            reservation.setReservationType(rs.getString("ReservationType"));
            reservation.setReservationTime(rs.getString("ReservationTime"));
            reservation.setNumberOfGuests(rs.getInt("NumberOfGuests"));
            reservation.setStatus(rs.getString("Status"));
            reservation.setPaymentStatus(rs.getString("PaymentStatus"));
            
            return reservation;
        }

        return null;
    }

    public void updateReservation(Reservation reservation) throws SQLException {
        String sql;
        PreparedStatement stmt;
        System.out.println("staff id reservation dao : " + reservation.getStaffID());
        if(reservation.getStaffID() > 0) {
            sql = "UPDATE reservations SET CustomerID = ?, StaffID = ?, ReservationType = ?, ReservationTime = ?, NumberOfGuests = ?, Status = ?, PaymentStatus = ?, TableID = ? WHERE ReservationID = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, reservation.getCustomerID());
            stmt.setObject(2, reservation.getStaffID(), Types.INTEGER);
            stmt.setString(3, reservation.getReservationType());
            stmt.setString(4, reservation.getReservationTime());
            stmt.setInt(5, reservation.getNumberOfGuests());
            stmt.setString(6, reservation.getStatus());
            stmt.setString(7, reservation.getPaymentStatus());
            stmt.setObject(8, reservation.getTableID(), Types.INTEGER);
            stmt.setInt(9, reservation.getReservationID());
        } else {
            sql = "UPDATE reservations SET CustomerID = ?, ReservationType = ?, ReservationTime = ?, NumberOfGuests = ?, Status = ?, PaymentStatus = ?, TableID = ? WHERE ReservationID = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, reservation.getCustomerID());
            stmt.setString(2, reservation.getReservationType());
            stmt.setString(3, reservation.getReservationTime());
            stmt.setInt(4, reservation.getNumberOfGuests());
            stmt.setString(5, reservation.getStatus());
            stmt.setString(6, reservation.getPaymentStatus());
            stmt.setObject(7, reservation.getTableID(), Types.INTEGER);
            stmt.setInt(8, reservation.getReservationID());
        }
        
        try {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM reservations WHERE ReservationId = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
    }
}
