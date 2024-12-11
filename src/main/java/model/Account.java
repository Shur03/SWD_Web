package model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Observable;
import java.util.Observer;

import db.DatabaseConnection;

public class Account implements Observer {
    private String accountId;
    private double balance;

    public Account(String accountId, double initialBalance) {
        this.accountId = accountId;
        this.balance = initialBalance;
    }

    public String getAccountId() {
        return accountId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
    	if(balance > 0) {
    		this.balance = balance;
    	}
    	else {
    		return;
    	}
        
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Double) {
            setBalance((Double) arg); // Update balance when notified
        }
    }
}