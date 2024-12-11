package dao;

import model.Account;
import model.Transaction;
import db.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//import javax.swing.JOptionPane;

public class TransactionDAO {

    // Method to fetch transactions by account ID
	public List<Transaction> getTransactionsByAccountId(String accountId) throws Exception {
	    return getTransactions(accountId, Integer.MAX_VALUE); // Get all transactions
	}

	public List<Transaction> getLastTransactionsByAccountId(String accountId, int limit) throws Exception {
	    return getTransactions(accountId, limit); // Get last 'n' transactions
	}

	private List<Transaction> getTransactions(String accountId, int limit) throws Exception {
	    List<Transaction> transactions = new ArrayList<>();
	    try (Connection conn = DatabaseConnection.getConnection()) {
	        if (conn == null) {
	            System.out.println("Failed to establish database connection!");
	            return transactions;
	        }

	        String query = "SELECT * FROM Transaction WHERE account_ID = ? ORDER BY transDate DESC LIMIT ?";
	        try (PreparedStatement stat = conn.prepareStatement(query)) {
	            stat.setString(1, accountId);
	            stat.setInt(2, limit);

	            ResultSet result = stat.executeQuery();
	            while (result.next()) {
	                int id = result.getInt("id");
	                String category = result.getString("category");
	                double amount = result.getDouble("amount");
	                Date transDate = result.getDate("transDate");
	                String description = result.getString("descrip");

	                Account account = getAccountById(conn, accountId);
	                Transaction transaction = new Transaction(id, category, amount, transDate.toLocalDate(), description, account);
	                transactions.add(transaction);
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Error during SQL execution: " + e.getMessage());
	        throw new Exception("Error fetching transactions: " + e.getMessage());
	    }
	    return transactions;
	}


    // Helper method to fetch the account details based on account ID
    private Account getAccountById(Connection conn, String accountId) throws SQLException {
        String query = "SELECT * FROM Account WHERE id = ?";
//        showMessageDialog(query);
        try (PreparedStatement stat = conn.prepareStatement(query)) {
            stat.setString(1, accountId);
            ResultSet result = stat.executeQuery();
            
            // Log if no result was found
            if (!result.next()) {
//            	showMessageDialog("No account found for ID: " + accountId);
                return null; // Return null if no account found
            }

            // Extract account details from the result set
            double balance = result.getDouble("balance");
//            showMessageDialog("Account found: ID=" + accountId + ", Balance=" + balance);
            return new Account(accountId, balance);
        }
    }
    public void saveTransaction(Transaction transaction) throws Exception {
        String query = "INSERT INTO Transaction (category, amount, transDate, descrip, account_ID) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stat = conn.prepareStatement(query)) {
            stat.setString(1, transaction.getCategory());
            stat.setDouble(2, transaction.getAmount());
            stat.setDate(3, Date.valueOf(transaction.getTransDate()));
            stat.setString(4, transaction.getDesc());
            stat.setString(5, transaction.getAcc().getAccountId());

            stat.executeUpdate();
            System.out.println("Transaction saved successfully.");
        } catch (SQLException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
            throw new Exception("Error saving transaction: " + e.getMessage());
        }
    }
//    private void showMessageDialog(String message) {
//        JOptionPane.showMessageDialog(null, message);
//    }
}
