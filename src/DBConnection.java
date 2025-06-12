import java.sql.*;
import java.util.Properties;

/**
 * Utility class for database connections
 */
public class DBConnection {
    // Read environment variables with defaults
    private static final String DB_HOST = System.getenv("DB_HOST") != null ? System.getenv("DB_HOST") : "localhost";
    private static final String DB_NAME = System.getenv("DB_NAME") != null ? System.getenv("DB_NAME") : "cinema";
    private static final String USER = System.getenv("DB_USER") != null ? System.getenv("DB_USER") : "root";
    private static final String PASSWORD = System.getenv("DB_PASS") != null ? System.getenv("DB_PASS") : "";
    private static final String URL = "jdbc:mysql://" + DB_HOST + ":3306/" + DB_NAME + 
                                      "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&connectTimeout=30000";
    
    // Maximum connection attempts and retry delay
    private static final int MAX_RETRIES = 5;
    private static final int RETRY_DELAY_MS = 5000; // 5 seconds
    
    /**
     * Get a database connection with retry mechanism
     * @return a Connection object
     * @throws SQLException if connection fails after retries
     * @throws ClassNotFoundException if JDBC driver not found
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        // Load the MySQL JDBC driver explicitly
        Class.forName("com.mysql.cj.jdbc.Driver");
        
        // Set up connection properties
        Properties props = new Properties();
        props.setProperty("user", USER);
        props.setProperty("password", PASSWORD);
        
        // Connection retry loop
        SQLException lastException = null;
        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            try {
                // Attempt to connect
                System.out.println("Connection attempt " + attempt + " of " + MAX_RETRIES + "...");
                return DriverManager.getConnection(URL, props);
            } catch (SQLException e) {
                lastException = e;
                if (attempt < MAX_RETRIES) {
                    System.out.println("Connection failed, retrying in " + (RETRY_DELAY_MS / 1000) + " seconds...");
                    try {
                        Thread.sleep(RETRY_DELAY_MS);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw e; // Re-throw the SQL exception if interrupted
                    }
                }
            }
        }
        
        // If we get here, all retries failed
        throw new SQLException("Failed to connect to database after " + MAX_RETRIES + " attempts", lastException);
    }
    
    /**
     * Close a database connection safely
     * @param connection the connection to close
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }
    
    /**
     * Get the next available ID from a table
     * @param tableName the name of the table
     * @param idColumnName the name of the ID column
     * @return the next available ID
     */
    public static int getNextId(String tableName, String idColumnName) {
        int nextId = 1; // Default starting ID
        
        try (Connection connection = getConnection()) {
            String query = "SELECT MAX(" + idColumnName + ") + 1 AS next_id FROM " + tableName;
            
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                
                if (resultSet.next()) {
                    // Get the next ID (if NULL, use 1)
                    int result = resultSet.getInt("next_id");
                    if (!resultSet.wasNull()) {
                        nextId = result;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error getting next ID: " + e.getMessage());
        }
        
        return nextId;
    }
} 