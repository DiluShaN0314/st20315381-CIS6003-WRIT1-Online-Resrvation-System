package controller;

import dao.ReservationDAO;
import model.Reservation;


import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.User;

@WebServlet("/ReservationController")
public class ReservationController extends HttpServlet {

    private ReservationDAO reservationDAO;

    @Override
    public void init() throws ServletException {
        try {
            reservationDAO = new ReservationDAO();
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
    
        if (action == null) {
            action = "list";
        }
        
        switch (action) {
            case "new":
                showNewForm(request, response);
                break;
            case "insert":
                insertReservation(request, response);
                break;
            case "delete":
        {
            try {
                cancelReservation(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(ReservationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                break;
            case "edit":
        {
            try {
                showEditForm(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(ReservationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                break;
            case "update":
        {
            try {
                updateReservation(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(ReservationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                break;
            default:
        {
            try {
                listReservation(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(ReservationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                break;
        }
       
    }
    
    private void listReservation(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String userId =  request.getParameter("id");
         System.out.println("user ID :" + userId);
        if (userId == null) {
            response.sendRedirect("SignInController"); // or handle the error appropriately
            return;
        }

        List<Reservation> reservations = reservationDAO.getAllReservations(parseInt(userId));
        request.setAttribute("listReservation", reservations);
        RequestDispatcher dispatcher = request.getRequestDispatcher("reservation-list.jsp");
        dispatcher.forward(request, response);
    }

    
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("reservation-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        Reservation existingReservation = reservationDAO.getReservation(id);
        request.setAttribute("reservation", existingReservation);
//        request.setAttribute("roles", getRolesFromDatabase());
        RequestDispatcher dispatcher = request.getRequestDispatcher("reservation-form.jsp");
        dispatcher.forward(request, response);
    }

    private void insertReservation(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        String customerID = request.getParameter("customerId");
        String staffID = request.getParameter("staffId");
        // Add null checks
        if (customerID == null || customerID.isEmpty()) {
            throw new ServletException("CustomerID is missing");
        }

        String reservationType = request.getParameter("reservationType");
        String reservationTime = request.getParameter("reservationTime");
        String numberOfGuests = request.getParameter("numberOfGuests");
        String status = request.getParameter("status");
        String paymentStatus = request.getParameter("paymentStatus");

        Reservation reservation = new Reservation();
        reservation.setCustomerID(Integer.parseInt(customerID));
        if (staffID != null && !staffID.isEmpty()) {
            reservation.setStaffID(Integer.parseInt(staffID));
        }
        reservation.setReservationType(reservationType);
        reservation.setReservationTime(reservationTime);
        reservation.setNumberOfGuests(Integer.parseInt(numberOfGuests));
        reservation.setStatus(status);
        reservation.setPaymentStatus(paymentStatus);

        try {
            reservationDAO.addReservation(reservation);
        response.sendRedirect("ReservationController?action=list&id="+Integer.parseInt(customerID));
        } catch (SQLException e) {
            throw new ServletException(e);
        }

    }


    private void updateReservation(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        
        String customerID = request.getParameter("customerId");
        String staffID = request.getParameter("staffId");
        // Add null checks
        if (customerID == null || customerID.isEmpty()) {
            throw new ServletException("CustomerID is missing");
        }

        String reservationType = request.getParameter("reservationType");
        String reservationTime = request.getParameter("reservationTime");
        String numberOfGuests = request.getParameter("numberOfGuests");
        String status = request.getParameter("status");
        String paymentStatus = request.getParameter("paymentStatus");

        Reservation reservation = new Reservation();
        reservation.setCustomerID(Integer.parseInt(customerID));
        if (staffID != null && !staffID.isEmpty()) {
            reservation.setStaffID(Integer.parseInt(staffID));
        }
        reservation.setReservationType(reservationType);
        reservation.setReservationTime(reservationTime);
        reservation.setNumberOfGuests(Integer.parseInt(numberOfGuests));
        reservation.setStatus(status);
        reservation.setPaymentStatus(paymentStatus);

        reservationDAO.updateReservation(reservation);
        response.sendRedirect("ReservationController?action=list&id="+Integer.parseInt(customerID));

    }

    private void cancelReservation(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        int cid = Integer.parseInt(request.getParameter("cId"));
        reservationDAO.deleteUser(id);
        response.sendRedirect("ReservationController?action=list&id="+cid);
    }

    
}
