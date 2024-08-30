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
</head>
<body>
    <h2>Order List</h2>
    <table class="order-table">
        <thead>
            <tr>
                <th>Order ID</th>
                <th>Customer ID</th>
                <th>Order Type</th>
                <th>Table ID</th>
                <th>Total</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<Order> orderList = (List<Order>) request.getAttribute("orderList");
                if (orderList != null && !orderList.isEmpty()) {
                    for (Order order : orderList) {
            %>
            <tr>
                <td><%= order.getId() %></td>
                <td><%= order.getCustomerId() %></td>
                <td><%= order.getOrderType() %></td>
                <td><%= order.getTableId() != null ? order.getTableId() : "N/A" %></td>
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
                <td colspan="5" class="no-orders">No orders found.</td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>
</body>
</html>
