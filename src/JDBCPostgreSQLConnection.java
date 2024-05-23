import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class JDBCPostgreSQLConnection {
	private final String url = "jdbc:postgresql://localhost/JavaProject";
    private final String user = "postgres";
    private final String password = "123456";
	
    
    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);

            if (conn != null) {
                System.out.println("Connected to the PostgreSQL server successfully.");
            } else {
                System.out.println("Failed to make connection!");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }
    
    public static void main(String[] args) {
        JDBCPostgreSQLConnection app = new JDBCPostgreSQLConnection();
        app.connect();
    }
}


