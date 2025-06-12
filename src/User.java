import java.sql.*;

/**
 * User class representing a user in the system
 */
public class User {
    private int userId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String createdAt;
    
    // Constructors
    public User() {
    }
    
    public User(int userId, String username, String password, String firstName, String lastName, String email, String phone) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }
    
    // Getters and Setters
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    
    // Database operations
    
    /**
     * Get a user by email
     * @param email the email to look up
     * @return the User object or null if not found
     */
    public static User getUserByEmail(String email) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE email = ?";
            
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, email);
                
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        User user = new User();
                        user.setUserId(resultSet.getInt("user_id"));
                        user.setUsername(resultSet.getString("username"));
                        user.setPassword(resultSet.getString("password"));
                        user.setFirstName(resultSet.getString("first_name"));
                        user.setLastName(resultSet.getString("last_name"));
                        user.setEmail(resultSet.getString("email"));
                        user.setPhone(resultSet.getString("phone"));
                        user.setCreatedAt(resultSet.getString("created_at"));
                        return user;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error getting user by email: " + e.getMessage());
        }
        
        return null; // User not found
    }
    
    /**
     * Authenticate a user with email and password
     * @param email the email
     * @param password the password
     * @return the authenticated User or null if authentication failed
     */
    public static User authenticate(String email, String password) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, email);
                statement.setString(2, password);
                
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        User user = new User();
                        user.setUserId(resultSet.getInt("user_id"));
                        user.setUsername(resultSet.getString("username"));
                        user.setPassword(resultSet.getString("password"));
                        user.setFirstName(resultSet.getString("first_name"));
                        user.setLastName(resultSet.getString("last_name"));
                        user.setEmail(resultSet.getString("email"));
                        user.setPhone(resultSet.getString("phone"));
                        user.setCreatedAt(resultSet.getString("created_at"));
                        return user;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error authenticating user: " + e.getMessage());
        }
        
        return null; // Authentication failed
    }
    
    /**
     * Save a new user to the database
     * @return true if successful, false otherwise
     */
    public boolean save() {
        try (Connection connection = DBConnection.getConnection()) {
            // Get next available user ID if not set
            if (this.userId <= 0) {
                this.userId = DBConnection.getNextId("users", "user_id");
            }
            
            // Generate username from first and last name if not set
            if (this.username == null || this.username.isEmpty()) {
                this.username = this.firstName.toLowerCase() + "." + this.lastName.toLowerCase();
            }
            
            String query = "INSERT INTO users (user_id, username, password, first_name, last_name, email, phone) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, this.userId);
                statement.setString(2, this.username);
                statement.setString(3, this.password);
                statement.setString(4, this.firstName);
                statement.setString(5, this.lastName);
                statement.setString(6, this.email);
                statement.setString(7, this.phone);
                
                int rowsAffected = statement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (Exception e) {
            System.out.println("Error saving user: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Update an existing user in the database
     * @return true if successful, false otherwise
     */
    public boolean update() {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "UPDATE users SET username = ?, password = ?, first_name = ?, " +
                           "last_name = ?, email = ?, phone = ? WHERE user_id = ?";
            
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, this.username);
                statement.setString(2, this.password);
                statement.setString(3, this.firstName);
                statement.setString(4, this.lastName);
                statement.setString(5, this.email);
                statement.setString(6, this.phone);
                statement.setInt(7, this.userId);
                
                int rowsAffected = statement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (Exception e) {
            System.out.println("Error updating user: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Delete a user from the database
     * @return true if successful, false otherwise
     */
    public boolean delete() {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "DELETE FROM users WHERE user_id = ?";
            
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, this.userId);
                
                int rowsAffected = statement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (Exception e) {
            System.out.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
} 