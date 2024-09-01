import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Order;
import dao.OrderDAO;
import util.DatabaseConnection;

public class OrderDAOTest {

    private OrderDAO orderDAO;

    @Before
    public void setUp() throws SQLException {
        // Initialize OrderDAO before each test
        orderDAO = new OrderDAO();
        
        // Setup database with initial test data if necessary
        // e.g., create test orders, menu items, etc.
    }

    @After
    public void tearDown() throws SQLException {
        // Clean up resources and reset database if necessary
        // e.g., delete test data
        DatabaseConnection.getInstance().getConnection().rollback();
    }

    @Test
    public void testAddOrder() throws SQLException {
        // Create a new order object
        Order order = new Order();
        order.setCustomerId(1);
        order.setOrderType("Dine-In");
        order.setTableId(5);
        order.setTotal(100.0);
        
        // Create a HashMap for item quantities
        Map<Integer, Integer> itemQuantities = new HashMap<>();
        itemQuantities.put(1, 2);
        itemQuantities.put(2, 3);
        order.setItemQuantities(itemQuantities);

        // Add the order to the database
        orderDAO.addOrder(order);

        // Fetch the order back from the database
        Order fetchedOrder = orderDAO.getOrderById(order.getId());

        // Assert that the fetched order matches the added order
        assertNotNull(fetchedOrder);
        assertEquals(order.getCustomerId(), fetchedOrder.getCustomerId());
        assertEquals(order.getOrderType(), fetchedOrder.getOrderType());
        assertEquals(order.getTableId(), fetchedOrder.getTableId());
        assertEquals(order.getTotal(), fetchedOrder.getTotal(), 0.01);
        assertEquals(order.getItemQuantities(), fetchedOrder.getItemQuantities());
    }

    @Test
    public void testUpdateOrder() throws SQLException {
        // Create a new order object
        Order order = new Order();
        order.setCustomerId(1);
        order.setOrderType("Take-Away");
        order.setTableId(null);
        order.setTotal(50.0);
        
        // Create a HashMap for item quantities
        Map<Integer, Integer> itemQuantities = new HashMap<>();
        itemQuantities.put(1, 1);
        order.setItemQuantities(itemQuantities);

        // Add the order to the database
        orderDAO.addOrder(order);

        // Modify the order
        order.setOrderType("Dine-In");
        order.setTableId(10);
        order.setTotal(75.0);
        
        // Update item quantities
        itemQuantities = new HashMap<>();
        itemQuantities.put(1, 2);
        itemQuantities.put(2, 1);
        order.setItemQuantities(itemQuantities);

        // Update the order
        orderDAO.updateOrder(order);

        // Fetch the order back from the database
        Order fetchedOrder = orderDAO.getOrderById(order.getId());

        // Assert that the fetched order matches the updated order
        assertNotNull(fetchedOrder);
        assertEquals(order.getOrderType(), fetchedOrder.getOrderType());
        assertEquals(order.getTableId(), fetchedOrder.getTableId());
        assertEquals(order.getTotal(), fetchedOrder.getTotal(), 0.01);
        assertEquals(order.getItemQuantities(), fetchedOrder.getItemQuantities());
    }

    @Test
    public void testDeleteOrder() throws SQLException {
        // Create a new order object
        Order order = new Order();
        order.setCustomerId(1);
        order.setOrderType("Dine-In");
        order.setTableId(5);
        order.setTotal(100.0);
        
        // Create a HashMap for item quantities
        Map<Integer, Integer> itemQuantities = new HashMap<>();
        itemQuantities.put(1, 2);
        itemQuantities.put(2, 3);
        order.setItemQuantities(itemQuantities);

        // Add the order to the database
        orderDAO.addOrder(order);

        // Delete the order
        orderDAO.deleteOrder(order.getId());

        // Fetch the order back from the database
        Order fetchedOrder = orderDAO.getOrderById(order.getId());

        // Assert that the order was deleted
        assertNull(fetchedOrder);
    }

    @Test
    public void testGetOrdersByCustomerId() throws SQLException {
        // Create and add an order for a customer
        Order order = new Order();
        order.setCustomerId(1);
        order.setOrderType("Dine-In");
        order.setTableId(5);
        order.setTotal(100.0);
        
        // Create a HashMap for item quantities
        Map<Integer, Integer> itemQuantities = new HashMap<>();
        itemQuantities.put(1, 2);
        itemQuantities.put(2, 3);
        order.setItemQuantities(itemQuantities);
        orderDAO.addOrder(order);

        // Fetch orders by customer ID
        List<Order> orders = orderDAO.getOrdersByCustomerId(1);

        // Assert that the orders list is not empty and contains the added order
        assertNotNull(orders);
        assertFalse(orders.isEmpty());
        assertEquals(1, orders.size());
        assertEquals(order.getId(), orders.get(0).getId());
    }
}
