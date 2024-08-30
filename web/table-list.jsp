<%@ page import="java.util.List" %>
<%@ page import="model.Table" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Table List</title>
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
        // JavaScript function to filter the table based on the search input
        function searchTable() {
            let input = document.getElementById('searchInput').value.toLowerCase();
            let rows = document.querySelectorAll('tbody tr');
            rows.forEach(row => {
                let id = row.children[0].innerText.toLowerCase();
                let capacity = row.children[1].innerText.toLowerCase();
                let status = row.children[2].innerText.toLowerCase();
                if (id.includes(input) || capacity.includes(input) || status.includes(input)) {
                    row.style.display = '';
                } else {
                    row.style.display = 'none';
                }
            });
        }
    </script>
</head>
<body>
    <h2>Table List</h2>
    <a href="TableController?action=new" style="display: inline-block; padding: 10px 20px; background-color: #007bff; color: #fff; text-decoration: none; border-radius: 4px;">Add New Table</a>
    
    <!-- Search bar -->
    <input type="text" id="searchInput" onkeyup="searchTable()" placeholder="Search for tables..." />
    
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Capacity</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<Table> listTable = (List<Table>) request.getAttribute("listTable");
                if (listTable != null) {
                    for (Table table : listTable) {
            %>
            <tr>
                <td><%= table.getId() %></td>
                <td><%= table.getCapacity() %></td>
                <td><%= table.getStatus() %></td>
                <td>
                    <a href="TableController?action=edit&id=<%= table.getId() %>">Edit</a>
                    <a href="TableController?action=delete&id=<%= table.getId() %>" onclick="return confirm('Are you sure?');">Delete</a>
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
