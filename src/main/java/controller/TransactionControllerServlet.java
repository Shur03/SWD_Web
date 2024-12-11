package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.Transaction;

import java.io.IOException;
import java.time.LocalDate;

import dao.TransactionDAO;

//@WebServlet("/TransactionControllerServlet")
public class TransactionControllerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TransactionDAO transactionDAO;

    @Override
    public void init() {
        transactionDAO = new TransactionDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Retrieve logged-in account
        Account loggedInAccount = (Account) session.getAttribute("loggedInAccount");

        if (loggedInAccount == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // Gather transaction details from the form
            String category = request.getParameter("category");
            double amount = Double.parseDouble(request.getParameter("amount"));
            String date = request.getParameter("date");
            String desc = request.getParameter("desc");

            // Save the transaction
            Transaction transaction = new Transaction(
                0, category, amount, LocalDate.parse(date), desc, loggedInAccount
            );
//            transactionDAO.saveTransaction(transaction);
            transactionDAO.saveTransaction(transaction);

            // Redirect to display transactions
            response.sendRedirect("TransactionRecordServlet");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
