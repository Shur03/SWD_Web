<%@ page import="model.Account"%>
<%@ page import="model.Customer"%>
<%@ page import="enums.PaymentMethod"%>
<%@ page import="enums.Source"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
// Retrieve the Account object from session
Customer customer = (Customer) session.getAttribute("customer");
if (customer == null || customer.getAccount() == null) {
	response.sendRedirect("login.jsp"); // Redirect to login if no customer or account is found
	return;
}
Account account = customer.getAccount();
String selectedType = request.getParameter("type"); // Get selected type if the form is being re-rendered
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Transaction Form</title>
<style>
body {
	font-family: Arial, sans-serif;
	margin: 0;
	padding: 20px;
	background-color: #f4f4f9;
}

.form-container {
	max-width: 400px;
	margin: 0 auto;
	padding: 20px;
	background: #fff;
	border-radius: 8px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.form-container h1 {
	margin-bottom: 20px;
}

.form-group {
	margin-bottom: 15px;
}

.form-group label {
	display: block;
	margin-bottom: 5px;
}

.form-group input, .form-group select {
	width: 100%;
	padding: 10px;
	box-sizing: border-box;
}

.btn {
	display: block;
	width: 100%;
	padding: 10px;
	background: #007bff;
	color: #fff;
	text-align: center;
	border: none;
	border-radius: 5px;
	cursor: pointer;
}

.btn:hover {
	background: #0056b3;
}

/* Hide fields based on transaction type */
#paymentMethodField, #sourceField, #goalField {
	display: none;
}
</style>
<script>
	function toggleFields() {
		const category = document.getElementById('category').value;
		const paymentMethodField = document
				.getElementById('paymentMethodField');
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

	// Call toggleFields on page load to adjust visibility based on selected type
	window.onload = function() {
		toggleFields();
		// Set the transaction date field to today's date by default
		const transactionDateInput = document.getElementById("transactionDate");
		const today = new Date();
		const yyyy = today.getFullYear();
		const mm = String(today.getMonth() + 1).padStart(2, '0'); // Months are zero-based
		const dd = String(today.getDate()).padStart(2, '0');
		transactionDateInput.value = `${yyyy}-${mm}-${dd}`;
	};
</script>
</head>
<body>
	<div class="form-container">
		<h1>Make a Transaction</h1>
		<form action="TransactionServlet" method="POST">
			<!-- Hidden field for Account ID -->
			<input type="hidden" name="accountId"
				value="<%=account.getAccountId()%>">

			<!-- Transaction Type -->
			<div class="form-group">
				<label for="category">Transaction Type:</label> <select
					id="category" name="category" onchange="toggleFields()" required>
					s
					<option value="Expense">Expense</option>
					<option value="Income">Income</option>
					<option value="Saving">Saving</option>
				</select>

			</div>

			<!-- Amount -->
			<div class="form-group">
				<label for="amount">Amount:</label> <input type="number" id="amount"
					name="amount" step="0.01" required>
			</div>

			<!-- Description -->
			<div class="form-group">
				<label for="description">Description:</label> <input type="text"
					id="description" name="description" required>
			</div>

			<!-- Transaction Date -->
			<div class="form-group">
				<label for="transactionDate">Transaction Date:</label> <input
					type="date" id="transactionDate" name="transactionDate" required>
			</div>

			<!-- Payment Method (Visible only for 'Expense') -->
			<div class="form-group" id="paymentMethodField">
				<label for="paymentMethod">Payment Method:</label>
				<select id="paymentMethod"
					name="paymentMethod">
					<option value="CARD">CARD</option>
					<option value="CASH">CASH</option>
				</select> 
			</div>

			<!-- Source (Visible only for 'Income' or 'Saving') -->
			<div class="form-group" id="sourceField">
				<label for="source">Source:</label> 
				<select
					id="source" name="source">
					<option value="SALARY">SALARY</option>
					<option value="FREELANCE">FREELANCE</option>
				</select>
			</div>

			<!-- Goal (Visible only for 'Saving') -->
			<div class="form-group" id="goalField">
				<label for="goal">Goal:</label> <input type="text" id="goal"
					name="goal">
			</div>

			<button type="submit" class="btn">Submit</button>
		</form>
	</div>
</body>
</html>
