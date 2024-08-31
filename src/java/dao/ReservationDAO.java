package dao;

import model.Reservation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import util.DatabaseConnection;

public class ReservationDAO {

    
    private Connection connection;
    
    public ReservationDAO() throws SQLException {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public void addReservation(Reservation reservation) throws SQLException {
        String sql = "INSERT INTO reservations (CustomerID, StaffID, ReservationType, ReservationTime, NumberOfGuests, Status, PaymentStatus, TableID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, reservation.getCustomerID());
        stmt.setObject(2, reservation.getStaffID());
        stmt.setString(3, reservation.getReservationType());
        stmt.setString(4, reservation.getReservationTime());
        stmt.setInt(5, reservation.getNumberOfGuests());
        stmt.setString(6, reservation.getStatus());
        stmt.setString(7, reservation.getPaymentStatus());
        stmt.setObject(8, reservation.getTableID(), Types.INTEGER);
        stmt.executeUpdate();
    }

    public List<Reservation> getAllReservations(int CustomerID) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations WHERE CustomerID = ? ";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, CustomerID);
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
        
        if(reservation.getStaffID() > 0) {
            sql = "UPDATE reservations SET CustomerID = ?, StaffID = ?, ReservationType = ?, ReservationTime = ?, NumberOfGuests = ?, Status = ?, PaymentStatus = ?, TableID = ? WHERE ReservationId=D = ?)";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, reservation.getCustomerID());
            stmt.setObject(2, reservation.getStaffID());
            stmt.setString(3, reservation.getReservationType());
            stmt.setString(4, reservation.getReservationTime());
            stmt.setInt(5, reservation.getNumberOfGuests());
            stmt.setString(6, reservation.getStatus());
            stmt.setString(7, reservation.getPaymentStatus());
            stmt.setObject(8, reservation.getTableID(), Types.INTEGER);
            stmt.setInt(9, reservation.getReservationID());
        } else {
            sql = "UPDATE reservations SET CustomerID = ?, ReservationType = ?, ReservationTime = ?, NumberOfGuests = ?, Status = ?, PaymentStatus = ?, TableID = ? WHERE ReservationId=D = ?)";
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
        
        stmt.executeUpdate();
    }

    public void deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM reservations WHERE ReservationId = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
    }
}
