package IOL;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import db.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;
import model.Customer;

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

        Customer customer = authenticate(username, password);
        if (customer != null) {
            // Successful login
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

    private Customer authenticate(String username, String password) {
        String query = "SELECT c.id AS customerID, c.userName, c.pass, a.id AS accountID, a.balance " +
                       "FROM Customer c " +
                       "LEFT JOIN Account a ON a.customerID = c.id " +
                       "WHERE c.userName = ? AND c.pass = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String accountID = resultSet.getString("accountID");
                double balance = resultSet.getDouble("balance");

                Account account = null;
                if (accountID != null) {
                    account = new Account(accountID, balance);
                }

                String customerID = resultSet.getString("customerID");
                String userName = resultSet.getString("userName");
                String pass = resultSet.getString("pass");

                return new Customer(customerID, userName, pass, account);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
