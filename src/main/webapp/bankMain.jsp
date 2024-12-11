<%@ page import="model.Customer"%>
<%@ page import="model.Account"%>
<%@ page import="model.Transaction"%>
<%@ page import="dao.TransactionDAO"%>
<%@ page import="java.util.List"%>
<%
// Simulate a Customer object (replace this with actual session or request data)
Customer customer = (Customer) session.getAttribute("customer");
if (customer == null || customer.getAccount() == null) {
	response.sendRedirect("login.jsp"); // Redirect to login if no customer is found
	return;
}
//Account account = customer.getAccount();
Account loggedInAccount = (Account) session.getAttribute("loggedInAccount");
TransactionDAO dao = new TransactionDAO();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Bank System</title>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"
	rel="stylesheet">
	<link href="css/bankMain.css" rel="stylesheet">
</head>
<body>
	<!-- Header Section -->
	<div class="header">
		<img src="img/son.jpg" alt="User Icon">
		<h1>
			Welcome,
			<%=customer.getUserName()%></h1>
		<div class="account-info">
			<p>
				<strong>Account ID:</strong> <span><%=loggedInAccount.getAccountId()%></span>
			</p>
			<p>
				<strong>Balance:</strong> $<span id="balance"><%=loggedInAccount.getBalance()%></span>
			</p>
		</div>
	</div>

	<!-- Main Content -->
	<div class="main-content">
		<div class="record">
			<%
			List<Transaction> transactions = dao.getLastTransactionsByAccountId(loggedInAccount.getAccountId(), 2);
			if (transactions != null && !transactions.isEmpty()) {
				for (Transaction t : transactions) {
			%>
			<div class="transaction-item">
				<p>
					<strong>Date:</strong>
					<%=t.getTransDate()%></p>
				<p>
					<strong>Category:</strong>
					<%=t.getCategory()%></p>
				<p>
					<strong>Amount:</strong> $<%=t.getAmount()%></p>
				<p>
					<strong>Description:</strong>
					<%=t.getDesc()%></p>
				<hr />
			</div>
			<%
			}
			} else {
			%>
			<p>No recent transactions found.</p>
			<%
			}
			%>
		</div>

		<div class="grid-buttons">
			<a href="TransactionView.jsp"><i class="fa-solid fa-right-left"></i></a>
		</div>
	</div>

	<!-- Footer Section -->
	<div class="footer">
		<p>&copy; Shur Yeruult</p>
	</div>
</body>
</html>
