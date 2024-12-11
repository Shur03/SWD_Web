package IOL;

import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;
import model.Customer;

@WebServlet("/getBalance")
public class GetBalanceServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        if (customer == null || customer.getAccount() == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Account account = customer.getAccount();
        response.setContentType("application/json");
        response.getWriter().write("{\"balance\": \"" + account.getBalance() + "\"}");
    }
}

