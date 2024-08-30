<%@page import="model.User"%>
<%@ page import="model.Reservation" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reservation Form</title>
    <link rel="stylesheet" href="css/reservation-form.css">
</head>
<body>
    <% 
        // Retrieve the user object from the session
        User user = (User) session.getAttribute("user"); 
        int customerId = (user != null) ? user.getId() : -1; // default to -1 if user is null
    %>
    <h2>Create a Reservation</h2>
    <form action="ReservationController" method="post">
        <label for="customerId">Customer ID:</label>
        <input type="text" id="customerId" name="customerId" value="<%= request.getAttribute("reservation") != null ? ((Reservation) request.getAttribute("reservation")).getCustomerID() : customerId %>" required><br>

        <label for="staffId">Staff ID:</label>
        <input type="text" id="staffId" name="staffId" value="<%= request.getAttribute("reservation") != null ? ((Reservation) request.getAttribute("reservation")).getStaffID() : "" %>"><br>

        <label for="reservationType">Reservation Type:</label>
        <select id="reservationType" name="reservationType" required onchange="console.log(this.value)">
            <option value="">Select a Reservation Type</option>
            <option value="Delivery" 
               <%= request.getAttribute("reservation") != null && 
                        "Delivery".equals(((Reservation) request.getAttribute("reservation")).getReservationType()) ? "selected" : "" %> >
                Delivery
            </option>
            <option value="Dine-In" 
                <%= request.getAttribute("reservation") != null && 
                        "Dine-In".equals(((Reservation) request.getAttribute("reservation")).getReservationType()) ? "selected" : "" %>>
                Dine-In
            </option>
            <option value="Takeaway" 
                <%= request.getAttribute("reservation") != null && 
                        "Takeaway".equals(((Reservation) request.getAttribute("reservation")).getReservationType()) ? "selected" : "" %>>
                Takeaway
            </option>
        </select><br>

        <label for="reservationTime">Reservation Time:</label>
        <input type="datetime-local" id="reservationTime" name="reservationTime" value="<%= request.getAttribute("reservation") != null ? ((Reservation) request.getAttribute("reservation")).getReservationTime() : "" %>" required><br>

        <label for="numberOfGuests">Number of Guests:</label>
        <input type="number" id="numberOfGuests" name="numberOfGuests" value="<%= request.getAttribute("reservation") != null ? ((Reservation) request.getAttribute("reservation")).getNumberOfGuests() : "" %>" required><br>

        <label for="status">Status:</label>
        <select id="status" name="status" console.log(this.value)>
            <option value="">Select the status</option>
            <option value="Pending" 
                <%= request.getAttribute("reservation") != null && 
                    "Pending".equals(((Reservation) request.getAttribute("reservation")).getStatus()) ? "selected" : "" %>>
                Pending
            </option>
            <option value="Confirmed" 
                <%= request.getAttribute("reservation") != null && 
                    "Confirmed".equals(((Reservation) request.getAttribute("reservation")).getStatus()) ? "selected" : "" %>>
                Confirmed
            </option>
            <option value="Cancelled" 
                <%= request.getAttribute("reservation") != null && 
                    "Cancelled".equals(((Reservation) request.getAttribute("reservation")).getStatus()) ? "selected" : "" %>>
                Cancelled
            </option>
        </select><br>

        <label for="paymentStatus">Payment Status:</label>
        <select id="paymentStatus" name="paymentStatus" console.log(this.value)>
            <option value="">Select the payment status</option>
            <option value="Paid" 
               <%= request.getAttribute("reservation") != null && 
                       "Paid".equals(((Reservation) request.getAttribute("reservation")).getPaymentStatus()) ? "selected" : "" %>>
               Paid
            </option>
            <option value="Unpaid" 
                <%= request.getAttribute("reservation") != null && 
                        "Unpaid".equals(((Reservation) request.getAttribute("reservation")).getPaymentStatus()) ? "selected" : "" %>>
                Unpaid
            </option>
        </select><br>

        <input type="submit" name="action" value="<%= request.getAttribute("reservation") != null ? "update" : "insert" %>">
    </form>
</body>
</html>
