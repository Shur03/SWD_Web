<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bank Login</title>
    <link href="css/login.css" rel="stylesheet">
</head>
<body>
    <div class="login-container">
        <h2>Login</h2>
        <form action="BankLoginServlet" method="POST">
            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" id="username" name="username" required />
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required />
            </div>
            <button type="submit" class="btn">Login</button>
        </form>

        <!-- Display messages -->
        <div class="message">
            <c:if test="${not empty message}">
                <span class="${messageType}">${message}</span>
            </c:if>
        </div>
    </div>
</body>
</html>
