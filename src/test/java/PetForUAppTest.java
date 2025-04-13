import Database.PetDatabase;
import controller.ArgsController;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PetForUAppTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

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

    @Test
    public void testArgsControllerHelp() {
        // Arrange
        String[] args = {"--help"};
        ArgsController controller = new ArgsController(args);

        // Act
        boolean isHelp = controller.isHelp();
        String helpText = controller.getHelp();

        // Assert
        assertTrue(isHelp, "Should detect help flag");
        assertNotNull(helpText, "Help text should not be null");
        assertTrue(helpText.contains("Usage") && helpText.contains("Options"),
                "Help text should contain usage information");
    }

    @Test
    public void testArgsControllerNoHelp() {
        // Arrange
        String[] args = {};
        ArgsController controller = new ArgsController(args);

        // Act
        boolean isHelp = controller.isHelp();

        // Assert
        assertFalse(isHelp, "Should not detect help flag");
    }

    @Test
    public void testPetDatabaseGetAllPets() {
        // Act
        var pets = PetDatabase.getAllPets();

        // Assert
        assertNotNull(pets, "Pet list should not be null");
        assertFalse(pets.isEmpty(), "Pet list should not be empty");
    }

    @Test
    public void testMainMethodWithHelpArgs() {
        // Arrange - mock command line args
        String[] args = {"--help"};

        // Act
        PetForUApp.main(args);

        // Assert
        String output = outContent.toString();
        assertTrue(output.contains("Usage") && output.contains("Options"),
                "Output should contain help information");
    }

    @Test
    public void testMainMethodInteractiveMode() {
        // This test is more complex as it simulates user interaction
        // We'll only test a minimal path that exits quickly

        // Arrange - mock user input for MBTI questions, then quit
        String input = String.join(System.lineSeparator(),
                "Y", "Y", "Y", "Y",              // MBTI questions (ISTJ)
                "Male", "Any",                   // Gender preferences
                "5", "400", "100", "N", "Y", "3", // Other user details
                "Q"                              // Quit immediately
        );

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Act
        try {
            PetForUApp.main(new String[]{});

            // Assert
            String output = outContent.toString();
            assertTrue(output.contains("Welcome to PetMatcher"),
                    "Output should contain welcome message");
            assertTrue(output.contains("Menu:") && output.contains("View best matched pet"),
                    "Output should show menu");
        } catch (Exception e) {
            fail("Main method threw exception: " + e.getMessage());
        }
    }

    @Test
    public void testOutputDirectoryCreation() {
        // Arrange - Delete output directory if it exists
        File outputDir = new File("output");
        if (outputDir.exists()) {
            for (File file : outputDir.listFiles()) {
                file.delete();
            }
            outputDir.delete();
        }

        // Arrange user input (same as above)
        String input = String.join(System.lineSeparator(),
                "Y", "Y", "Y", "Y",              // MBTI questions
                "Male", "Any",                   // Gender
                "5", "400", "100", "N", "Y", "3", // Details
                "Q"                              // Quit
        );

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Act
        PetForUApp.main(new String[]{});

        // Assert
        assertTrue(outputDir.exists(), "Output directory should be created");
        assertTrue(new File("output/pet_compatibility.csv").exists(),
                "CSV file should be created");
    }
}