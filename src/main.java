import javax.xml.crypto.Data;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;
import java.util.Scanner;

// Import custom classes

/**
 * Main application class with menu-driven interface
 */
public class main {
    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser = null;
    
    public static void main(String[] args) {
        // Test database connection
        try {
            System.out.println("Attempting to connect to database...");
            System.out.println("DB_HOST: " + System.getenv("DB_HOST"));
            System.out.println("DB_NAME: " + System.getenv("DB_NAME"));
            System.out.println("DB_USER: " + System.getenv("DB_USER"));
            System.out.println("DB_PASS: " + (System.getenv("DB_PASS") != null ? "******" : "null"));
            
            Connection conn = DBConnection.getConnection();
            System.out.println("Successfully connected to database!");
            conn.close();
        } catch (Exception e) {
            ConsoleColors.printError("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
            System.out.println("\nTROUBLESHOOTING TIPS:");
            System.out.println("1. Ensure Docker containers are running: docker-compose ps");
            System.out.println("2. Check container logs: docker-compose logs");
            System.out.println("3. Verify database is healthy: docker-compose exec db mysqladmin -u root -proot ping");
            System.out.println("4. Try restarting containers: docker-compose down && docker-compose up");
            System.exit(1);
        }
        
        // Display welcome message
        ConsoleColors.printHeader("ECOMMERCE DATABASE MANAGEMENT SYSTEM");
        
        // Main application loop
        boolean exit = false;
        while (!exit) {
            if (currentUser == null) {
                // User is not logged in, show authentication menu
                showAuthMenu();
            } else {
                // User is logged in, show main menu
                exit = showMainMenu();
            }
        }
        
        // Clean up resources
        scanner.close();
        ConsoleColors.printInfo("Thank you for using the Ecommerce Database Management System!");
    }
    
    /**
     * Display authentication menu (login/signup)
     */
    private static void showAuthMenu() {
        ConsoleColors.printHeader("AUTHENTICATION MENU");
        ConsoleColors.printOption(1, "Login");
        ConsoleColors.printOption(2, "Sign Up");
        ConsoleColors.printOption(0, "Exit");
        
        ConsoleColors.printPrompt("\nEnter your choice: ");
        String input = scanner.nextLine().trim();
        
        try {
            int choice = Integer.parseInt(input);
            
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    signup();
                    break;
                case 0:
                    System.exit(0);
                    break;
                default:
                    ConsoleColors.printError("Invalid choice. Please try again.");
            }
        } catch (NumberFormatException e) {
            ConsoleColors.printError("Please enter a valid number.");
        }
    }
    
    /**
     * Handle user login
     */
    private static void login() {
        ConsoleColors.printHeader("LOGIN");
        
        // Special admin login
        ConsoleColors.printOption(1, "Regular User Login");
        ConsoleColors.printOption(2, "Admin Login");
        ConsoleColors.printOption(0, "Back to Main Menu");
        
        ConsoleColors.printPrompt("\nEnter your choice: ");
        String input = scanner.nextLine().trim();
        
        try {
            int choice = Integer.parseInt(input);
            
            switch (choice) {
                case 1:
                    // Regular user login with email
                    ConsoleColors.printPrompt("Email: ");
                    String email = scanner.nextLine().trim();
                    
                    ConsoleColors.printPrompt("Password: ");
                    String password = scanner.nextLine().trim();
                    
                    // Authenticate user
                    User user = User.authenticate(email, password);
                    if (user != null) {
                        currentUser = user;
                        ConsoleColors.printSuccess("Login successful! Welcome, " + user.getFirstName() + "!");
                    } else {
                        ConsoleColors.printError("Invalid email or password. Please try again.");
                    }
                    break;
                    
                case 2:
                    // Admin login
                    ConsoleColors.printPrompt("Admin Username: ");
                    String adminUsername = scanner.nextLine().trim();
                    
                    ConsoleColors.printPrompt("Admin Password: ");
                    String adminPassword = scanner.nextLine().trim();
                    
                    // Check admin credentials
                    if ("admin".equalsIgnoreCase(adminUsername) && "password".equals(adminPassword)) {
                        // Create admin user
                        User adminUser = new User();
                        adminUser.setUserId(0);
                        adminUser.setUsername("admin");
                        adminUser.setFirstName("Admin");
                        adminUser.setLastName("User");
                        adminUser.setEmail("admin@example.com");
                        
                        currentUser = adminUser;
                        ConsoleColors.printSuccess("Admin login successful!");
                    } else {
                        ConsoleColors.printError("Invalid admin credentials. Please try again.");
                    }
                    break;
                    
                case 0:
                    return;
                    
                default:
                    ConsoleColors.printError("Invalid choice. Please try again.");
            }
        } catch (NumberFormatException e) {
            ConsoleColors.printError("Please enter a valid number.");
        }
    }
    
