<%@ page import="model.Menu" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Menu Form</title>
    <link rel="stylesheet" type="text/css" href="css/menu-form.css">
</head>
<body>
    <h2>Menu Form</h2>
    <form action="MenuController" method="post" enctype="multipart/form-data">
         <%-- Display the current image if available --%>
        <%
        Menu menu = (Menu) request.getAttribute("menu");
        if (menu != null && menu.getImagePath() != null && !menu.getImagePath().isEmpty()) {
            String imagePath = "uploads/" + menu.getImagePath();
        %>
            <div class="image-container">
                <img src="<%= imagePath %>" alt="Image" style="max-width: 300px; max-height: 300px;" class="menu-image" />
            </div>
            
        <%
        }
        %>
        <br>
        <input type="hidden" name="id" value="<%= request.getAttribute("menu") != null ? ((Menu) request.getAttribute("menu")).getId() : "" %>">
        <label>Name:</label>
        <input type="text" name="name" value="<%= request.getAttribute("menu") != null ? ((Menu) request.getAttribute("menu")).getName() : "" %>" required>
        <br>
        <label>Description:</label>
        <textarea name="description" rows="4" required><%= request.getAttribute("menu") != null ? ((Menu) request.getAttribute("menu")).getDescription() : "" %></textarea>
        <br>
        <label>Price:</label>
        <input type="number" step="0.01" name="price" value="<%= request.getAttribute("menu") != null ? ((Menu) request.getAttribute("menu")).getPrice() : "" %>" required>
        <br>
        <label>Category</label>
        <input type="text"  name="category" value="<%= request.getAttribute("menu") != null ? ((Menu) request.getAttribute("menu")).getCategory() : "" %>" required>
        <br>
        <label>Image Upload</label>
        <input type = "file" name = "file" size = "50" accept="image/*" /><br>
        <input type="submit" name="action" value="<%= request.getAttribute("menu") != null ? "update" : "insert" %>">
    </form>
    <a href="MenuController?action=list">Back to List</a>
</body>
</html>
