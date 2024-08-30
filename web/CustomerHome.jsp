<%@ page import="model.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home - Restaurant</title>
    <link rel="stylesheet" type="text/css" href="css/home.css">
</head>
<body>
    <% User user = (session != null) ? (User) session.getAttribute("user") : null; %>
    <header>
        <h1>Welcome to Our ABC Restaurant</h1>
        <nav>
            <ul>
                <li><a href="MenuController">Menu</a></li>
                <li><a href="ReservationController?action=list&id=<%= user.getId() %>">View Reservations</a></li>
                <li><a href="ReservationController?action=new&id=<%= user.getId() %>">Make a Reservation</a></li>
                <li><a href="OrderController?action=new&id=<%= user.getId() %>">Make an Order</a></li>
                <li><a href="OrderController?action=list&id=<%= user.getId() %>">View Orders</a></li>
            </ul>
        </nav>
        <% 
            if (user != null) {
            String imagePath = "uploads/" + user.getImagePath();
        %>
            <span class="profile-icon">
                <img src="<%= imagePath %>"> <!-- Adjust the path as needed -->
            </span>
            <span> <%= user.getEmail() %></span>
            <button class="edit-profile-button" onclick="editProfile(<%= user.getId() %>)">Edit Profile</button> 
            <button class="logout-button" onclick="confirmLogout()">Logout</button> <!-- Logout Button -->
        <%
            }
        %>
    </header>
    <%
        if (user != null) {
    %>
        <h2>Welcome Back <%= user.getName() %> !! </h2>
        <!-- Add other user details as needed -->
    <%
        } else {
    %>
        <p>Please log in to view your details.</p>
        <a href="SignInController">Login</a>
    <%
        }
    %>
    <footer>
        <p>&copy; 2024 ABC Restaurant. All rights reserved.</p>
    </footer>
</body>
<script>
    function editProfile(userId) {
        window.location.href = "UserController?action=edit&id=" + userId;
    }
    
    function confirmLogout() {
        if (confirm("Are you sure you want to logout?")) {
            window.location.href = "LogoutController"; // Redirect to logout if confirmed
        }
    }
</script>
</html>
