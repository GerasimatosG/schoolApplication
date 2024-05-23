import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;


public class Main {

	public static void main(String[] args) {
		
		JDBCPostgreSQLConnection app = new JDBCPostgreSQLConnection();
		
		try {
			Connection connection = app.connect();
			System.out.println("Connected to the PostgreSQL server.");
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		
			statement.close();
	        connection.close();
			
		}catch(Exception e) {
			System.out.println("error");
		}

	}

}
