package model;

import db.DatabaseConnection;
import java.sql.*;

public class TransactionModel {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/BankTrans";
    private static final String DB_USERNAME = "shur";
    private static final String DB_PASSWORD = "shur0369";

    public boolean registerTransaction(Transaction transaction) throws Exception {
        String query;
        switch (transaction.getCategory()) {
            case "Income" -> query = "INSERT INTO Transaction (account_ID, category, amount, transDate, descrip, sources) VALUES (?, ?, ?, ?, ?, ?)";
            case "Expense" -> query = "INSERT INTO Transaction (account_ID, category, amount, transDate, descrip, method) VALUES (?, ?, ?, ?, ?, ?)";
            case "Saving" -> query = "INSERT INTO Transaction (account_ID, category, amount, transDate, descrip, goal) VALUES (?, ?, ?, ?, ?, ?)";
            default -> throw new IllegalArgumentException("Invalid transaction category.");
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, transaction.getAcc().getAccountId());
            stmt.setString(2, transaction.getCategory());
            stmt.setDouble(3, transaction.getAmount());
            stmt.setDate(4, Date.valueOf(transaction.getTransDate()));
            stmt.setString(5, transaction.getDesc());

            if (transaction instanceof Income income) {
                stmt.setString(6, income.getSource().toString());
            } else if (transaction instanceof Expense expense) {
                stmt.setString(6, expense.getPaymentMethod().toString());
            } else if (transaction instanceof Saving saving) {
                stmt.setString(6, saving.getGoal());
            }

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        transaction.setId(generatedKeys.getInt(1));
//                        updateAccountBalance(transaction);
                        return true;
                    }
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error inserting transaction data: " + e.getMessage(), e);
        }
    }

    private void updateAccountBalance(Transaction transaction) throws Exception {
        double newBalance = calculateNewBalance(transaction.getAcc().getBalance(), transaction);

        // Update account object
        transaction.getAcc().setBalance(newBalance);

        // Update database
        try (Connection conn = DatabaseConnection.getConnection()) {
            String updateBalanceQuery = "UPDATE Account SET balance = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(updateBalanceQuery)) {
                stmt.setDouble(1, newBalance);
                stmt.setString(2, transaction.getAcc().getAccountId());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to update balance in the database.");
        }
    }

    private double calculateNewBalance(double currentBalance, Transaction transaction) {
        if ("Income".equalsIgnoreCase(transaction.getCategory()) || "Saving".equalsIgnoreCase(transaction.getCategory())) {
            return currentBalance + transaction.getAmount();
        } else if ("Expense".equalsIgnoreCase(transaction.getCategory())) {
            return currentBalance - transaction.getAmount();
        }
        return currentBalance;
    }
    public Account getAccountById(String accountId) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT id, balance FROM Account WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, accountId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String id = rs.getString("id");
                        double balance = rs.getDouble("balance");
                        return new Account(id, balance);
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("Error retrieving account by ID: " + e.getMessage(), e);
        }
        return null; // Return null if account is not found
    }
}