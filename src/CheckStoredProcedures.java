import java.sql.*;
import java.util.Properties;

public class CheckStoredProcedures {
    public static void main(String[] args) {
        // Connection settings for Laragon
        String url = "jdbc:mysql://localhost:3306/cinema?useSSL=false";
        
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Set up connection properties
            Properties props = new Properties();
            props.setProperty("user", "root");
            props.setProperty("password", "");
            
            System.out.println("Connecting to database...");
            try (Connection connection = DriverManager.getConnection(url, props)) {
                System.out.println("Connection successful!");
                
                // Check database information
                DatabaseMetaData metaData = connection.getMetaData();
                System.out.println("Database product name: " + metaData.getDatabaseProductName());
                System.out.println("Database product version: " + metaData.getDatabaseProductVersion());
                
                // Check if stored procedures exist
                checkProcedure(connection, "AddUser");
                checkProcedure(connection, "UpdateUserPassword");
                checkProcedure(connection, "UpdateUserEmail");
                
                // Check if tables exist and have the right structure
                checkTable(connection, "users");
                checkTable(connection, "categories");
                checkTable(connection, "products");
                
                System.out.println("\nAll checks complete!");
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void checkProcedure(Connection connection, String procedureName) {
        try {
            System.out.println("\nChecking for procedure: " + procedureName);
            
            // Query to check if procedure exists
            String query = "SELECT ROUTINE_NAME FROM INFORMATION_SCHEMA.ROUTINES " +
                           "WHERE ROUTINE_TYPE='PROCEDURE' AND ROUTINE_SCHEMA=? AND ROUTINE_NAME=?";
            
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, "cinema");
                statement.setString(2, procedureName);
                
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        System.out.println("✓ Procedure " + procedureName + " exists");
                    } else {
                        System.out.println("✗ Procedure " + procedureName + " does NOT exist");
                        System.out.println("  Please run the stored_procedures.sql or individual procedure scripts");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking procedure " + procedureName + ": " + e.getMessage());
        }
    }
    
    private static void checkTable(Connection connection, String tableName) {
        try {
            System.out.println("\nChecking for table: " + tableName);
            
            // Check if table exists
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, tableName, null);
            
            if (tables.next()) {
                System.out.println("✓ Table " + tableName + " exists");
                
                // Print column information
                System.out.println("  Columns:");
                ResultSet columns = metaData.getColumns(null, null, tableName, null);
                while (columns.next()) {
                    String columnName = columns.getString("COLUMN_NAME");
                    String columnType = columns.getString("TYPE_NAME");
                    System.out.println("    - " + columnName + " (" + columnType + ")");
                }
            } else {
                System.out.println("✗ Table " + tableName + " does NOT exist");
                System.out.println("  Please run the ecommerce_db_setup.sql script");
            }
        } catch (SQLException e) {
            System.out.println("Error checking table " + tableName + ": " + e.getMessage());
        }
    }
} 