    /**
     * Handle user signup
     */
    private static void signup() {
        Signup signupManager = new Signup();
        signupManager.newSignup();
    }
    
    /**
     * Display main menu for logged-in users
     * @return true if the application should exit, false otherwise
     */
    private static boolean showMainMenu() {
        ConsoleColors.printHeader("MAIN MENU");
        ConsoleColors.printInfo("Logged in as: " + currentUser.getFirstName() + " " + currentUser.getLastName() + 
                               (currentUser.getUserId() == 0 ? " (Admin)" : ""));
        
        ConsoleColors.printOption(1, "View Database Tables");
        ConsoleColors.printOption(2, "User Management");
        if (currentUser.getUserId() == 0) {
            // Admin options
            ConsoleColors.printOption(3, "Database Administration");
        }
        ConsoleColors.printOption(9, "Logout");
        ConsoleColors.printOption(0, "Exit Application");
        
        ConsoleColors.printPrompt("\nEnter your choice: ");
        String input = scanner.nextLine().trim();
        
        try {
            int choice = Integer.parseInt(input);
            
            switch (choice) {
                case 1:
                    showTablesMenu();
                    return false;
                case 2:
                    showUserManagementMenu();
                    return false;
                case 3:
                    if (currentUser.getUserId() == 0) {
                        // Admin menu
                        showAdminMenu();
                    } else {
                        ConsoleColors.printError("Invalid choice. Please try again.");
                    }
                    return false;
                case 9:
                    // Logout
                    currentUser = null;
                    ConsoleColors.printSuccess("You have been logged out.");
                    return false;
                case 0:
                    // Exit application
                    return true;
                default:
                    ConsoleColors.printError("Invalid choice. Please try again.");
                    return false;
            }
        } catch (NumberFormatException e) {
            ConsoleColors.printError("Please enter a valid number.");
            return false;
        }
    }
    
    /**
     * Display menu for viewing database tables
     */
    private static void showTablesMenu() {
        ConsoleColors.printHeader("DATABASE TABLES");
        
        ConsoleColors.printOption(1, "Users");
        ConsoleColors.printOption(2, "Products");
        ConsoleColors.printOption(3, "Categories");
        ConsoleColors.printOption(4, "Orders");
        ConsoleColors.printOption(5, "Payment");
        ConsoleColors.printOption(6, "Shipping");
        ConsoleColors.printOption(7, "Reviews");
        ConsoleColors.printOption(0, "Back to Main Menu");
        
        ConsoleColors.printPrompt("\nEnter your choice: ");
        String input = scanner.nextLine().trim();
        
        try {
            int choice = Integer.parseInt(input);
            
            if (choice == 0) {
                return;
            }
            
            String selectedTable;
            switch (choice) {
                case 1: selectedTable = "users"; break;
                case 2: selectedTable = "products"; break;
                case 3: selectedTable = "categories"; break;
                case 4: selectedTable = "orders"; break;
                case 5: selectedTable = "payment"; break;
                case 6: selectedTable = "shipping"; break;
                case 7: selectedTable = "reviews"; break;
                default:
                    ConsoleColors.printError("Invalid option, showing categories table");
                    selectedTable = "categories";
            }
            
            DatabaseFetcher fetcher = new DatabaseFetcher();
            fetcher.fetchDataFromDatabase(selectedTable);
            
            // Wait for user to press enter
            ConsoleColors.printPrompt("\nPress Enter to continue...");
            scanner.nextLine();
            
        } catch (NumberFormatException e) {
            ConsoleColors.printError("Please enter a valid number.");
        }
    }
    
