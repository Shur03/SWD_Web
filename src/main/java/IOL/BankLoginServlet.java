package IOL;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Customer;
import factory.CustomerFactory;

//@WebServlet("/BankLoginServlet")
public class BankLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            request.setAttribute("message", "Please enter both username and password.");
            request.setAttribute("messageType", "error");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // Use the CustomerFactory to authenticate and create a Customer object
        Customer customer = CustomerFactory.authenticate(username, password);

        if (customer != null) {
            // Set customer and account in session
            request.getSession().setAttribute("customer", customer);
            request.getSession().setAttribute("loggedInAccount", customer.getAccount());
            response.sendRedirect("bankMain.jsp");
        } else {
            // Invalid credentials
            request.setAttribute("message", "Invalid username or password.");
            request.setAttribute("messageType", "error");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
