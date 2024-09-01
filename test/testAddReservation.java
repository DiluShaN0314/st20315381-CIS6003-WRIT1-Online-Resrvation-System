/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dao.ReservationDAO;
import java.sql.SQLException;
import model.Reservation;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dell
 */
public class testAddReservation {
    
    public testAddReservation() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void testAddReservation() throws SQLException {
        Reservation reservation = new Reservation();
        ReservationDAO reservationDAO = new ReservationDAO();
        reservation.setCustomerID(21);
        reservation.setStaffID(1);
        reservation.setTableID(7);
        reservation.setReservationType("Dine-In");
        reservation.setReservationTime("2024-09-15 19:00");
        reservation.setNumberOfGuests(4);
        reservation.setStatus("Confirmed");
        reservation.setPaymentStatus("Paid");

        reservationDAO.addReservation(reservation);

         assertNotNull("Reservation ID should not be null", reservation.getReservationID());
    System.out.println("Reservation ID: " + reservation.getReservationID());
        // Fetch the reservation and assert it was added correctly
        Reservation fetchedReservation = reservationDAO.getReservation(reservation.getReservationID());

        // Debugging
        System.out.println("Fetched Reservation: " + fetchedReservation);

        assertNotNull(fetchedReservation);
        assertEquals(reservation.getCustomerID(), fetchedReservation.getCustomerID());
        assertEquals(reservation.getStaffID(), fetchedReservation.getStaffID());
        assertEquals(reservation.getTableID(), fetchedReservation.getTableID());
        assertEquals(reservation.getReservationType(), fetchedReservation.getReservationType());
        assertEquals(reservation.getReservationTime(), fetchedReservation.getReservationTime());
        assertEquals(reservation.getNumberOfGuests(), fetchedReservation.getNumberOfGuests());
        assertEquals(reservation.getStatus(), fetchedReservation.getStatus());
        assertEquals(reservation.getPaymentStatus(), fetchedReservation.getPaymentStatus());
    }

}
