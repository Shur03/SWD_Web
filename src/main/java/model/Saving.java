package model;

import java.time.LocalDate;
import java.util.Date;

public class Saving extends Transaction {
	public String goal;
	public Saving(int id, String category,  double amount, LocalDate transDate,String desc, Account acc, String goal) {
		super(id, category, amount,transDate, desc,acc);
		this.goal = goal;
	}
	public Saving( String category,  double amount, LocalDate transDate,String desc, Account acc, String goal) {
		super( category, amount,transDate, desc,acc);
		this.goal = goal;
	}
	public String getGoal() {
		return this.goal;
	}
	private void setGoal(String goal) {
		this.goal = goal;
	}
}
