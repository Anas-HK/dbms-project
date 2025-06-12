import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Class for user registration functionality
 */
public class Signup {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final String PHONE_REGEX = "^[0-9]{10,15}$";
    
    private Scanner scanner;
    
    public Signup() {
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Register a new user
     * @return true if registration was successful, false otherwise
     */
    public boolean newSignup() {
        ConsoleColors.printHeader("USER REGISTRATION");
        ConsoleColors.printInfo("Enter '!cancel' at any prompt to cancel registration and return to the main menu");
        
        // Get user input with validation
        String firstName = getValidInputWithCancel("First Name", 2, 50, null);
        if (firstName == null) return false; // User cancelled
        
        String lastName = getValidInputWithCancel("Last Name", 2, 50, null);
        if (lastName == null) return false; // User cancelled
        
        String email = getValidInputWithCancel("Email", 5, 100, EMAIL_REGEX);
        if (email == null) return false; // User cancelled
        
        String phone = getValidInputWithCancel("Phone Number", 10, 15, PHONE_REGEX);
        if (phone == null) return false; // User cancelled
        
        String password = getValidInputWithCancel("Password", 6, 50, null);
        if (password == null) return false; // User cancelled
        
        // Confirm password
        String confirmPassword = getValidInputWithCancel("Confirm Password", 6, 50, null);
        if (confirmPassword == null) return false; // User cancelled
        
        if (!password.equals(confirmPassword)) {
            ConsoleColors.printError("Passwords do not match. Registration cancelled.");
            return false;
        }
        
        // Create a new user object
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);
        
        // Save to database
        if (user.save()) {
            ConsoleColors.printSuccess("Registration successful! You can now log in with your email and password.");
            return true;
        } else {
            ConsoleColors.printError("Registration failed. Please try again later.");
            return false;
        }
    }
    
    /**
     * Get valid input from the user with validation and cancel option
     * @param fieldName name of the field
     * @param minLength minimum allowed length
     * @param maxLength maximum allowed length
     * @param regex optional regex pattern for validation (can be null)
     * @return the validated input or null if cancelled
     */
    private String getValidInputWithCancel(String fieldName, int minLength, int maxLength, String regex) {
        Pattern pattern = regex != null ? Pattern.compile(regex) : null;
        String input;
        boolean isValid;
        
        do {
            ConsoleColors.printPrompt(fieldName + " (or !cancel to go back): ");
            input = scanner.nextLine().trim();
            
            // Check for cancel command
            if (input.equalsIgnoreCase("!cancel")) {
                ConsoleColors.printInfo("Registration cancelled. Returning to main menu.");
                return null;
            }
            
            // Check length
            if (input.length() < minLength) {
                ConsoleColors.printError(fieldName + " must be at least " + minLength + " characters.");
                isValid = false;
            } else if (input.length() > maxLength) {
                ConsoleColors.printError(fieldName + " must be at most " + maxLength + " characters.");
                isValid = false;
            } else if (pattern != null && !pattern.matcher(input).matches()) {
                // Check regex pattern if provided
                if (regex.equals(EMAIL_REGEX)) {
                    ConsoleColors.printError("Please enter a valid email address.");
                } else if (regex.equals(PHONE_REGEX)) {
                    ConsoleColors.printError("Please enter a valid phone number (10-15 digits).");
                } else {
                    ConsoleColors.printError("Invalid " + fieldName + " format.");
                }
                isValid = false;
            } else {
                isValid = true;
            }
        } while (!isValid);
        
        return input;
    }
}
