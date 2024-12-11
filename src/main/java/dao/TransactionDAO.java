package dao;

import model.Account;
import model.Transaction;
import db.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    // Method to fetch transactions by account ID
    public List<Transaction> getTransactionsByAccountId(String accountId) throws Exception {
        List<Transaction> transactions = new ArrayList<>();
        
        // Log the start of the method
        System.out.println("Fetching transactions for Account ID: " + accountId);
        
        // Establish the database connection
        Connection conn = DatabaseConnection.getConnection();
        
        // Check if the connection is successful
        if (conn == null) {
            System.out.println("Failed to establish database connection!");
            return transactions;  // Return empty list if connection fails
        }
        
        System.out.println("Connection established successfully!");

        // SQL query to fetch transactions for a given account ID
        String query = "SELECT * FROM Transaction WHERE account_ID = ?";
        
        // Log the query being executed
        System.out.println("Executing query: " + query);

        try (PreparedStatement stat = conn.prepareStatement(query)) {
            // Set the account ID parameter in the query
            stat.setString(1, accountId);
            
            // Execute the query
            ResultSet result = stat.executeQuery();
            System.out.println("Query executed successfully.");

            // Process the result set and add transactions to the list
            while (result.next()) {
                int id = result.getInt("id");
                String category = result.getString("category");
                double amount = result.getDouble("amount");
                Date transDate = result.getDate("transDate");
                String description = result.getString("descrip");

                // Fetch the associated account for the transaction
                Account account = getAccountById(conn, accountId);
                Transaction transaction = new Transaction(id, category, amount, transDate.toLocalDate(), description, account);
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            // Log any SQL exceptions that occur
            System.out.println("Error during SQL execution: " + e.getMessage());
            throw new Exception("Error fetching transactions: " + e.getMessage());
        }

        // Return the list of transactions
        return transactions;
    }

    // Helper method to fetch the account details based on account ID
    private Account getAccountById(Connection conn, String accountId) throws SQLException {
        String query = "SELECT * FROM Account WHERE id = ?";
        
        // Log the query being executed to fetch account
        System.out.println("Executing query to fetch Account details: " + query);

        try (PreparedStatement stat = conn.prepareStatement(query)) {
            stat.setString(1, accountId);
            ResultSet result = stat.executeQuery();
            
            // Log if no result was found
            if (!result.next()) {
                System.out.println("No account found for ID: " + accountId);
                return null; // Return null if no account found
            }

            // Extract account details from the result set
            double balance = result.getDouble("balance");
            System.out.println("Account found: ID=" + accountId + ", Balance=" + balance);
            return new Account(accountId, balance);
        }
    }
}
