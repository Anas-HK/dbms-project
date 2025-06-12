import java.util.Scanner;

/**
 * Class for updating user passwords
 */
public class UpdateUserPassword {
    private Scanner scanner;
    
    public UpdateUserPassword() {
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Update a user's password
     * @return true if update was successful, false otherwise
     */
    public boolean UpdatePassword() {
        ConsoleColors.printHeader("UPDATE PASSWORD");
        
        // Get user email
        ConsoleColors.printPrompt("Enter your email: ");
        String email = scanner.nextLine().trim();
        
        // Get current password for verification
        ConsoleColors.printPrompt("Enter current password: ");
        String currentPassword = scanner.nextLine().trim();
        
        // Authenticate user
        User user = User.authenticate(email, currentPassword);
        if (user == null) {
            ConsoleColors.printError("Invalid email or password.");
            return false;
        }
        
        // Get new password with confirmation
        ConsoleColors.printPrompt("Enter new password (min 6 characters): ");
        String newPassword = scanner.nextLine().trim();
        
        if (newPassword.length() < 6) {
            ConsoleColors.printError("Password must be at least 6 characters long.");
            return false;
        }
        
        ConsoleColors.printPrompt("Confirm new password: ");
        String confirmPassword = scanner.nextLine().trim();
        
        if (!newPassword.equals(confirmPassword)) {
            ConsoleColors.printError("Passwords do not match.");
            return false;
        }
        
        // Update user password
        user.setPassword(newPassword);
        if (user.update()) {
            ConsoleColors.printSuccess("Password updated successfully!");
            return true;
        } else {
            ConsoleColors.printError("Failed to update password. Please try again later.");
            return false;
        }
    }
}
