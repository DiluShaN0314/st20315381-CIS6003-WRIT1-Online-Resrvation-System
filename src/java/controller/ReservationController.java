package controller;

import dao.ReservationDAO;
import dao.UserDAO;
import dao.TableDAO;
import model.Reservation;
import model.Table;
import model.User;

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

@WebServlet("/ReservationController")
public class ReservationController extends HttpServlet {

    private ReservationDAO reservationDAO;
    private UserDAO userDAO;
    private TableDAO tableDAO;

    @Override
    public void init() throws ServletException {
        try {
            reservationDAO = new ReservationDAO();
            userDAO = new UserDAO();
            tableDAO = new TableDAO();
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
        {
            try {
                showNewForm(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(ReservationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
            throws ServletException, IOException, SQLException {
        List<User> staffList = userDAO.getAllStaff();
        List<User> customerList = userDAO.getAllCustomer();
        List<Table> tableList = tableDAO.getAllTables();
        request.setAttribute("staffList", staffList);
        request.setAttribute("customerList", customerList);
        request.setAttribute("tableList", tableList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("reservation-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));        
        List<User> staffList = userDAO.getAllStaff();      
        List<User> customerList = userDAO.getAllCustomer();
        List<Table> tableList = tableDAO.getAllTables();
        request.setAttribute("staffList", staffList);
        request.setAttribute("customerList", customerList);
        request.setAttribute("tableList", tableList);
        Reservation existingReservation = reservationDAO.getReservation(id);
        request.setAttribute("reservation", existingReservation);
//        request.setAttribute("roles", getRolesFromDatabase());
        RequestDispatcher dispatcher = request.getRequestDispatcher("reservation-form.jsp");
        dispatcher.forward(request, response);
    }

    private void insertReservation(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        Reservation reservation = buildReservationFromRequest(request);
        
        try {
            System.out.println("Inserting reservation: " + reservation); // <-- Add this line for debugging
            reservationDAO.addReservation(reservation);
            response.sendRedirect("ReservationController?action=list&id=" + reservation.getCustomerID());
        } catch (SQLException e) {
            e.printStackTrace(); // <-- Add this line to print the stack trace
            throw new ServletException(e);
        }
    }

    private void updateReservation(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException, SQLException {
        Reservation reservation = buildReservationFromRequest(request);

        try {
            System.out.println("Updating reservation: " + reservation); // <-- Add this line for debugging
            reservationDAO.updateReservation(reservation);
            response.sendRedirect("ReservationController?action=list&id=" + reservation.getCustomerID());
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private Reservation buildReservationFromRequest(HttpServletRequest request) throws ServletException {
        String customerID = request.getParameter("customerId");
        if (customerID == null || customerID.isEmpty()) {
            throw new ServletException("CustomerID is missing");
        }

        String staffID = request.getParameter("staffId");
        String tableID = request.getParameter("tableId");
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
        if (tableID != null && !tableID.isEmpty()) {
            reservation.setTableID(Integer.parseInt(tableID));
        } else {
            reservation.setTableID(null); // Or set a default value
        }

        reservation.setReservationType(reservationType);
        reservation.setReservationTime(reservationTime);
        reservation.setNumberOfGuests(Integer.parseInt(numberOfGuests));
        reservation.setStatus(status);
        reservation.setPaymentStatus(paymentStatus);

        return reservation;
    }

    
//    private void insertReservation(HttpServletRequest request, HttpServletResponse response) 
//        throws ServletException, IOException {
//        String customerID = request.getParameter("customerId");
//        String staffID = request.getParameter("staffId");
//        String tableID = request.getParameter("tableId");
//        // Add null checks
//        if (customerID == null || customerID.isEmpty()) {
//            throw new ServletException("CustomerID is missing");
//        }
//        
//        String reservationType = request.getParameter("reservationType");
//        String reservationTime = request.getParameter("reservationTime");
//        String numberOfGuests = request.getParameter("numberOfGuests");
//        String status = request.getParameter("status");
//        String paymentStatus = request.getParameter("paymentStatus");
//        
//        Reservation reservation = new Reservation();
//        reservation.setCustomerID(Integer.parseInt(customerID));
//        if (staffID != null && !staffID.isEmpty()) {
//            reservation.setStaffID(Integer.parseInt(staffID));
//        }
//        if (tableID != null && !tableID.isEmpty()) {
//            reservation.setTableID(Integer.parseInt(tableID));
//        }
//        reservation.setReservationType(reservationType);
//        reservation.setReservationTime(reservationTime);
//        reservation.setNumberOfGuests(Integer.parseInt(numberOfGuests));
//        reservation.setStatus(status);
//        reservation.setPaymentStatus(paymentStatus);
//
//        try {
//            reservationDAO.addReservation(reservation);
//        response.sendRedirect("ReservationController?action=list&id="+Integer.parseInt(customerID));
//        } catch (SQLException e) {
//            throw new ServletException(e);
//        }
//
//    }
//
//
//    private void updateReservation(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
//        
//        String customerID = request.getParameter("customerId");
//        String staffID = request.getParameter("staffId");
//        String tableID = request.getParameter("tableId");
//        // Add null checks
//        if (customerID == null || customerID.isEmpty()) {
//            throw new ServletException("CustomerID is missing");
//        }
//
//        String reservationType = request.getParameter("reservationType");
//        String reservationTime = request.getParameter("reservationTime");
//        String numberOfGuests = request.getParameter("numberOfGuests");
//        String status = request.getParameter("status");
//        String paymentStatus = request.getParameter("paymentStatus");
//
//        Reservation reservation = new Reservation();
//        reservation.setCustomerID(Integer.parseInt(customerID));
//        if (staffID != null && !staffID.isEmpty()) {
//            reservation.setStaffID(Integer.parseInt(staffID));
//        }
//        if (tableID != null && !tableID.isEmpty()) {
//            reservation.setTableID(Integer.parseInt(tableID));
//        }
//        reservation.setReservationType(reservationType);
//        reservation.setReservationTime(reservationTime);
//        reservation.setNumberOfGuests(Integer.parseInt(numberOfGuests));
//        reservation.setStatus(status);
//        reservation.setPaymentStatus(paymentStatus);
//
//        reservationDAO.updateReservation(reservation);
//        response.sendRedirect("ReservationController?action=list&id="+Integer.parseInt(customerID));
//
//    }

    private void cancelReservation(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        int cid = Integer.parseInt(request.getParameter("cId"));
        reservationDAO.deleteUser(id);
        response.sendRedirect("ReservationController?action=list&id="+cid);
    }

    
}
