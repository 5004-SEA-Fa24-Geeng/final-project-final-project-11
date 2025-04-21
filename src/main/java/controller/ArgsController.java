package controller;

/**
 * The {@code ArgsController} class handles parsing of command-line arguments.
 * It determines whether the user has requested help information by checking
 * if specific flags (e.g., {@code --help} or {@code -h}) are present in the input.
 */
public class ArgsController {

    /** Indicates whether the help flag was provided. */
    private final boolean helpRequested;

    /**
     * Constructs an {@code ArgsController} with the given command-line arguments.
     *
     * @param args the command-line arguments passed to the application
     */
    public ArgsController(String[] args) {
        helpRequested = args.length > 0 &&
                (args[0].equals("--help") || args[0].equals("-h"));
    }

    /**
     * Returns whether the help flag was requested.
     *
     * @return {@code true} if help was requested, {@code false} otherwise
     */
    public boolean isHelp() {
        return helpRequested;
    }

    /**
     * Returns the help message string shown to the user.
     *
     * @return a formatted usage message for the application
     */
    public String getHelp() {
        return """
            Usage: java DNInfoApp [--help]
            
            Options:
              --help, -h    Show this help message
            """;
    }
}
