<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Transaction Form</title>
</head>
<body>
	<h2>Transaction Form</h2>
	<form method="post" action="TransactionControllerServlet">
		<label for="accountId">Account:</label> <input type="text"
			id="accountId" name="accountId" value="12345" readonly><br>
		<br> <label for="category">Category:</label> <select
			id="category" name="category" onchange="toggleFields()">
			<option value="Expense">Expense</option>
			<option value="Income">Income</option>
			<option value="Saving">Saving</option>
		</select><br>
		<br> <label for="amount">Amount:</label> <input type="number"
			id="amount" name="amount" required><br>
		<br> <label for="date">Date:</label> <input type="text" id="date"
			name="date" readonly>
		<script>
			// Set the current date dynamically
			document.getElementById("date").value = new Date().toISOString()
					.split('T')[0];
		</script>


		<label for="desc">Description:</label> <input type="text" id="desc"
			name="desc"><br>
		<br> <label id="paymentMethodLabel" for="paymentMethod">Payment
			Method:</label> <select id="paymentMethod" name="paymentMethod">
			<option value="Cash">Cash</option>
			<option value="Card">Card</option>
		</select><br>
		<br> <label id="sourceLabel" for="source">Source:</label> <select
			id="source" name="source">
			<option value="Salary">Salary</option>
			<option value="Business">Business</option>
		</select><br>
		<br> <label id="goalLabel" for="goal">Goal:</label> <input
			type="text" id="goal" name="goal"><br>
		<br>

		<button type="submit">Register</button>
	</form>

	<script>
		function toggleFields() {
			const category = document.getElementById('category').value;
			document.getElementById('paymentMethodLabel').style.display = (category === 'Expense') ? 'block'
					: 'none';
			document.getElementById('paymentMethod').style.display = (category === 'Expense') ? 'block'
					: 'none';
			document.getElementById('sourceLabel').style.display = (category === 'Income') ? 'block'
					: 'none';
			document.getElementById('source').style.display = (category === 'Income') ? 'block'
					: 'none';
			document.getElementById('goalLabel').style.display = (category === 'Saving') ? 'block'
					: 'none';
			document.getElementById('goal').style.display = (category === 'Saving') ? 'block'
					: 'none';
		}

		// Initialize field visibility
		toggleFields();
	</script>
</body>
</html>
