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
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TransactionDAO transactionDAO;

    @Override
    public void init() {
        transactionDAO = new TransactionDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account"); // Assuming the account is stored in session

        if (account == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            List<Transaction> transactions = transactionDAO.getTransactionsByAccountId(account.getAccountId());
            request.setAttribute("transactions", transactions);
            request.getRequestDispatcher("TransactionRecord.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
//            response.sendRedirect("error.jsp");
        }
    }
}
