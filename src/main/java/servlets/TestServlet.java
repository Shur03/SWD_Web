package servlets;

import java.time.LocalDate;

import dao.TransactionDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;
import model.Transaction;

@WebServlet("/testServlet")
public class TestServlet {
private TransactionDAO transactionDAO;
public void init() {
//	super();
	transactionDAO = new TransactionDAO();
	
}
protected void doPost(HttpServletRequest request, HttpServletResponse response ) {
//	 private Account acc;
//	    private LocalDate transDate;
//	    private double amount;
//	    private String desc;
//	    private String category;
	String category = request.getParameter("category");
	String amount = request.getParameter("amount");
	String desc = request.getParameter("desc");
//	Transaction transaction = new Transaction();
	
}
}
