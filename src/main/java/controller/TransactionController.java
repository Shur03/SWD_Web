package controller;

import model.Transaction;
import model.TransactionModel;

public class TransactionController {
    public final TransactionModel model;
    

    public TransactionController(TransactionModel model) {
        this.model = model;
    }

    public String registerTrans(Transaction transaction) {
        try {
            boolean success = model.registerTransaction(transaction);
            if (success) {
                return "Transaction registered successfully!";
            } else {
                return "Transaction registration failed.";
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

	
}