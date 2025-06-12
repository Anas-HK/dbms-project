import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Class for updating user email
 */
public class UpdateUserProcedure {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private Scanner scanner;
    
    public UpdateUserProcedure() {
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Update a user's email
     * @return true if update was successful, false otherwise
     */
    public boolean UpdateUser() {
        ConsoleColors.printHeader("UPDATE EMAIL");
        
        // Get user email for authentication
        ConsoleColors.printPrompt("Enter your current email: ");
        String currentEmail = scanner.nextLine().trim();
        
        // Get password for verification
        ConsoleColors.printPrompt("Enter your password: ");
        String password = scanner.nextLine().trim();
        
        // Authenticate user
        User user = User.authenticate(currentEmail, password);
        if (user == null) {
            ConsoleColors.printError("Invalid email or password.");
            return false;
        }
        
        // Get new email with validation
        String newEmail;
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        boolean isValid;
        
        do {
            ConsoleColors.printPrompt("Enter new email: ");
            newEmail = scanner.nextLine().trim();
            
            if (newEmail.equals(currentEmail)) {
                ConsoleColors.printError("New email is the same as current email.");
                isValid = false;
            } else if (newEmail.length() < 5 || newEmail.length() > 100) {
                ConsoleColors.printError("Email must be between 5 and 100 characters.");
                isValid = false;
            } else if (!pattern.matcher(newEmail).matches()) {
                ConsoleColors.printError("Please enter a valid email address.");
                isValid = false;
            } else {
                isValid = true;
            }
        } while (!isValid);
        
        // Update user email
        user.setEmail(newEmail);
        if (user.update()) {
            ConsoleColors.printSuccess("Email updated successfully!");
            return true;
        } else {
            ConsoleColors.printError("Failed to update email. Please try again later.");
            return false;
        }
    }
}
