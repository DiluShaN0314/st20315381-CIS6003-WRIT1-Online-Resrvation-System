<%@page import="java.util.Map"%>
<%@page import="model.User"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Menu" %>
<%@ page import="model.Table" %>
<%@ page import="model.Order" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create Order</title>
    <link rel="stylesheet" type="text/css" href="css/order.css">
    <style>
        .item-row {
            display: flex;
            align-items: center;
            margin-bottom: 10px;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        
        .item-row img {
            width: 50px; /* Mini size */
            height: auto;
            margin-right: 10px;
            border-radius: 4px;
        }
        
        .item-row span {
            flex-grow: 1;
        }
    </style>
    <script>
        function calculateTotal() {
            var total = 0;
            var items = document.querySelectorAll(".item-row");
            items.forEach(function(item) {
                var quantity = item.querySelector(".quantity").value;
                var price = parseFloat(item.querySelector(".price").textContent.replace('$', ''));
                total += quantity * price;
            });
            document.getElementById("total").textContent = '$' + total.toFixed(2);
        }

        function toggleTableSelection(orderType) {
            document.getElementById("table-selection").style.display = orderType === 'Dine-In' ? 'block' : 'none';
        }
    </script>
</head>
<body>
    <% User user = (session != null) ? (User) session.getAttribute("user") : null; 
        System.out.println("user id order : " +user.getId());
    %>
    <h2>Create Order</h2>
    <form action="OrderController" method="post">        
        <input type="hidden" name="id" value="<%= request.getAttribute("order") != null ? ((Order) request.getAttribute("order")).getId() : "" %>">
        <input type="hidden" name="userID" value="<%= user.getId() %>">
        <h3>Select Items</h3>
        <div id="menu-items">
            <%
                List<Menu> menuList = (List<Menu>) request.getAttribute("menuList");
                if (menuList != null) {
                    for (Menu menu : menuList) {
                        String imagePath = menu.getImagePath();
                        if (imagePath != null && !imagePath.isEmpty()) {
                            imagePath = "uploads/" + imagePath;
                        } else {
                            imagePath = "path/to/default/image.jpg"; // Placeholder image if none exists
                        }
            %>
            <div class="item-row">
                <img src="<%= imagePath %>" alt="<%= menu.getName() %>">
                <span><%= menu.getName() %> - $<%= menu.getPrice() %></span>
                <input type="number" name="quantities[<%= menu.getId() %>]" class="quantity" 
                    value="<%= (request.getAttribute("order") != null && ((Order) request.getAttribute("order")).getItemQuantities().get(menu.getId()) != null) ? 
                               ((Order) request.getAttribute("order")).getItemQuantities().get(menu.getId()) : 0 %>" 
                    min="0" onchange="calculateTotal()">
                <span class="price">$<%= menu.getPrice() %></span>
            </div>
            <%
                    }
                }
            %>
        </div>
        <h3>Order Type</h3>
        <select name="orderType" onchange="toggleTableSelection(this.value)">
            <option value="">Select Order Type</option>
            <option value="Delivery"
                    <%= request.getAttribute("order") != null && 
                        "Delivery".equals(((Order) request.getAttribute("order")).getOrderType()) ? "selected" : "" %> >
                Delivery
           </option>
            <option value="Dine-In"
                     <%= request.getAttribute("order") != null && 
                        "Dine-In".equals(((Order) request.getAttribute("order")).getOrderType()) ? "selected" : "" %>>
                Dine-In
            </option>
            <option value="Takeaway"
                    <%= request.getAttribute("order") != null && 
                        "Takeaway".equals(((Order) request.getAttribute("order")).getOrderType()) ? "selected" : "" %>>
                Take-Away
            </option>
        </select>
        
        <div id="table-selection" style="display:none;">
            <h3>Select Table</h3>
            <select name="tableId">
                <option value="">Select a Table</option>
                <%
                    List<Table> tableList = (List<Table>) request.getAttribute("tableList");
                    if (tableList != null) {
                        Order order = (Order) request.getAttribute("order");
//                        System.out.println("table id order 1 : "+ order.getTableId());
                        int tableID = (order != null) && order.getTableId() != null ? order.getTableId(): 0; // Extract role as int
                        for (Table table : tableList) {
                %>
                <option value="<%= table.getId() %>" <%= (table.getId() == tableID) ? "selected" : ""%>>
                    <%= table.getId() %> - Capacity: <%= table.getCapacity() %>
                </option>
                <%
                        }
                    }
                %>
            </select>
        </div>
        
        <h3>Total: <span id="total">$0.00</span></h3>
        
        <input type="submit" name="action"  value="<%= request.getAttribute("order") != null ? "update" : "placeOrder" %>">
    </form>
</body>
<script>
    calculateTotal();
    toggleTableSelection(document.getElementsByName('orderType')[0].value);
 </script>
</html>
