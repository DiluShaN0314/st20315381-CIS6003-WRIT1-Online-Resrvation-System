<%@ page import="model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Menu" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Menu List</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <script>
        // JavaScript function to filter the menu items by name
        function searchMenu() {
            let input = document.getElementById('searchInput').value.toLowerCase();
            let items = document.getElementsByClassName('grid-item');
       
            for (let i = 0; i < items.length; i++) {
                let name = items[i].getElementsByTagName('h3')[0].innerText.toLowerCase();
                let category = items[i].getElementsByTagName('p')[0].innerText.substring(10).toLowerCase();

                if (name.includes(input) || category.includes(input)) {
                    items[i].style.display = '';
                } else {
                    items[i].style.display = 'none';
                }
            }
        }
    </script>
</head>
<body>
    <% User user = (session != null) ? (User) session.getAttribute("user") : null; %>
    <h2>Menu List</h2>
    
    <% if (user != null && user.getRoleId() != 3) { %>
        <a href="MenuController?action=new" style="display: inline-block; padding: 10px 20px; background-color: #007bff; color: #fff; text-decoration: none; border-radius: 4px;">Add New Menu Item</a>
    <% } %>
    
    <!-- Search bar -->
    <input type="text" id="searchInput" onkeyup="searchMenu()" placeholder="Search for menu items..." style="margin-top: 20px; padding: 10px; width: 100%; border-radius: 4px; border: 1px solid #ccc;">

    <div class="grid-container" style="margin-top: 20px;">
        <%
            List<Menu> listMenu = (List<Menu>) request.getAttribute("listMenu");
            if (listMenu != null) {
                for (Menu menu : listMenu) {
                    String imagePath = menu.getImagePath();
                    if (imagePath != null && !imagePath.isEmpty()) {
                        imagePath = "uploads/" + imagePath;
                    } else {
                        imagePath = "path/to/default/image.jpg"; // Placeholder image if none exists
                    }
        %>
        <div class="grid-item" style="margin-bottom: 20px;">
            <img src="<%= imagePath %>" alt="<%= menu.getName() %>" style="width: 100%; height: auto;">
            <h3><%= menu.getName() %></h3>
            <p><strong>Category:</strong> <%= menu.getCategory() %></p>
            <p><strong>Description:</strong> <%= menu.getDescription() %></p>
            <p><strong>Price:</strong> $<%= menu.getPrice() %></p>
            <% if (user != null && user.getRoleId() != 3) { %>
                <a href="MenuController?action=edit&id=<%= menu.getId() %>" style="margin-right: 10px;">Edit</a>
                <a href="MenuController?action=delete&id=<%= menu.getId() %>" onclick="return confirm('Are you sure?');">Delete</a>
            <% } else {%>
                <a href="OrderController?action=new&id=<%= user.getId() %>">Place Order</a>
            <% }%>
        </div>
        <%
                }
            }
        %>
    </div>
</body>
</html>
