<%@page import="java.util.List"%>
<%@page import="model.User"%>
<%@ page import="model.Reservation" %>
<%@ page import="model.Table" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reservation Form</title>
    <link rel="stylesheet" href="css/reservation-form.css">
    <script>
        function toggleTableSelection(reservationType) {
            document.getElementById("tableId").style.display = reservationType === 'Dine-In' ? 'block' : 'none';
            document.querySelector('[for="tableId"]').style.display = reservationType === 'Dine-In' ? 'block' : 'none';
        }
    </script>
</head>
<body>
    <% 
        // Retrieve the user object from the session
        User user = (User) session.getAttribute("user"); 
        int customerId = (user != null) ? user.getId() : -1; // default to -1 if user is null
        
         // Retrieve lists from request attributes
        
        List<Table> tableList = (List<Table>) request.getAttribute("tableList");
        List<User> customerList = (List<User>) request.getAttribute("customerList");
    %>
    <h2>Create a Reservation</h2>
    <form action="ReservationController" method="post">
        <input type="hidden" name="id" value="<%= request.getAttribute("reservation") != null ? ((Reservation) request.getAttribute("reservation")).getReservationID(): "" %>">
        <label for="customerId">Customer ID:</label>
       <% if(user.getRoleId() == 3) { %> 
        <input type="text" id="customerId" name="customerId" value="<%= request.getAttribute("reservation") != null ? ((Reservation) request.getAttribute("reservation")).getCustomerID() : customerId %>" required><br>
        <% } else { %>
        <select id="customerId" name="customerId">
            <option value="">Select Customer</option>
            <% 
                if (customerList != null) {
                    for (User customer : customerList) {
            %>
                <option value="<%= customer.getId() %>" <%= request.getAttribute("reservation") != null && customer.getId() == ((Reservation) request.getAttribute("reservation")).getCustomerID() ? "selected" : "" %>>
                    <%= customer.getName() %>
                </option>
            <% 
                    }
                } else {
                    out.println("<option>No customers available</option>");
                }
            %>
        </select><br>
        <% } %>
        
        
        <label for="staffId">Staff ID:</label>
        <select id="staffId" name="staffId">
            <option value="">Select Staff</option>
            <% 
                List<User> staffList = (List<User>) request.getAttribute("staffList");
                if (staffList != null) {
                    Reservation res = (Reservation) request.getAttribute("reservation");
                    int staffID = (res != null) && res.getStaffID() != null ? res.getStaffID(): 0; // Extract role as int
                    for (User staff : staffList) {
            %>
                <option value="<%= staff.getId() %>" <%= staff.getId() == staffID ? "selected" : "" %>>
                    <%= staff.getName() %>
                </option>
            <% 
                    }
                }
            %>
        </select><br>
        
        <label for="reservationType">Reservation Type:</label>
        <select id="reservationType" name="reservationType" required onchange="toggleTableSelection(this.value)">
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

        <label for="tableId">Table:</label>
        <select id="tableId" name="tableId">
            <option value="">Select Table</option>
            <% 
                if (tableList != null) {
                    for (Table table : tableList) {
            %>
                <option value="<%= table.getId() %>">
                    Table <%= table.getId() %> (Capacity: <%= table.getCapacity() %>)
                </option>
            <% 
                    }
                }
            %>
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
<script>
    toggleTableSelection(reservationType.value);
</script>
</html>
