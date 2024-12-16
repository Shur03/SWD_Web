package factory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import db.DatabaseConnection;
import model.Account;
import model.Customer;

public class CustomerFactory {

    public static Customer authenticate(String username, String password) {
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
