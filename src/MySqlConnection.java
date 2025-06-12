import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySqlConnection {
    public static void main(String[] args) {
        // Use the successful connection approach
        String url = "jdbc:mysql://localhost:3306/cinema?useSSL=false";
        
        try {
            // Load the MySQL JDBC driver explicitly
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            System.out.println("Connecting to Laragon MySQL database...");
            
            // Use Properties for connection parameters
            Properties props = new Properties();
            props.setProperty("user", "root");
            props.setProperty("password", "");
            
            // Connect with the properties that worked
            Connection connection = DriverManager.getConnection(url, props);
            System.out.println("Connection established successfully!");
            
            // Close the connection
            connection.close();
            System.out.println("Connection closed!");
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
            
            System.out.println("\nLARAGON MYSQL TROUBLESHOOTING TIPS:");
            System.out.println("1. Verify Laragon is running and MySQL service is started");
            System.out.println("2. Check if the 'cinema' database exists in Laragon");
            System.out.println("3. Try connecting via Laragon's HeidiSQL with root and no password");
            System.out.println("4. Make sure MySQL connector JAR is in classpath");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
