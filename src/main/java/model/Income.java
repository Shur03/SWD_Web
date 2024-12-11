package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import enums.Source;

public class Income extends Transaction {
  
	private Source source;

    public Income(int id, String category, double amount,LocalDate date, String description, Account acc, Source source) {
        super(id, category, amount, date,description,acc);
        this.source = source;
    }
    public Income( String category, double amount,LocalDate date, String description, Account acc, Source source) {
        super( category, amount, date,description,acc);
        this.source = source;
    }
    public Source getSource() {
        return source;
    }

    public static List<Income> addRecurringIncome(int id,String category, double amount,  LocalDate startDate,String description,Account acc,  Source source, int occurrences, String frequency) {
        List<Income> recurringIncomes = new ArrayList<>();
        LocalDate currentDate = startDate;

        for (int i = 0; i < occurrences; i++) {
            recurringIncomes.add(new Income(id + i, category, amount,currentDate, description,acc, source));
            currentDate = getNextDate(currentDate, frequency);;
        }

        return recurringIncomes;
    }

    private static LocalDate getNextDate(LocalDate currentDate, String frequency) {
        int daysToAdd = frequency.equalsIgnoreCase("weekly") ? 7 : 30;
        return currentDate.plusDays(daysToAdd);
    }
//    @Override
//  	public String toString() {
//  		return "Income [source=" + source + ", id=" + id + ", transDate=" + transDate + ", amount=" + amount + ", desc="
//  				+ desc + ", category=" + category + "]";
//  	}
}
