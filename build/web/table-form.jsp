<%@ page import="model.Table" %>
<%@ page import="controller.TableController" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Table Form</title>
    <link rel="stylesheet" type="text/css" href="css/table-form.css">
</head>
<body>
    <h2>Table Form</h2>
    <form action="TableController" method="post">
        <input type="hidden" name="id" value="<%= request.getAttribute("table") != null ? ((Table) request.getAttribute("table")).getId() : "" %>">
        <label>Capacity:</label>
        <input type="number" name="capacity" value="<%= request.getAttribute("table") != null ? ((Table) request.getAttribute("table")).getCapacity() : "" %>" required>
        <br>
        <label>Status:</label>
        <input type="text" name="status" value="<%= request.getAttribute("table") != null ? ((Table) request.getAttribute("table")).getStatus() : "" %>" required>
        <br>
        <input type="submit" name="action" value="<%= request.getAttribute("table") != null ? "update" : "insert" %>">
    </form>
    <a href="TableController">Back to List</a>
</body>
</html>
