import Database.PetDatabase;
import controller.ArgsController;
import controller.PetManager;
import controller.ConsoleController;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@code PetForUApp} application.
 * <p>
 * This test class verifies both the logic and user interaction flow of the main application.
 * It includes tests for command-line arguments, CSV generation, menu choices, and invalid input handling.
 */
@ExtendWith(MockitoExtension.class)
public class PetForUAppTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    /**
     * Redirects System.out before each test to capture console output.
     */
    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    /**
     * Restores original System.in and System.out after each test.
     * Also deletes the output CSV file if generated.
     */
    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);

        // Clean up output directory
        File outputDir = new File("output");
        if (outputDir.exists()) {
            File csvFile = new File("output/pet_compatibility.csv");
            if (csvFile.exists()) {
                csvFile.delete();
            }
        }
    }

    /**
     * Tests that ArgsController correctly detects the '--help' flag.
     */
    @Test
    public void testArgsControllerHelp() {
        String[] args = {"--help"};
        ArgsController controller = new ArgsController(args);
        assertTrue(controller.isHelp());
        assertNotNull(controller.getHelp());
        assertTrue(controller.getHelp().contains("Usage") && controller.getHelp().contains("Options"));
    }

    /**
     * Tests that ArgsController correctly identifies when help is not requested.
     */
    @Test
    public void testArgsControllerNoHelp() {
        String[] args = {};
        ArgsController controller = new ArgsController(args);
        assertFalse(controller.isHelp());
    }

    /**
     * Verifies that the pet database is populated successfully.
     */
    @Test
    public void testPetDatabaseGetAllPets() {
        var pets = PetDatabase.getAllPets();
        assertNotNull(pets);
        assertFalse(pets.isEmpty());
    }

    /**
     * Runs the app with '--help' argument and checks if the usage message is printed.
     */
    @Test
    public void testMainMethodWithHelpArgs() {
        String[] args = {"--help"};
        PetForUApp.main(args);
        String output = outContent.toString();
        assertTrue(output.contains("Usage") && output.contains("Options"));
    }

    /**
     * Simulates full user input interaction and checks if prompts and menu appear correctly.
     */
    @Test
    public void testMainMethodInteractiveMode() {
        String input = String.join(System.lineSeparator(),
                "Y", "Y", "Y", "Y",
                "Male", "Any",
                "5", "400", "100", "N", "Y", "3",
                "Q");
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        try {
            PetForUApp.main(new String[]{});
            String output = outContent.toString();
            assertTrue(output.contains("Welcome to PetMatcher"));
            assertTrue(output.contains("Menu:") && output.contains("View best matched pet"));
        } catch (Exception e) {
            fail("Main method threw exception: " + e.getMessage());
        }
    }

    /**
     * Ensures the output directory and compatibility CSV are created after program execution.
     */
    @Test
    public void testOutputDirectoryCreation() {
        File outputDir = new File("output");
        if (outputDir.exists()) {
            for (File file : outputDir.listFiles()) file.delete();
            outputDir.delete();
        }

        String input = String.join(System.lineSeparator(),
                "Y", "Y", "Y", "Y",
                "Male", "Any",
                "5", "400", "100", "N", "Y", "3",
                "Q");
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        PetForUApp.main(new String[]{});

        assertTrue(outputDir.exists());
        assertTrue(new File("output/pet_compatibility.csv").exists());
    }

    /**
     * Tests switch-case paths 1–4 in the menu, including search interaction.
     */
    @Test
    public void testSwitchCase1234Paths() {
        // Simulate user selecting 1, 2, 3, then 4 with type and breed, and finally Q to quit
        String simulatedInput = String.join("\n",
                "1", // Best match
                "2", // Recommended pets
                "3", // All pets
                "4", // Search
                "Dog", // type input for 4
                "",    // breed input for 4
                "Q"   // quit
        ) + "\n";

        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Create a manager with sample inputs
        PetManager manager = new PetManager("Male", "Any", 1, 1, 1, 1,
                5, 50.0, 30.0, false, true, 2.0);

        ConsoleController consoleController = new ConsoleController(manager.getUser(), new model.CompatibilityCalculator());

        // Run the menu
        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. View best matched pet");
            System.out.println("2. View recommended pets (80%+ compatibility)");
            System.out.println("3. View all pets with compatibility scores");
            System.out.println("4. Search for a pet by type or breed");
            System.out.println("Q. Quit");

            System.out.print("Your choice: ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1" -> consoleController.displayBestMatch(manager.getCsvPath());
                case "2" -> consoleController.displayRecommendedPets(manager.getCsvPath());
                case "3" -> consoleController.displayAllPetsWithScores(manager.getCsvPath());
                case "4" -> {
                    System.out.print("Enter the pet type (Dog, Cat, Hamster, Parrot): ");
                    String petType = scanner.nextLine().trim();

                    System.out.print("Enter the breed of the pet (e.g., Syrian, Goldfish, Labrador — leave empty for any breed): ");
                    String petBreed = scanner.nextLine().trim();

                    model.PetSearcher petSearcher = new model.PetSearcher(consoleController);
                    petSearcher.searchAndDisplay(petType, petBreed, manager.getCsvPath());
                }
                case "Q", "q" -> running = false;
                default -> System.out.println("Invalid option.");
            }
        }

        System.setIn(originalIn);
        System.setOut(originalOut);

        String output = outContent.toString();

        // Confirm all 4 menu actions were triggered — check presence of known phrases (light check only)
        assertTrue(output.contains("YOUR BEST PET MATCH")
                || output.contains("LOW COMPATIBILITY MATCH")
                || output.contains("NO COMPATIBLE PETS FOUND"));

        assertTrue(output.contains("HIGHLY COMPATIBLE PETS")
                || output.contains("NO COMPATIBLE PETS FOUND"));

        assertTrue(output.contains("ALL PETS WITH COMPATIBILITY SCORES"));

        assertTrue(output.contains("SEARCH RESULTS")
                || output.contains("NO SEARCH RESULTS"));
    }

    /**
     * Validates that an invalid menu input triggers an error message.
     */
    @Test
    public void testInvalidSwitchCase() {
        String input = String.join(System.lineSeparator(),
                "Y", "Y", "Y", "Y",
                "Male", "Any",
                "5", "400", "100", "N", "Y", "3",
                "Z", "Q");
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        PetForUApp.main(new String[]{});

        String output = outContent.toString();
        assertTrue(output.contains("Invalid option"));
    }

    /**
     * Tests handling of invalid input in askYesNo method.
     */
    @Test
    public void testAskYesNoInvalidEntry() {
        String input = String.join(System.lineSeparator(),
                "maybe", "Y",
                "Y", "Y", "Y",
                "Male", "Any",
                "5", "400", "100", "N", "Y", "3",
                "Q");
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        PetForUApp.main(new String[]{});

        String output = outContent.toString();
        assertTrue(output.contains("Please enter 'Y' or 'N'."));
    }

    /**
     * Tests handling of invalid and negative input in askPositiveInt and askPositiveDouble.
     */
    @Test
    public void testAskPositiveIntInvalidEntry() {
        String input = String.join(System.lineSeparator(),
                "Y", "Y", "Y", "Y",
                "Male", "Any",
                "-5", "5",
                "-10", "100",
                "abc", "100",
                "N", "Y", "3",
                "Q");
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        PetForUApp.main(new String[]{});

        String output = outContent.toString();
        assertTrue(output.contains("Please enter a positive number."));
        assertTrue(output.contains("Invalid input. Please enter a positive number."));
    }
}
