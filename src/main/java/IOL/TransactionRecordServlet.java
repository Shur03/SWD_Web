package IOL;

import dao.TransactionDAO;
import model.Account;
import model.Transaction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/TransactionRecordServlet")
public class TransactionRecordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TransactionDAO transactionDAO;

    @Override
    public void init() {
        transactionDAO = new TransactionDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account loggedInAccount = (Account) session.getAttribute("loggedInAccount");

        if (loggedInAccount == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // Fetch the last 5 transactions for the logged-in account
            List<Transaction> transactions = transactionDAO.getLastTransactionsByAccountId(loggedInAccount.getAccountId(), 5);

            // Log the fetched transactions for debugging purposes
            System.out.println("Transactions found: " + transactions.size());

            if (transactions.isEmpty()) {
                // Optionally, handle the case where no transactions are found
                System.out.println("No transactions found for account: " + loggedInAccount.getAccountId());
            }

            // Set the transactions in the request scope
            request.setAttribute("transactions", transactions);

            // Forward to the JSP page
            request.getRequestDispatcher("TransactionRecord.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
