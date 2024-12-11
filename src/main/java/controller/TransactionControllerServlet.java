package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class TransactionControllerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountId = request.getParameter("accountId");
        String category = request.getParameter("category");
        double amount = Double.parseDouble(request.getParameter("amount"));
        String date = request.getParameter("date");
        String desc = request.getParameter("desc");
        String paymentMethod = request.getParameter("paymentMethod");
        String source = request.getParameter("source");
        String goal = request.getParameter("goal");

        // Save transaction logic can go here (e.g., storing in DB)

        // Forward data to JSP
        request.setAttribute("accountId", accountId);
        request.setAttribute("category", category);
        request.setAttribute("amount", amount);
        request.setAttribute("date", date);
        request.setAttribute("desc", desc);
        request.setAttribute("paymentMethod", paymentMethod);
        request.setAttribute("source", source);
        request.setAttribute("goal", goal);

        request.getRequestDispatcher("TransactionSummary.jsp").forward(request, response);
    }
}
