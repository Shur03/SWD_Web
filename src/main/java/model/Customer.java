package model;

import java.util.ArrayList;
import java.util.List;

public class Customer {
 private String customerId;
 private String userName;
 private String password;
 private Account account;

 public Customer(String customerId, String userName, String pass, Account account) {
     this.customerId = customerId;
     this.userName = userName;
     this.password = pass;
     this.account = account;
 }

 public String getCustomerId() {
     return customerId;
 }

 public String getUserName() {
	return userName;
}

private void setUserName(String userName) {
	this.userName = userName;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public void setCustomerId(String customerId) {
	this.customerId = customerId;
}

public Account getAccount() {
	return account;
}

private void setAccount(Account account) {
	this.account = account;
}

}

