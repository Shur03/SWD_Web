package IOL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDao {
	public static boolean validate(String name, String pass) {
		boolean status = false;
		String jdbcUrl = "jdbc:mysql://localhost:3306/BankTrans";
        String jdbcUsername = "shur";
        String jdbcPassword = "shur03";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
	       Connection con = DriverManager.getConnection(jdbcUrl,jdbcUsername,jdbcPassword );

			PreparedStatement ps = con.prepareStatement("select * from Customer where userName=? and pass=?");
			ps.setString(1, name);
			ps.setString(2, pass);

			ResultSet rs = ps.executeQuery();
			status = rs.next();

		} catch (Exception e) {
			System.out.println(e);
		}
		return status;
	}
}