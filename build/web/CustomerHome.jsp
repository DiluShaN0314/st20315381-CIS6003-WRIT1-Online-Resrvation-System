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
        <h3>Offers will Announce Soon !!!</h3>
        
        <!-- Menu Page Iframe -->
        <div class="menu-section">
            <h3>Explore Our Menu</h3>
            <iframe src="MenuController" width="100%" height="600px" frameborder="0" allowfullscreen></iframe>
            <p>Please note that the menu is subject to change and may be cached by your browser. Refresh the page to see the latest updates.</p>
        </div>
    <%
        } else {
    %>
        <p>Please log in to view your details.</p>
        <a href="SignInController">Login</a>
    <%
        }
    %>
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
