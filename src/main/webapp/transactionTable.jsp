<%@ page import="java.util.List" %>
<%@ page import="model.Transaction" %>
<%
    List<Transaction> transactions = (List<Transaction>) request.getAttribute("transactions");
    if (transactions != null && !transactions.isEmpty()) {
%>
<table>
    <tr>
        <th>ID</th>
        <th>Date</th>
        <th>Amount</th>
        <th>Category</th>
        <th>Description</th>
    </tr>
    <%
        for (Transaction transaction : transactions) {
    %>
    <tr>
        <td><%= transaction.getId() %></td>
        <td><%= transaction.getTransDate() %></td>
        <td><%= transaction.getAmount() %></td>
        <td><%= transaction.getCategory() %></td>
        <td><%= transaction.getDesc() %></td>
    </tr>
    <%
        }
    %>
</table>
<%
    } else {
%>
    <p>No transactions found for this account.</p>
<%
    }
%>
