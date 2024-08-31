<%@ page import="java.util.List" %>
<%@ page import="model.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User List</title>
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
        // JavaScript function to filter the user table
        function searchUser() {
            let input = document.getElementById('searchInput').value.toLowerCase();
            let rows = document.querySelectorAll('tbody tr');
            rows.forEach(row => {
                let name = row.children[1].innerText.toLowerCase();
                let email = row.children[2].innerText.toLowerCase();
                if (name.includes(input) || email.includes(input)) {
                    row.style.display = '';
                } else {
                    row.style.display = 'none';
                }
            });
        }
    </script>
</head>
<body>
    <h2>User List</h2>
    <a href="UserController?action=new" style="display: inline-block; padding: 10px 20px; background-color: #007bff; color: #fff; text-decoration: none; border-radius: 4px;">Add New User</a>
    <br>
    <!-- Search bar -->
    <input type="text" id="searchInput" onkeyup="searchUser()" placeholder="Search for users..." />
    
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Role</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<User> listUser = (List<User>) request.getAttribute("listUser");
                if (listUser != null) {
                    for (User user : listUser) {
            %>
            <tr>
                <td><%= user.getId() %></td>
                <td><%= user.getName() %></td>
                <td><%= user.getEmail() %></td>
                <td><%= user.getRole() %></td>
                <td>
                    <a href="UserController?action=edit&id=<%= user.getId() %>">Edit</a>
                    <a href="UserController?action=delete&id=<%= user.getId() %>" onclick="return confirm('Are you sure?');">Delete</a>
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
