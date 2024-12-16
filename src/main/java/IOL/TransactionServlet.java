package IOL;

import model.Account;
import model.Expense;
import model.Income;
import model.Saving;
import model.Transaction;
import javax.servlet.annotation.WebServlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import controller.TransactionController;
import enums.PaymentMethod;
import enums.Source;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/TransactionServlet")
public class TransactionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TransactionController transactionController;

    @Override
    public void init() throws jakarta.servlet.ServletException {
        super.init();
        transactionController = new TransactionController(new model.TransactionModel());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, jakarta.servlet.ServletException {
        try {
            // Retrieve the logged-in account from the session
            HttpSession session = request.getSession(false); // Get the existing session, if available
            if (session == null || session.getAttribute("loggedInAccount") == null) {
                throw new IllegalStateException("No logged-in account found. Please log in.");
            }

            Account loggedInAccount = (Account) session.getAttribute("loggedInAccount");

            // Transaction details from the request
            String category = request.getParameter("category");
            if (category == null || category.isEmpty()) {
                throw new IllegalArgumentException("Transaction category is required.");
            }
            double amount = Double.parseDouble(request.getParameter("amount"));
            if (amount <= 0) { // Check for invalid amounts
                request.setAttribute("errorMessage", "Amount must be greater than zero.");
                request.getRequestDispatcher("/TransactionView.jsp").forward(request, response);
                return; // Stop further processing
            }
//             amount = Double.parseDouble(request.getParameter("amount"));
            String description = request.getParameter("description");
            String transactionDateString = request.getParameter("transactionDate");
            LocalDate transactionDate = LocalDate.parse(transactionDateString);

            // Use the logged-in account
            Transaction transaction;
            switch (category) {
                case "Income": {
                    String sourceStr = request.getParameter("source");
                    Source source = Source.valueOf(sourceStr);
                    double newBalance = loggedInAccount.getBalance() + amount;
                    if (newBalance < 0) {
                        throw new IllegalArgumentException("Insufficient balance for this transaction.");
                    }
                    loggedInAccount.setBalance(newBalance); // Update the account balance
                    transaction = new Income(category, amount, transactionDate, description, loggedInAccount, source);
                    break;
                }
                case "Expense": {
                    String paymentMethodStr = request.getParameter("paymentMethod");
                    PaymentMethod method = PaymentMethod.valueOf(paymentMethodStr);
                    double newBalance = loggedInAccount.getBalance() - amount;
                    if (newBalance < 0) {
                        throw new IllegalArgumentException("Insufficient balance for this transaction.");
                    }
                    loggedInAccount.setBalance(newBalance); // Update the account balance
                    transaction = new Expense(category, amount, transactionDate, description, loggedInAccount, method);
                    break;
                }
                case "Saving": {
                    String goal = request.getParameter("goal");
                    double newBalance = loggedInAccount.getBalance() + amount;
                    if (newBalance < 0) {
                        throw new IllegalArgumentException("Insufficient balance for this transaction.");
                    }
                    loggedInAccount.setBalance(newBalance); // Update the account balance
                    transaction = new Saving(category, amount, transactionDate, description, loggedInAccount, goal);
                    break;
                }
                default:
                    throw new IllegalArgumentException("Invalid transaction category.");
            }

            // Register the transaction
            String result = transactionController.registerTrans(transaction);

            // Pass result back to the JSP page
            request.setAttribute("result", result);
            request.getRequestDispatcher("/bankMain.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new jakarta.servlet.ServletException("Error processing transaction: " + e.getMessage(), e);
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/TransactionForm.jsp").forward(request, response);
    }
}
