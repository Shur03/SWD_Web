package model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import enums.PaymentMethod;

public class Expense extends Transaction {
    private PaymentMethod paymentMethod;

    public Expense(int id,String category, double amount, LocalDate date, String description,Account acc,  PaymentMethod paymentMethod) {
        super( id,category, amount, date,description,acc );
        this.paymentMethod = paymentMethod;
    }
    public Expense(String category, double amount, LocalDate date, String description,Account acc,  PaymentMethod paymentMethod) {
        super( category, amount, date,description,acc );
        this.paymentMethod = paymentMethod;
    }
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }


    public static List<Expense> addRecurringExpense(int id,  String category, double amount,LocalDate startDate, String description,Account acc, PaymentMethod paymentMethod, int occurrences, String frequency) {
        List<Expense> recurringExpenses = new ArrayList<>();
        LocalDate currentDate = startDate;

        for (int i = 0; i < occurrences; i++) {
            recurringExpenses.add(new Expense(id + i, category, amount, currentDate, description, acc,paymentMethod));
            currentDate = getNextDate(currentDate, frequency);
        }

        return recurringExpenses;
    }

    private static LocalDate getNextDate(LocalDate currentDate, String frequency) {
        int daysToAdd = frequency.equalsIgnoreCase("weekly") ? 7 : 30;
        return currentDate.plusDays(daysToAdd);
    }
//	@Override
//	public String toString() {
//		return "Expense [paymentMethod=" + paymentMethod + ", id=" + id + ", transDate=" + transDate + ", amount="
//				+ amount + ", desc=" + desc + ", category=" + category + "]";
//	}

   
}
