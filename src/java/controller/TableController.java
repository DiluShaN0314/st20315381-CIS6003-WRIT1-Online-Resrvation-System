package controller;

import dao.TableDAO;
import model.Table;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/TableController")
public class TableController extends HttpServlet {

    private TableDAO tableDAO;

    public void init() throws ServletException {
        try {
            tableDAO = new TableDAO();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Cannot initialize TableDAO", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }
        try {
            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "insert":
                    insertTable(request, response);
                    break;
                case "delete":
                    deleteTable(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "update":
                    updateTable(request, response);
                    break;
                default:
                    listTable(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listTable(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Table> listTable = tableDAO.getAllTables();
        request.setAttribute("listTable", listTable);
        RequestDispatcher dispatcher = request.getRequestDispatcher("table-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("table-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Table existingTable = tableDAO.getTable(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("table-form.jsp");
        request.setAttribute("table", existingTable);
        dispatcher.forward(request, response);
    }

    private void insertTable(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int capacity = Integer.parseInt(request.getParameter("capacity"));
        String status = request.getParameter("status");
        Table newTable = new Table();
        newTable.setCapacity(capacity);
        newTable.setStatus(status);
        tableDAO.createTable(newTable);
        response.sendRedirect("TableController?action=list");
    }

    private void updateTable(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int capacity = Integer.parseInt(request.getParameter("capacity"));
        String status = request.getParameter("status");

        Table table = new Table();
        table.setId(id);
        table.setCapacity(capacity);
        table.setStatus(status);
        tableDAO.updateTable(table);
        response.sendRedirect("TableController?action=list");
    }

    private void deleteTable(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        tableDAO.deleteTable(id);
        response.sendRedirect("TableController?action=list");
    }
}
