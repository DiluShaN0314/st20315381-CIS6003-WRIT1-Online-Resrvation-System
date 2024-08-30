<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Registration</title>
    <link rel="stylesheet" href="css/user-form.css">
</head>
<body>
    <h2>Create a New Account</h2>
    <form action="RegisterController" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="<%= request.getAttribute("user") != null ? ((User) request.getAttribute("user")).getId() : "" %>">
        
        <label>Name:</label>
        <input type="text" name="name" value="<%= request.getAttribute("user") != null ? ((User) request.getAttribute("user")).getName() : "" %>" required>
        <br>
        
        <label for="username">Username:</label>
        <input type="text" name="username" value="<%= request.getAttribute("user") != null ? ((User) request.getAttribute("user")).getUsername() : "" %>" required><br>

        <label for="email">Email:</label>
        <input type="email" name="email" value="<%= request.getAttribute("user") != null ? ((User) request.getAttribute("user")).getEmail() : "" %>" required>

        <label for="password">Password:</label>
        <input type="password" name="password" value="<%= request.getAttribute("user") != null ? ((User) request.getAttribute("user")).getPassword() : "" %>" required><br>
        
        <label for="contactInfo">Contact Info:</label>
        <input type="text" name="contactInfo" value="<%= request.getAttribute("user") != null ? ((User) request.getAttribute("user")).getContactInfo() : "" %>"><br>

        <label>Image Upload</label>
        <input type = "file" name = "file" size = "50" accept="image/*" /><br>
        <br>
        
        <input type="submit" name="action" value="Register">

        <c:if test="${not empty errorMessage}">
            <div style="color: red; text-align: center; margin-top: 10px;">
                ${errorMessage}
            </div>
        </c:if>
    </form>
</body>
</html>
