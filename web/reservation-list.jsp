<%@ page import="model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Reservation" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Reservation List</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <style>
        /* Add some basic styling for the search bar */
        #searchInput {
            margin-bottom: 20px;
            padding: 10px;
            width: 100%;
            border-radius: 4px;
            border: 1px solid #ccc;
        }
    </style>
    <script>
        // JavaScript function to filter the reservation table
        function searchReservation() {
            let input = document.getElementById('searchInput').value.toLowerCase();
            let rows = document.querySelectorAll('tbody tr');
            rows.forEach(row => {
                let id = row.children[0].innerText.toLowerCase();
                let userId = row.children[1].innerText.toLowerCase();
                let guests = row.children[2].innerText.toLowerCase();
                let status = row.children[3].innerText.toLowerCase();
                let date = row.children[4].innerText.toLowerCase();
                if (id.includes(input) || userId.includes(input) || guests.includes(input) || status.includes(input) || date.includes(input)) {
                    row.style.display = '';
                } else {
                    row.style.display = 'none';
                }
            });
        }
    </script>
</head>
<body>
    <% 
        // Retrieve the user object from the session
        User user = (User) session.getAttribute("user"); 
        int customerId = (user != null) ? user.getId() : -1; // default to -1 if user is null
    %>
    <h2>Reservation List</h2>
    <a href="ReservationController?action=new&id=<%= customerId %>" style="display: inline-block; padding: 10px 20px; background-color: #007bff; color: #fff; text-decoration: none; border-radius: 4px;">Add New Reservation</a>
    
    <!-- Search bar -->
    <input type="text" id="searchInput" onkeyup="searchReservation()" placeholder="Search for reservations..." />
    
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>User ID</th>
                <th>Number Of Guests</th>
                <th>Status</th>
                <th>Reservation Date</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<Reservation> listReservation = (List<Reservation>) request.getAttribute("listReservation");
                if (listReservation != null) {
                    for (Reservation reservation : listReservation) {
            %>
            <tr>
                <td><%= reservation.getReservationID() %></td>
                <td><%= reservation.getCustomerID() %></td>
                <td><%= reservation.getNumberOfGuests() %></td>
                <td><%= reservation.getStatus() %></td>
                <td><%= reservation.getReservationTime() %></td>
                <td>
                    <a href="ReservationController?action=edit&id=<%= reservation.getReservationID() %>">Edit</a>
                    <a href="ReservationController?action=delete&id=<%= reservation.getReservationID() %>&cId=<%= customerId %>" onclick="return confirm('Are you sure?');">Delete</a>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </tbody>
    </table>
</body>
</html>
