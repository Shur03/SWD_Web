package model;

import java.time.LocalDate;

public class Transaction implements Comparable<Transaction> {
    private int id;
    private Account acc;
    private LocalDate transDate;
    private double amount;
    private String desc;
    private String category; // Income, Expense, Saving

    public Transaction(int id, String category, double amount, LocalDate transDate, String desc, Account acc) {
        this.id = id;
        this.transDate = transDate;
        this.amount = amount;
        this.desc = desc;
        this.category = category;
        this.acc = acc;
    }

    public Transaction( String category, double amount, LocalDate transDate, String desc, Account acc) {
        this.transDate = transDate;
        this.amount = amount;
        this.desc = desc;
        this.category = category;
        this.acc = acc;
    }
    

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Account getAcc() {
		return acc;
	}


	private void setAcc(Account acc) {
		this.acc = acc;
	}


	public LocalDate getTransDate() {
		return transDate;
	}


	private void setTransDate(LocalDate transDate) {
		this.transDate = transDate;
	}


	public double getAmount() {
		return amount;
	}


	private void setAmount(double amount) {
		this.amount = amount;
	}


	public String getDesc() {
		return desc;
	}


	private void setDesc(String desc) {
		this.desc = desc;
	}


	public String getCategory() {
		return category;
	}


	private void setCategory(String category) {
		this.category = category;
	}

	@Override
    public int compareTo(Transaction other) {
        return this.transDate.compareTo(other.transDate); 
    }

    @Override
    public String toString() {
        return "Transaction [id=" + id + ", transDate=" + transDate + ", amount=" + amount +
               ", desc=" + desc + ", category=" + category + "]";
    }

    public double calculateTotal() {
        return amount;
    }

	
	
}