    /**
     * Display user management menu
     */
    private static void showUserManagementMenu() {
        ConsoleColors.printHeader("USER MANAGEMENT");
        
        ConsoleColors.printOption(1, "Update Password");
        ConsoleColors.printOption(2, "Update Email");
        ConsoleColors.printOption(3, "View My Profile");
        if (currentUser.getUserId() == 0) {
            // Admin options
            ConsoleColors.printOption(4, "View All Users");
            ConsoleColors.printOption(5, "Delete User");
        }
        ConsoleColors.printOption(0, "Back to Main Menu");
        
        ConsoleColors.printPrompt("\nEnter your choice: ");
        String input = scanner.nextLine().trim();
        
        try {
            int choice = Integer.parseInt(input);
            
            switch (choice) {
                case 0:
                    return;
                case 1:
                    UpdateUserPassword passwordUpdater = new UpdateUserPassword();
                    passwordUpdater.UpdatePassword();
                    break;
                case 2:
                    UpdateUserProcedure emailUpdater = new UpdateUserProcedure();
                    emailUpdater.UpdateUser();
                    break;
                case 3:
                    // View profile
                    viewUserProfile();
                    break;
                case 4:
                    // Admin: View all users
                    if (currentUser.getUserId() == 0) {
                        DatabaseFetcher fetcher = new DatabaseFetcher();
                        fetcher.fetchDataFromDatabase("users");
                        
                        // Wait for user to press enter
                        ConsoleColors.printPrompt("\nPress Enter to continue...");
                        scanner.nextLine();
                    } else {
                        ConsoleColors.printError("Access denied.");
                    }
                    break;
                case 5:
                    // Admin: Delete user
                    if (currentUser.getUserId() == 0) {
                        deleteUser();
                    } else {
                        ConsoleColors.printError("Access denied.");
                    }
                    break;
                default:
                    ConsoleColors.printError("Invalid choice. Please try again.");
            }
        } catch (NumberFormatException e) {
            ConsoleColors.printError("Please enter a valid number.");
        }
    }
    
    /**
     * Display admin menu
     */
    private static void showAdminMenu() {
        ConsoleColors.printHeader("ADMIN MENU");
        
        ConsoleColors.printOption(1, "Database Connection Test");
        ConsoleColors.printOption(0, "Back to Main Menu");
        
        ConsoleColors.printPrompt("\nEnter your choice: ");
        String input = scanner.nextLine().trim();
        
        try {
            int choice = Integer.parseInt(input);
            
            switch (choice) {
                case 0:
                    return;
                case 1:
                    // Database connection test
                    try {
                        DBConnection.getConnection().close();
                        ConsoleColors.printSuccess("Database connection successful!");
                    } catch (Exception e) {
                        ConsoleColors.printError("Database connection failed: " + e.getMessage());
                    }
                    
                    // Wait for user to press enter
                    ConsoleColors.printPrompt("\nPress Enter to continue...");
                    scanner.nextLine();
                    break;
                default:
                    ConsoleColors.printError("Invalid choice. Please try again.");
            }
        } catch (NumberFormatException e) {
            ConsoleColors.printError("Please enter a valid number.");
        }
    }
    
    /**
     * View current user's profile
     */
    private static void viewUserProfile() {
        ConsoleColors.printHeader("USER PROFILE");
        
        if (currentUser != null) {
            System.out.println(ConsoleColors.CYAN_BOLD + "User ID:     " + ConsoleColors.RESET + currentUser.getUserId());
            System.out.println(ConsoleColors.CYAN_BOLD + "Username:    " + ConsoleColors.RESET + currentUser.getUsername());
            System.out.println(ConsoleColors.CYAN_BOLD + "First Name:  " + ConsoleColors.RESET + currentUser.getFirstName());
            System.out.println(ConsoleColors.CYAN_BOLD + "Last Name:   " + ConsoleColors.RESET + currentUser.getLastName());
            System.out.println(ConsoleColors.CYAN_BOLD + "Email:       " + ConsoleColors.RESET + currentUser.getEmail());
            System.out.println(ConsoleColors.CYAN_BOLD + "Phone:       " + ConsoleColors.RESET + currentUser.getPhone());
            System.out.println(ConsoleColors.CYAN_BOLD + "Created At:  " + ConsoleColors.RESET + currentUser.getCreatedAt());
        } else {
            ConsoleColors.printError("No user is currently logged in.");
        }
        
        // Wait for user to press enter
        ConsoleColors.printPrompt("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    /**
     * Delete a user (admin only)
     */
    private static void deleteUser() {
        ConsoleColors.printHeader("DELETE USER");
        
        ConsoleColors.printPrompt("Enter User ID to delete: ");
        String input = scanner.nextLine().trim();
        
        try {
            int userId = Integer.parseInt(input);
            
            // Confirm deletion
            ConsoleColors.printPrompt("Are you sure you want to delete User ID " + userId + "? (yes/no): ");
            String confirm = scanner.nextLine().trim().toLowerCase();
            
            if (confirm.equals("yes")) {
                // Create user object with ID and delete
                User user = new User();
                user.setUserId(userId);
                
                if (user.delete()) {
                    ConsoleColors.printSuccess("User with ID " + userId + " has been deleted.");
                } else {
                    ConsoleColors.printError("Failed to delete user. User ID may not exist.");
                }
            } else {
                ConsoleColors.printInfo("User deletion cancelled.");
            }
        } catch (NumberFormatException e) {
            ConsoleColors.printError("Please enter a valid number.");
        }
        
        // Wait for user to press enter
        ConsoleColors.printPrompt("\nPress Enter to continue...");
        scanner.nextLine();
    }
}
