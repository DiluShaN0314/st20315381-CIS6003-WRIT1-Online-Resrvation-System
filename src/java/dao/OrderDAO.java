package dao;

import model.Menu;
import model.Order;
import model.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.User;
import util.DatabaseConnection;

public class OrderDAO {
    private Connection connection;
    private UserDAO userDAO;
    public OrderDAO() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    // Fetch orders by customer ID
    public List<Order> getOrdersByCustomerId(int customerId) throws SQLException {
        userDAO = new UserDAO();
        User user = userDAO.getUser(customerId);
        List<Order> orderList = new ArrayList<>();
        
        String query = user.getRoleId() == 3 ? "SELECT * FROM orders WHERE CustomerID = ?" : "SELECT * FROM orders";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            if(user.getRoleId() == 3){ pstmt.setInt(1, customerId);}
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getInt("ID"));
                    order.setCustomerId(rs.getInt("CustomerID"));
                    order.setOrderType(rs.getString("OrderType"));
                    order.setTableId(rs.getObject("TableID", Integer.class));
                    order.setTotal(rs.getDouble("Total"));
                    orderList.add(order);
                }
            }
        }
        return orderList;
    }

    // Fetch order by ID
    public Order getOrderById(int orderId) throws SQLException {
        Order order = null;
        String query = "SELECT * FROM orders WHERE ID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, orderId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    order = new Order();
                    order.setId(rs.getInt("ID"));
                    order.setCustomerId(rs.getInt("CustomerID"));
                    order.setOrderType(rs.getString("OrderType"));
                    order.setTableId(rs.getObject("TableID", Integer.class));
                    order.setTotal(rs.getDouble("Total"));
                    getOrderItemQty(order);
                }
            }
        }
        return order;
    }
    
    public void getOrderItemQty(Order order) throws SQLException {
        String query = "SELECT * FROM order_items WHERE OrderID = ?";
        Map<Integer, Integer> itemQuantities = new HashMap<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, order.getId());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int menuId = rs.getInt("ItemID"); // Assuming MenuID is the item ID
                    int qty = rs.getInt("Qty");       // Quantity of the item

                    // Add the item ID and quantity to the map
                    itemQuantities.put(menuId, qty);
                }
            }
        }

        // Set the populated map to the order object
        order.setItemQuantities(itemQuantities);
    }

    
     public double getItemPrice(int itemId) throws SQLException {
        String query = "SELECT Price FROM products WHERE ProductID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, itemId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("Price");
                } else {
                    throw new SQLException("Item not found: ID " + itemId);
                }
            }
        }
    }
     
    public void addOrder(Order order) throws SQLException {
        String insertOrderQuery = "INSERT INTO orders (CustomerID, OrderType, TableID, Total) VALUES (?, ?, ?, ?)";
        String insertOrderItemQuery = "INSERT INTO order_items (OrderID, ItemID, Qty) VALUES (?, ?, ?)";

        // Start transaction
        connection.setAutoCommit(false);
        try (PreparedStatement orderStmt = connection.prepareStatement(insertOrderQuery, Statement.RETURN_GENERATED_KEYS)) {
            orderStmt.setInt(1, order.getCustomerId());
            orderStmt.setString(2, order.getOrderType());
            orderStmt.setObject(3, order.getTableId(), Types.INTEGER); // Handle nullable field
            orderStmt.setDouble(4, order.getTotal());
            orderStmt.executeUpdate();

            // Retrieve generated order ID
            try (ResultSet rs = orderStmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int orderId = rs.getInt(1);

                    // Insert order items
                    try (PreparedStatement itemStmt = connection.prepareStatement(insertOrderItemQuery)) {
                        for (Map.Entry<Integer, Integer> entry : order.getItemQuantities().entrySet()) {
                            itemStmt.setInt(1, orderId);
                            itemStmt.setInt(2, entry.getKey());
                            itemStmt.setInt(3, entry.getValue());
                            itemStmt.addBatch();
                        }
                        itemStmt.executeBatch();
                    }
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }

            // Commit transaction
            connection.commit();
        } catch (SQLException e) {
            // Rollback transaction on error
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }
    // Update an existing order
    public void updateOrder(Order order) throws SQLException {
        String updateOrderQuery = "UPDATE orders SET OrderType = ?, TableID = ?, Total = ? WHERE ID = ?";

        // Start transaction
        connection.setAutoCommit(false);
        try (PreparedStatement orderStmt = connection.prepareStatement(updateOrderQuery)) {
            orderStmt.setString(1, order.getOrderType());
            orderStmt.setObject(2, order.getTableId(), Types.INTEGER); // Handle nullable field
            orderStmt.setDouble(3, order.getTotal());
            orderStmt.setInt(4, order.getId());
            orderStmt.executeUpdate();

            // Update order items
            String deleteOrderItemsQuery = "DELETE FROM order_items WHERE OrderID = ?";
            try (PreparedStatement deleteStmt = connection.prepareStatement(deleteOrderItemsQuery)) {
                deleteStmt.setInt(1, order.getId());
                deleteStmt.executeUpdate();
            }

            String insertOrderItemQuery = "INSERT INTO order_items (OrderID, ItemID, Qty) VALUES (?, ?, ?)";
            try (PreparedStatement itemStmt = connection.prepareStatement(insertOrderItemQuery)) {
                for (Map.Entry<Integer, Integer> entry : order.getItemQuantities().entrySet()) {
                    itemStmt.setInt(1, order.getId());
                    itemStmt.setInt(2, entry.getKey());
                    itemStmt.setInt(3, entry.getValue());
                    itemStmt.addBatch();
                }
                itemStmt.executeBatch();
            }

            // Commit transaction
            connection.commit();
        } catch (SQLException e) {
            // Rollback transaction on error
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    // Delete an order
    public void deleteOrder(int orderId) throws SQLException {
        String deleteOrderQuery = "DELETE FROM orders WHERE ID = ?";
        String deleteOrderItemsQuery = "DELETE FROM order_items WHERE OrderID = ?";

        // Start transaction
        connection.setAutoCommit(false);
        try (PreparedStatement deleteOrderStmt = connection.prepareStatement(deleteOrderQuery);
             PreparedStatement deleteOrderItemsStmt = connection.prepareStatement(deleteOrderItemsQuery)) {

            deleteOrderItemsStmt.setInt(1, orderId);
            deleteOrderItemsStmt.executeUpdate();

            deleteOrderStmt.setInt(1, orderId);
            deleteOrderStmt.executeUpdate();

            // Commit transaction
            connection.commit();
        } catch (SQLException e) {
            // Rollback transaction on error
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public List<Menu> getMenuItems() throws SQLException {
        List<Menu> menuList = new ArrayList<>();
        String query = "SELECT * FROM products";

        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Menu menu = new Menu();
                menu.setId(rs.getInt("ProductID"));
                menu.setName(rs.getString("Name"));
                menu.setDescription(rs.getString("Description"));
                menu.setPrice(rs.getDouble("Price"));
                menu.setCategory(rs.getString("Category"));
                menu.setImagePath(rs.getString("Image"));
                menuList.add(menu);
            }
        }
        return menuList;
    }

    // Fetch tables
    public List<Table> getTables() throws SQLException {
        List<Table> tableList = new ArrayList<>();
        String query = "SELECT * FROM tables";

        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Table table = new Table();
                table.setId(rs.getInt("ID"));
                table.setStatus(rs.getString("status"));
                table.setCapacity(rs.getInt("capacity"));
                tableList.add(table);
            }
        }
        return tableList;
    }
}
