<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Role" %>
<%@ page import="model.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Form</title>
    <link rel="stylesheet" type="text/css" href="css/user-form.css">
</head>
<body>
    <h2>User Form</h2>
    <form action="UserController" method="post" enctype="multipart/form-data">
        <%-- Display the current image if available --%>
        <%
        User user = (User) request.getAttribute("user");
        if (user != null && user.getImagePath() != null && !user.getImagePath().isEmpty()) {
            String imagePath = "uploads/" + user.getImagePath();
        %>
            <div class="image-container">
                <img src="<%= imagePath %>" alt="Image" style="max-width: 300px; max-height: 300px;" class="menu-image" />
            </div>
            
        <%
        }
        %>
        <br>
        <input type="hidden" name="id" value="<%= request.getAttribute("user") != null ? ((User) request.getAttribute("user")).getId() : "" %>">
        
        <label for="username">Username:</label>
        <input type="text" name="username" value="<%= request.getAttribute("user") != null ? ((User) request.getAttribute("user")).getUsername() : "" %>" required><br>

        <label for="password">Password:</label>
        <input type="password" name="password" value="<%= request.getAttribute("user") != null ? ((User) request.getAttribute("user")).getPassword() : "" %>" required><br>
        
        <label>Name:</label>
        <input type="text" name="name" value="<%= request.getAttribute("user") != null ? ((User) request.getAttribute("user")).getName() : "" %>" required>
        <br>
        
        <label>Email:</label>
        <input type="email" name="email" value="<%= request.getAttribute("user") != null ? ((User) request.getAttribute("user")).getEmail() : "" %>" required>
        <br>
        
        <label for="contactInfo">Contact Info:</label>
        <input type="text" name="contactInfo" value="<%= request.getAttribute("user") != null ? ((User) request.getAttribute("user")).getContactInfo() : "" %>"><br>
        
        <label>Role:</label>
        <select name="role" required onchange=console.log(this.value);>
            <option value="">Select a role</option>
            <% 
            List<Role> roles = (List<Role>) request.getAttribute("roles"); 
            if (roles != null) {
                //User user = (User) request.getAttribute("user");
                int userRoleID = (user != null) ? user.getRoleId() : -1; // Extract role as int
                for (Role role : roles) { 
            %>
                <option value="<%= role.getRoleID() %>" <%= (role.getRoleID() == userRoleID) ? "selected" : "" %>>
                    <%= role.getRoleName() %>
                </option>
            <% 
                } 
            } 
            %>
        </select>
        <br>
        
        <label>Image Upload</label>
        <input type = "file" name = "file" size = "50" accept="image/*" /><br>
        <br>
        
        <input type="submit" name="action" value="<%= request.getAttribute("user") != null ? "Update" : "Insert" %>">
        
        <c:if test="${not empty errorMessage}">
            <div style="color: red; text-align: center; margin-top: 10px;">
                ${errorMessage}
            </div>
        </c:if>
    </form>
    
        <%
            if (user != null && user.getRoleId() != 3) {
        %>
            <a href="UserController">Back to List</a>
    
        <%
            }
        %>
</body>
</html>