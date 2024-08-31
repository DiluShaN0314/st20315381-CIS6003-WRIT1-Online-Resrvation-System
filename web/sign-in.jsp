<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Sign In</title>
    <link rel="stylesheet" href="css/sign-in.css">
</head>
<body>
    <h2>Sign In</h2>
    <form action="SignInController" method="post">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>

        <input type="submit" value="Sign In">

        <c:if test="${not empty errorMessage}">
            <div style="color: red; text-align: center; margin-top: 10px;">
                ${errorMessage}
            </div>
        </c:if>
    </form>
            
            
    <div style="text-align: center; margin-top: 20px;">
        <p>Don't have an account? <a href="register.jsp">Create a new account</a></p>
    </div>
</body>
</html>
