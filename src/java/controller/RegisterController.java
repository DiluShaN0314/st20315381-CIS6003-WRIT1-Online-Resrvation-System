/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author dell
 */
import dao.UserDAO;
import java.io.File;
import java.io.FileOutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import model.User;

@WebServlet("/RegisterController")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class RegisterController extends HttpServlet {
    private UserDAO userDAO;
    private static final String UPLOAD_DIR = "uploads";
    
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
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
    
        if (action == null) {
            response.sendRedirect("sign-in.jsp");
            return;
        }
        
        try {
            switch (action) {
                case "Register":
                    createCustomer(request, response);
                    break;
                default:
                    response.sendRedirect("sign-in.jsp");
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException("Database error", ex);
        }
    }
    
    private void createCustomer(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String contactInfo = request.getParameter("contactInfo");
        String name = request.getParameter("name");
        
        // Get the upload directory
        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;

        System.out.println("Application Path: " + applicationPath);
        System.out.println("Upload File Path: " + uploadFilePath);
        // Create the directory if it doesn't exist
        File uploadDir = new File(uploadFilePath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Get the file part from the request
        Part filePart = request.getPart("file");
        String fileName = extractFileName(filePart);
        // Ensure the file name is valid
        if (fileName != null && !fileName.isEmpty()) {
            // Prepare the file path where the file will be saved
            String filePath = uploadFilePath + File.separator + fileName;

        System.out.println("File Path: " + filePath);
            // Save the file manually
            try (InputStream inputStream = filePart.getInputStream();
                 FileOutputStream outputStream = new FileOutputStream(filePath)) {

                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                response.getWriter().println("File uploaded successfully! Saved as " + fileName);
            } catch (IOException e) {
                response.getWriter().println("File upload failed: " + e.getMessage());
            }
        } else {
            response.getWriter().println("File upload failed: invalid file name.");
        }
        
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        user.setImagePath(fileName);
        user.setContactInfo(contactInfo);
        user.setRoleId(3);  // Set default role id as 3 (Customer)

        try {
            userDAO.createUser(user);
            response.sendRedirect("sign-in.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Registration failed! Please try again." + e.getMessage());
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
    
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }
}
