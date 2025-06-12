import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for fetching and displaying database tables
 */
public class DatabaseFetcher {
    
    /**
     * Fetch data from the specified table
     * @param tableName the name of the table to fetch data from
     * @return true if successful, false otherwise
     */
    public boolean fetchDataFromDatabase(String tableName) {
        ConsoleColors.printHeader("TABLE: " + tableName.toUpperCase());
        
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM " + tableName;
            
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                
                // Store column names and max width for each column
                List<String> columnNames = new ArrayList<>();
                List<Integer> columnWidths = new ArrayList<>();
                
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    columnNames.add(columnName);
                    // Initialize with column name length
                    columnWidths.add(columnName.length());
                }
                
                // Store rows for later display and calculate max column widths
                List<List<String>> rows = new ArrayList<>();
                while (resultSet.next()) {
                    List<String> row = new ArrayList<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String value = resultSet.getString(i);
                        if (value == null) value = "NULL";
                        row.add(value);
                        
                        // Update max width if needed
                        if (value.length() > columnWidths.get(i-1)) {
                            columnWidths.set(i-1, value.length());
                        }
                    }
                    rows.add(row);
                }
                
                // Print header
                System.out.println();
                for (int i = 0; i < columnCount; i++) {
                    String columnName = columnNames.get(i);
                    int width = columnWidths.get(i);
                    System.out.print(ConsoleColors.CYAN_BOLD + padRight(columnName, width) + " | " + ConsoleColors.RESET);
                }
                System.out.println();
                
                // Print separator
                for (int i = 0; i < columnCount; i++) {
                    int width = columnWidths.get(i);
                    System.out.print(ConsoleColors.CYAN + "-".repeat(width) + "-+-" + ConsoleColors.RESET);
                }
                System.out.println();
                
                // Print rows
                for (List<String> row : rows) {
                    for (int i = 0; i < columnCount; i++) {
                        String value = row.get(i);
                        int width = columnWidths.get(i);
                        System.out.print(padRight(value, width) + " | ");
                    }
                    System.out.println();
                }
                
                if (rows.isEmpty()) {
                    ConsoleColors.printInfo("No data found in table " + tableName);
                } else {
                    ConsoleColors.printSuccess("Retrieved " + rows.size() + " rows from " + tableName);
                }
                
                return true;
            }
        } catch (SQLException e) {
            ConsoleColors.printError("SQL Error: " + e.getMessage());
            System.out.println("Check if Laragon MySQL is running and 'cinema' database exists");
            return false;
        } catch (ClassNotFoundException e) {
            ConsoleColors.printError("Driver not found: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Fetch data from the categories table
     * @return true if successful, false otherwise
     */
    public boolean fetchDataFromDatabase() {
        return fetchDataFromDatabase("categories");
    }
    
    /**
     * Pad a string with spaces to the right to reach specified width
     * @param s the string to pad
     * @param width the desired width
     * @return the padded string
     */
    private String padRight(String s, int width) {
        return String.format("%-" + width + "s", s);
    }
}
