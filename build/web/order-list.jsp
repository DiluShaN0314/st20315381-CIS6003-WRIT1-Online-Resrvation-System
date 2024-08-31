<%@page import="java.util.Map"%>
<%@page import="model.Table"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Order" %>
<%@ page import="model.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order List</title>
    <link rel="stylesheet" type="text/css" href="css/order-list.css">
     <style>
        /* Add some basic styling for the search bar */
        #searchInput {
            margin-bottom: 20px;
            padding: 10px;
            width: 100%;
            border-radius: 4px;
            border: 1px solid #ccc;
        }
        
        /* Hide the Customer ID column conditionally based on user role */
        <% 
            User user = (User) session.getAttribute("user"); 
            if (user != null && user.getRoleId() == 3) { // Assuming 3 is the role ID for customers
        %>
        .order-table th:nth-child(2),
        .order-table td:nth-child(2) {
            display: none;
        }
        <% } %>
    </style>
    <script>
        // JavaScript function to filter the reservation table
        function searchReservation() {
            let input = document.getElementById('searchInput').value.toLowerCase();
            let rows = document.querySelectorAll('tbody tr');
            rows.forEach(row => {
                let id = row.children[0].innerText.toLowerCase();
                <% if (user == null || user.getRoleId() != 3) { %> // Adjust indices if the Customer ID column is visible
                let userId = row.children[1].innerText.toLowerCase();
                let orderType = row.children[2].innerText.toLowerCase();
                let tableId = row.children[3].innerText.toLowerCase();
                let total = row.children[4].innerText.toLowerCase();
                if (id.includes(input) || userId.includes(input) || orderType.includes(input) || tableId.includes(input) || total.includes(input)) {
                    row.style.display = '';
                } else {
                    row.style.display = 'none';
                }
                <% } else { %> // Adjust indices if the Customer ID column is hidden
                let orderType = row.children[1].innerText.toLowerCase();
                let tableId = row.children[2].innerText.toLowerCase();
                let total = row.children[3].innerText.toLowerCase();
                if (id.includes(input) || orderType.includes(input) || tableId.includes(input) || total.includes(input)) {
                    row.style.display = '';
                } else {
                    row.style.display = 'none';
                }
                <% } %>
            });
        }
    </script>
</head>
<body>
    <h2>Order List</h2>
    
    <!-- Search bar -->
    <input type="text" id="searchInput" onkeyup="searchReservation()" placeholder="Search for reservations..." />
    
    <table class="order-table">
        <thead>
            <tr>
                <th>Order ID</th>
                <% if (user == null || user.getRoleId() != 3) { %> <!-- Show Customer ID column only if not a customer -->
                <th>Customer ID</th>
                <% } %>
                <th>Order Type</th>
                <th>Table Details</th>
                <th>Total</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<Order> orderList = (List<Order>) request.getAttribute("orderList");
                Map<Integer, Table> tableMap = (Map<Integer, Table>) request.getAttribute("tableMap");
                
                if (orderList != null && !orderList.isEmpty()) {
                    for (Order order : orderList) {
                        Table table = null;
                            if (order.getTableId() != null && tableMap != null) {
                                table = tableMap.get(order.getTableId());
                            }
            %>
            <tr>
                <td><%= order.getId() %></td>
                <% if (user == null || user.getRoleId() != 3) { %> <!-- Show Customer ID only if not a customer -->
                <td><%= order.getCustomerId() %></td>
                <% } %>
                <td><%= order.getOrderType() %></td>
                <td>
                    <%= (table != null) ? "Table " + table.getId() + " - Capacity: " + table.getCapacity() : "N/A" %>
                </td>
                <td>$<%= String.format("%.2f", order.getTotal()) %></td>
                <td>
                    <a class="action-btn" href="OrderController?action=edit&id=<%=order.getId()%>">Edit</a>
                    |
                    <a class="action-btn" href="OrderController?action=delete&id=<%=order.getId()%>" onclick="return confirm('Are you sure you want to delete this order?');">Delete</a>
                </td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="<%= user == null || user.getRoleId() != 3 ? 6 : 5 %>" class="no-orders">No orders found.</td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>
</body>
</html>
