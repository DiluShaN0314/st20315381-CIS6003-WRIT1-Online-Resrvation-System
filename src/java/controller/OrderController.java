package controller;

import dao.OrderDAO;
import dao.TableDAO;
import model.Order;
import model.Menu;
import model.Table;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.User;

@WebServlet("/OrderController")
public class OrderController extends HttpServlet {

    private OrderDAO orderDAO;
    private TableDAO tableDAO;

    @Override
    public void init() throws ServletException {
        try {
            orderDAO = new OrderDAO();
            tableDAO = new TableDAO();
        } catch (SQLException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "list";
        }
        switch (action) {
            case "new":
                showOrderForm(request, response);
                break;
            case "placeOrder":
        {
            try {
                placeOrder(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                break;
            case "list":
        {
            try {
                listOrders(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
        {
            try {
                deleteOrder(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                break;
            case "update":
         {
            try {
                updateOrder(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                break;
            default:
        {
            try {
                listOrders(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                break;
        }
    }

    private void listOrders(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        int customerId = user.getId();
                
        try {
            List<Table> tableList = tableDAO.getAllTables();
            Map<Integer, Table> tableMap = new HashMap<>();
            for (Table table : tableList) {
                tableMap.put(table.getId(), table);
            }


            List<Order> orderList = orderDAO.getOrdersByCustomerId(customerId);
            request.setAttribute("orderList", orderList);
            request.setAttribute("tableMap", tableMap);
            RequestDispatcher dispatcher = request.getRequestDispatcher("order-list.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("id"));
        
        System.out.println(orderId + " action id: " + request.getParameter("action"));
        try {
            Order order = orderDAO.getOrderById(orderId);
            List<Menu> menuList = orderDAO.getMenuItems();
            List<Table> tableList = orderDAO.getTables();

            request.setAttribute("order", order);
            request.setAttribute("menuList", menuList);
            request.setAttribute("tableList", tableList);

            RequestDispatcher dispatcher = request.getRequestDispatcher("order.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void updateOrder(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, SQLException {
        int orderId = Integer.parseInt(request.getParameter("id"));

        Order order = new Order();
        order.setId(orderId);
        order.setCustomerId(Integer.parseInt(request.getParameter("userID")));
        order.setOrderType(request.getParameter("orderType"));
        order.setTableId("Dine-In".equals(request.getParameter("orderType")) ? 
                          Integer.parseInt(request.getParameter("tableId")) : null);

        Map<Integer, Integer> itemQuantities = new HashMap<>();
        for (String paramName : request.getParameterMap().keySet()) {
            if (paramName.startsWith("quantities[")) {
                int itemId = Integer.parseInt(paramName.substring(11, paramName.length() - 1));
                int quantity = Integer.parseInt(request.getParameter(paramName));
                itemQuantities.put(itemId, quantity);
            }
        }
        order.setItemQuantities(itemQuantities);
        order.setTotal(calculateTotal(itemQuantities));

        orderDAO.updateOrder(order);
        response.sendRedirect("OrderController?action=list&id=" + request.getParameter("userID"));
    }


    private double calculateTotal(Map<Integer, Integer> itemQuantities) throws SQLException {
        double total = 0;
        for (Map.Entry<Integer, Integer> entry : itemQuantities.entrySet()) {
            int itemId = entry.getKey();
            int quantity = entry.getValue();
            double price = orderDAO.getItemPrice(itemId);
            total += quantity * price;
        }
        return total;
    }

    private void deleteOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        int orderId = Integer.parseInt(request.getParameter("id"));

        orderDAO.deleteOrder(orderId);
        response.sendRedirect("OrderController?action=list");
    }

    // Other existing methods...

    private void showOrderForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Fetch menu and table list from DAO
        try {
            List<Menu> menuList = orderDAO.getMenuItems();
            List<Table> tableList = orderDAO.getTables();
            request.setAttribute("menuList", menuList);
            request.setAttribute("tableList", tableList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("order.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void placeOrder(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int customerId = user.getId(); // Use session's user ID

        Map<Integer, Integer> itemQuantities = new HashMap<>();
        for (String paramName : request.getParameterMap().keySet()) {
            if (paramName.startsWith("quantities[")) {
                int itemId = Integer.parseInt(paramName.substring(11, paramName.length() - 1));
                int quantity = Integer.parseInt(request.getParameter(paramName));
                itemQuantities.put(itemId, quantity);
            }
        }

        String orderType = request.getParameter("orderType");
        Integer tableId = null;
        if ("Dine-In".equals(orderType)) {
            tableId = Integer.parseInt(request.getParameter("tableId"));
        }

        double total = 0;
        for (Map.Entry<Integer, Integer> entry : itemQuantities.entrySet()) {
            int itemId = entry.getKey();
            int quantity = entry.getValue();
            double price = orderDAO.getItemPrice(itemId);
            total += quantity * price;
        }

        Order order = new Order();
        order.setCustomerId(customerId);
        order.setItemQuantities(itemQuantities);
        order.setOrderType(orderType);
        order.setTableId(tableId);
        order.setTotal(total);

        orderDAO.addOrder(order);
        response.sendRedirect("OrderController?action=list&id=" + customerId);
    }

}