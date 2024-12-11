<%@ page import="model.Customer" %>
<%@ page import="model.Account" %>
<%
    // Simulate a Customer object (replace this with actual session or request data)
    Customer customer = (Customer) session.getAttribute("customer");
    if (customer == null || customer.getAccount() == null) {
        response.sendRedirect("login.jsp"); // Redirect to login if no customer is found
        return;
    }
    Account account = customer.getAccount();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bank System</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f7f9fc;
            color: #333;
        }
        .header {
            background-color: #4cafef;
            color: white;
            padding: 20px;
            display: flex;
            align-items: center;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        .header img {
            width: 60px;
            height: 60px;
            margin-right: 15px;
            border-radius: 50%;
            border: 2px solid white;
        }
        .header h1 {
            font-size: 24px;
            margin: 0;
        }
        .main-content {
            padding: 30px;
            max-width: 800px;
            margin: 20px auto;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .main-content h2 {
            font-size: 22px;
            margin-bottom: 15px;
            color: #555;
        }
        .main-content p {
            font-size: 18px;
            margin: 10px 0;
        }
        .main-content span {
            font-weight: bold;
            color: #4cafef;
        }
        .footer {
            text-align: center;
            padding: 20px;
            background-color: #f1f1f1;
            margin-top: 30px;
            border-top: 1px solid #ddd;
        }
        .btn {
            display: inline-block;
            padding: 12px 25px;
            margin: 10px;
            background-color: #4cafef;
            color: white;
            text-decoration: none;
            font-size: 16px;
            font-weight: bold;
            border-radius: 5px;
            transition: background-color 0.3s ease, transform 0.2s ease;
        }
        .btn:hover {
            background-color: #357abc;
            transform: scale(1.05);
        }
        @media (max-width: 600px) {
            .main-content, .footer {
                padding: 20px;
            }
            .header img {
                width: 50px;
                height: 50px;
            }
            .header h1 {
                font-size: 20px;
            }
        }
    </style>
</head>
<body>
    <!-- Header Section -->
    <div class="header">
        <img src="img/son.jpg" alt="User Icon">
        <h1>Welcome, <%= customer.getUserName() %></h1>
    </div>

    <!-- Main Content -->
    <div class="main-content">
        <h2>Account Information</h2>
        <p><strong>Account ID:</strong> <span><%= account.getAccountId() %></span></p>
        <p><strong>Balance:</strong> $<span id="balance"><%= account.getBalance() %></span></p>
    </div>

    <!-- Footer Section -->
    <div class="footer">
        <a href="TransactionForm.jsp" class="btn">Make a Transaction</a>
        <a href="TransactionRecordServlet" class="btn">View Transactions</a>
    </div>

    <!-- Optional Script for Dynamic Updates -->
    <script>
        function updateBalance(newBalance) {
            document.getElementById('balance').textContent = newBalance;
        }
    </script>
</body>
</html>
