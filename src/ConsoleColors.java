/**
 * Utility class for console colors and formatting
 */
public class ConsoleColors {
    // Reset
    public static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    public static final String BLACK = "\033[0;30m";   // BLACK
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final String WHITE = "\033[0;37m";   // WHITE

    // Bold
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE

    // Underline
    public static final String BLACK_UNDERLINED = "\033[4;30m";  // BLACK
    public static final String RED_UNDERLINED = "\033[4;31m";    // RED
    public static final String GREEN_UNDERLINED = "\033[4;32m";  // GREEN
    public static final String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW
    public static final String BLUE_UNDERLINED = "\033[4;34m";   // BLUE
    public static final String PURPLE_UNDERLINED = "\033[4;35m"; // PURPLE
    public static final String CYAN_UNDERLINED = "\033[4;36m";   // CYAN
    public static final String WHITE_UNDERLINED = "\033[4;37m";  // WHITE

    // Background
    public static final String BLACK_BACKGROUND = "\033[40m";  // BLACK
    public static final String RED_BACKGROUND = "\033[41m";    // RED
    public static final String GREEN_BACKGROUND = "\033[42m";  // GREEN
    public static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW
    public static final String BLUE_BACKGROUND = "\033[44m";   // BLUE
    public static final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE
    public static final String CYAN_BACKGROUND = "\033[46m";   // CYAN
    public static final String WHITE_BACKGROUND = "\033[47m";  // WHITE

    // Utility methods
    
    /**
     * Print a header with decoration
     * @param text the header text
     */
    public static void printHeader(String text) {
        int width = 60;
        String line = "=" + "=".repeat(width - 2) + "=";
        
        System.out.println();
        System.out.println(CYAN_BOLD + line + RESET);
        
        // Calculate padding
        int padding = (width - text.length()) / 2;
        String paddedText = " ".repeat(padding) + text + " ".repeat(padding);
        // Adjust if odd length
        if (paddedText.length() < width) {
            paddedText += " ";
        }
        
        System.out.println(CYAN_BOLD + "|" + YELLOW_BOLD + paddedText + CYAN_BOLD + "|" + RESET);
        System.out.println(CYAN_BOLD + line + RESET);
        System.out.println();
    }
    
    /**
     * Print a menu option
     * @param option the option number
     * @param description the option description
     */
    public static void printOption(int option, String description) {
        System.out.println(YELLOW_BOLD + "  " + option + ". " + RESET + description);
    }
    
    /**
     * Print a success message
     * @param message the success message
     */
    public static void printSuccess(String message) {
        System.out.println(GREEN_BOLD + "\n✓ " + message + RESET + "\n");
    }
    
    /**
     * Print an error message
     * @param message the error message
     */
    public static void printError(String message) {
        System.out.println(RED_BOLD + "\n✗ " + message + RESET + "\n");
    }
    
    /**
     * Print an info message
     * @param message the info message
     */
    public static void printInfo(String message) {
        System.out.println(BLUE_BOLD + "\nℹ " + message + RESET + "\n");
    }
    
    /**
     * Print a prompt
     * @param message the prompt message
     */
    public static void printPrompt(String message) {
        System.out.print(CYAN_BOLD + message + RESET);
    }
} 