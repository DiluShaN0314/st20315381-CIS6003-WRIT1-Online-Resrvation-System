<%@page import="model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home Page</title>
    <link rel="stylesheet" type="text/css" href="css/home.css">
</head>
<body>
    <% 
        User user = (session != null) ? (User) session.getAttribute("user") : null; 
        int role = (user != null) ? user.getRoleId(): 0;
    %>
    <header>
        <h1>Welcome to ABC Restaurant</h1>
        <nav>
            <ul>
                <li><a href="ReservationController?action=list&id=<%= user.getId() %>">Reservations</a></li>
                <li><a href="OrderController?action=list&id=<%= user.getId() %>">Orders</a></li>
                <% if (role == 1) { %> <!-- Check if the user is an admin -->
                    <li><a href="UserController?action=new">Users</a></li>
                    <li><a href="menu-form.jsp">Menu</a></li>
                    <li><a href="table-form.jsp">Tables</a></li>
                <% } %>
            </ul>
        </nav>
        <button class="logout-button" onclick="confirmLogout()">Logout</button> <!-- Logout Button -->
    </header>
    <main>
        <section>
            <h2>Our Services</h2>
            <p>At ABC Restaurant, we offer a wide range of services to make your dining experience exceptional. Browse through our reservation system, explore our menu, or check out our available tables.</p>
        </section>
        <section>
            <h2>Contact Us</h2>
            <p>If you have any questions, feel free to <a href="mailto:info@abcrestaurant.com">email us</a>.</p>
        </section>
    </main>
    <footer>
        <p>&copy; 2024 ABC Restaurant. All rights reserved.</p>
    </footer>
    
    <script>
        function confirmLogout() {
            if (confirm("Are you sure you want to logout?")) {
                window.location.href = "LogoutController"; // Redirect to logout if confirmed
            }
        }
    </script>
</body>
</html>
