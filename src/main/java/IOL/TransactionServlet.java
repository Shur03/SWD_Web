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
            String category = request.getParameter("category");
            if (category == null || category.isEmpty()) {
                throw new IllegalArgumentException("Transaction category is required.");
            }

            String accountId = request.getParameter("accountId");
            double amount = Double.parseDouble(request.getParameter("amount"));
            String description = request.getParameter("description");
            String transactionDateString = request.getParameter("transactionDate");
            LocalDate transactionDate = LocalDate.parse(transactionDateString);

            // Fetch the account and its current balance
            Account account = transactionController.model.getAccountById(accountId); // Assume this method exists in the controller
            if (account == null) {
                throw new IllegalArgumentException("Account not found with ID: " + accountId);
            }
            double currentBalance = account.getBalance();
            System.out.println(currentBalance);// Retrieve current balance

            // Create transaction object
            Transaction transaction;
            switch (category) {
                case "Income": {
                    String sourceStr = request.getParameter("source");
                    Source source = Source.valueOf(sourceStr);
                    currentBalance += amount; 
                    // Add amount to the balance
                    transaction = new Income(category, currentBalance, transactionDate, description, account, source);
                    break;
                }
                case "Expense": {
                    String paymentMethodStr = request.getParameter("paymentMethod");
                    PaymentMethod method = PaymentMethod.valueOf(paymentMethodStr);
                    currentBalance -= amount; // Subtract amount from the balance
                    transaction = new Expense(category, currentBalance, transactionDate, description, account, method);
                    break;
                }
                case "Saving": {
                    String goal = request.getParameter("goal");
                    currentBalance += amount; // Add amount to the balance
                    transaction = new Saving(category, currentBalance, transactionDate, description, account, goal);
                    break;
                }
                default:
                    throw new IllegalArgumentException("Invalid transaction category.");
            }

            // Update account balance and register the transaction
            account.setBalance(currentBalance); // Update the account object with the new balance
            String result = transactionController.registerTrans(transaction);

            // Set result and forward to JSP
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
