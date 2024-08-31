package controller;

import dao.UserDAO;
import model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.http.HttpSession;

@WebServlet("/SignInController")
public class SignInController extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        try {
            userDAO = new UserDAO();
        } catch (SQLException e) {
            throw new ServletException("Database connection problem.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            User user = userDAO.getUserByUsername(username);

            if (user != null && user.getPassword().equals(password)) {
                // Successful login
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                System.out.println("user role :" + user.getRoleId());
                if(user.getRoleId() != 3) {
                    response.sendRedirect("index.jsp"); // Redirect to a welcome page or dashboard
                } else {
                    response.sendRedirect("CustomerHome.jsp"); // Redirect to a welcome page or dashboard
                }
            } else {
                // Login failed
                request.setAttribute("errorMessage", "Invalid username or password.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("sign-in.jsp");
                dispatcher.forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Optionally handle GET requests (e.g., redirect to the login form)
        RequestDispatcher dispatcher = request.getRequestDispatcher("sign-in.jsp");
        dispatcher.forward(request, response);
    }
    
    private User authenticateUser(String username, String password) {
        // Implement your authentication logic here
        // For example, query the database for the user
        // Return a User object if authentication is successful, otherwise return null
        return null;
    }
}
