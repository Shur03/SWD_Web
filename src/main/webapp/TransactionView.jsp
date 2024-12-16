<%@ page import="model.Account"%>
<%@ page import="model.Customer"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
// Retrieve the Account object from session
Customer customer = (Customer) session.getAttribute("customer");
if (customer == null || customer.getAccount() == null) {
    response.sendRedirect("login.jsp"); // Redirect to login if no customer or account is found
    return;
}
Account account = customer.getAccount();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transaction Form</title>
    <link href="css/transView.css" rel="stylesheet">
    <script>
        function toggleFields() {
            const category = document.getElementById('category').value;
            const paymentMethodField = document.getElementById('paymentMethodField');
            const sourceField = document.getElementById('sourceField');
            const goalField = document.getElementById('goalField');

            // Reset visibility based on the selected transaction type
            if (category === 'Income') {
                paymentMethodField.style.display = 'none';
                sourceField.style.display = 'block';
                goalField.style.display = 'none';
            } else if (category === 'Expense') {
                paymentMethodField.style.display = 'block';
                sourceField.style.display = 'none';
                goalField.style.display = 'none';
            } else if (category === 'Saving') {
                paymentMethodField.style.display = 'none';
                sourceField.style.display = 'block';
                goalField.style.display = 'block';
            }
        }

        // Initialize the form
        window.onload = function() {
            toggleFields(); // Adjust visibility based on selected type

            // Set the transaction date field to today's date by default
            const transactionDateInput = document.getElementById("transactionDate");
            const today = new Date();
            const yyyy = today.getFullYear();
            const mm = String(today.getMonth() + 1).padStart(2, '0');
            const dd = String(today.getDate()).padStart(2, '0');
            transactionDateInput.value = `${yyyy}-${mm}-${dd}`;

            // Add client-side validation for amount
            document.querySelector("form").addEventListener("submit", function(event) {
                const amount = parseFloat(document.getElementById("amount").value);
                if (amount <= 0) {
                    alert("Error: Amount must be greater than 0.");
                    event.preventDefault(); // Prevent form submission
                }
            });
        };
    </script>
</head>
<body>
    <div class="form-container">
        <h1>Make a Transaction</h1>

        <!-- Display server-side error message, if any -->
        <% if (request.getAttribute("error") != null) { %>
            <p class="error" style="color: red;"><%= request.getAttribute("error") %></p>
        <% } %>

        <form action="TransactionServlet" method="POST">
            <!-- Hidden field for Account ID -->
            <input type="hidden" name="accountId" value="<%=account.getAccountId()%>">

            <!-- Transaction Type -->
            <div class="form-group">
                <label for="category">Transaction Type:</label>
                <select id="category" name="category" onchange="toggleFields()" required>
                    <option value="Expense">Expense</option>
                    <option value="Income">Income</option>
                    <option value="Saving">Saving</option>
                </select>
            </div>

            <!-- Amount -->
            <div class="form-group">
                <label for="amount">Amount:</label>
                <input type="number" id="amount" name="amount" step="0.01" required>
            </div>

            <!-- Description -->
            <div class="form-group">
                <label for="description">Description:</label>
                <input type="text" id="description" name="description" required>
            </div>

            <!-- Transaction Date -->
            <div class="form-group">
                <label for="transactionDate">Transaction Date:</label>
                <input type="date" id="transactionDate" name="transactionDate" required>
            </div>

            <!-- Payment Method (Visible only for 'Expense') -->
            <div class="form-group" id="paymentMethodField" style="display: none;">
                <label for="paymentMethod">Payment Method:</label>
                <select id="paymentMethod" name="paymentMethod">
                    <option value="CARD">CARD</option>
                    <option value="CASH">CASH</option>
                </select>
            </div>

            <!-- Source (Visible only for 'Income' or 'Saving') -->
            <div class="form-group" id="sourceField" style="display: none;">
                <label for="source">Source:</label>
                <select id="source" name="source">
                    <option value="SALARY">SALARY</option>
                    <option value="FREELANCE">FREELANCE</option>
                </select>
            </div>

            <!-- Goal (Visible only for 'Saving') -->
            <div class="form-group" id="goalField" style="display: none;">
                <label for="goal">Goal:</label>
                <input type="text" id="goal" name="goal">
            </div>

            <button type="submit" class="btn">Submit</button>
        </form>
    </div>
</body>
</html>
