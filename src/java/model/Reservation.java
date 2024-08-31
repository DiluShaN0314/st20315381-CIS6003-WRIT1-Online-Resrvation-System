package model;

import java.util.Date;

public class Reservation {
    private int reservationID;
    private int customerID;
    private Integer staffID;
    private Integer tableID;
    private String reservationType;
    private String reservationTime;
    private int numberOfGuests;
    private String status;
    private String paymentStatus;

    // Getters and Setters
    public int getReservationID() {
        return reservationID;
    }
    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }
    public int getCustomerID() {
        return customerID;
    }
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    public Integer getStaffID() {
        return staffID;
    }
    public void setStaffID(Integer staffID) {
        this.staffID = staffID;
    }
    public String getReservationType() {
        return reservationType;
    }
    public void setReservationType(String reservationType) {
        this.reservationType = reservationType;
    }
    public String getReservationTime() {
        return reservationTime;
    }
    public void setReservationTime(String reservationTime) {
        this.reservationTime = reservationTime;
    }
    public int getNumberOfGuests() {
        return numberOfGuests;
    }
    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getPaymentStatus() {
        return paymentStatus;
    }
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setTableID(int TableID) {
        this.tableID = tableID;
    }
    
    public int getTableID() {
        return tableID;
    }
}
