package controller;

import controller.ConsoleController;
import model.CompatibilityCalculator;
import model.Pet;
import model.PetWithScore;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ConsoleController}, verifying console output
 * for various display methods and internal formatting utilities.
 */
@ExtendWith(MockitoExtension.class)
public class ConsoleControllerTest {

    private static final String TEST_DIR      = "test_output";
    private static final String TEST_CSV_PATH = TEST_DIR + "/pet_test.csv";

    private ConsoleController consoleController;
    private User testUser;
    private CompatibilityCalculator calculator;

    // Captured output stream
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream originalOut;

    @BeforeEach
    public void setUp() throws IOException {
        // Redirect System.out to capture console output
        originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Ensure test directory exists
        new File(TEST_DIR).mkdirs();

        // Prepare a CSV with sample data
        createTestCsv();

        // Initialize user and compatibility calculator
        testUser   = new User("Male", "Any", "ENFJ", 7, 500, 200, false, true, 4.0);
        calculator = new CompatibilityCalculator();

        // Create the controller under test
        consoleController = new ConsoleController(testUser, calculator);
    }

    @AfterEach
    public void tearDown() {
        // Restore original System.out
        System.setOut(originalOut);

        // Clean up test files and directory
        new File(TEST_CSV_PATH).delete();
        new File(TEST_DIR).delete();
    }

    /**
     * Verifies that displayBestMatch shows a pet name and its compatibility score.
     */
    @Test
    public void testDisplayBestMatch() {
        consoleController.displayBestMatch(TEST_CSV_PATH);
        String output = outContent.toString();

        assertTrue(output.contains("Bella"),    "Output should include the pet's name");
        assertTrue(output.contains("95%"),      "Output should include the compatibility score");
    }

    /**
     * When all scores are low, displayBestMatch should indicate no suitable pets.
     */
    @Test
    public void testDisplayNoCompatiblePets() throws IOException {
        createLowScoreCsv();
        consoleController.displayBestMatch(TEST_CSV_PATH);
        String output = outContent.toString();

        assertTrue(
                output.contains("NO COMPATIBLE PETS FOUND")
                        || output.contains("LOW COMPATIBILITY")
                        || output.contains("might not be the best time"),
                "Output should indicate low or no compatibility"
        );
    }

    /**
     * displayRecommendedPets should list high-scoring pets only.
     */
    @Test
    public void testDisplayRecommendedPets() {
        consoleController.displayRecommendedPets(TEST_CSV_PATH);
        String output = outContent.toString();

        assertTrue(output.contains("Bella"),   "Should contain first recommended pet");
        assertTrue(output.contains("Charlie"), "Should contain second recommended pet");
        assertFalse(output.contains("Luna"),   "Should omit low-compatibility pet");
    }

    /**
     * displayAllPetsWithScores should list every pet and mention scores.
     */
    @Test
    public void testDisplayAllPetsWithScores() {
        consoleController.displayAllPetsWithScores(TEST_CSV_PATH);
        String output = outContent.toString();

        assertTrue(
                output.contains("ALL PETS")
                        || output.contains("COMPATIBILITY SCORES"),
                "Should include header indicating all pets or scores"
        );
        assertTrue(output.contains("Bella"),   "Should list first pet");
        assertTrue(output.contains("Charlie"), "Should list second pet");
        assertTrue(output.contains("Milo"),    "Should list third pet");
    }

    /**
     * displaySearchResult should show search header, pet names, and scores.
     */
    @Test
    public void testDisplaySearchResult() {
        List<PetWithScore> results = new ArrayList<>();
        Pet pet1 = new Pet("Bella","Dog","Beagle","Female","ISFJ",
                7,50.0,30.0,false,true,2.0,"images/bella.jpg");
        Pet pet2 = new Pet("Charlie","Dog","Golden Retriever","Male","ENTJ",
                9,75.0,50.0,false,true,2.5,"images/charlie.jpg");

        results.add(new PetWithScore(pet1, 0.95));
        results.add(new PetWithScore(pet2, 0.85));

        consoleController.displaySearchResult(results);
        String output = outContent.toString();

        assertTrue(
                output.contains("SEARCH")
                        || output.contains("RESULTS"),
                "Should include a search results header"
        );
        assertTrue(output.contains("Bella"),    "Should list first search result");
        assertTrue(output.contains("Charlie"),  "Should list second search result");
        assertTrue(output.contains("95%")
                && output.contains("85%"),      "Should display compatibility percentages");
    }

    /**
     * displaySearchResult with empty list should indicate no results.
     */
    @Test
    public void testDisplaySearchResultEmpty() {
        consoleController.displaySearchResult(new ArrayList<>());
        String output = outContent.toString();

        assertTrue(
                output.contains("NO")
                        && output.contains("RESULTS"),
                "Should indicate that there are no search results"
        );
    }

    /**
     * Private method displayLowCompatibility should output low‐compatibility message.
     */
    @Test
    public void testDisplayLowCompatibility() {
        Pet pet = new Pet("Milo","Cat","Siamese","Male","INFP",
                5,25.0,20.0,false,false,1.5,"images/milo.jpg");
        PetWithScore pws = new PetWithScore(pet, 0.75);

        try {
            Method lowCompat = ConsoleController.class
                    .getDeclaredMethod("displayLowCompatibility", PetWithScore.class);
            lowCompat.setAccessible(true);
            lowCompat.invoke(consoleController, pws);

            String output = outContent.toString();
            assertTrue(output.contains("LOW")
                            || output.contains("COMPATIBILITY"),
                    "Should indicate low compatibility");
            assertTrue(output.contains("Milo"),   "Should mention the pet's name");
            assertTrue(output.contains("75%"),    "Should display the compatibility percentage");
        } catch (Exception e) {
            fail("Failed to invoke displayLowCompatibility: " + e.getMessage());
        }
    }

    /**
     * Private method formatPercentage should correctly convert a double to "NN%".
     */
    @Test
    public void testFormatPercentage() {
        try {
            Method fmt = ConsoleController.class
                    .getDeclaredMethod("formatPercentage", double.class);
            fmt.setAccessible(true);

            String p1 = (String) fmt.invoke(consoleController, 0.95);
            String p2 = (String) fmt.invoke(consoleController, 0.7523);
            String p3 = (String) fmt.invoke(consoleController, 0.0);

            assertEquals("95%", p1, "0.95 should format as 95%");
            assertEquals("75%", p2, "0.7523 should format as 75%");
            assertEquals("0%",  p3, "0.0 should format as 0%");
        } catch (Exception e) {
            fail("Failed to invoke formatPercentage: " + e.getMessage());
        }
    }

    // ---------------------- Helper Methods ----------------------

    /**
     * Creates a CSV with three pets: Bella (95%), Charlie (85%), Milo (70%).
     */
    private void createTestCsv() throws IOException {
        try (FileWriter writer = new FileWriter(TEST_CSV_PATH)) {
            writer.write("Name,Breed,Type,Score,ImagePath\n");
            writer.write("Bella,Beagle,Dog,95%,images/bella.jpg\n");
            writer.write("Charlie,Golden Retriever,Dog,85%,images/charlie.jpg\n");
            writer.write("Milo,Siamese,Cat,70%,images/milo.jpg\n");
        }
    }

    /**
     * Creates a CSV with all low scores: 75%, 65%, 55%.
     */
    private void createLowScoreCsv() throws IOException {
        try (FileWriter writer = new FileWriter(TEST_CSV_PATH)) {
            writer.write("Name,Breed,Type,Score,ImagePath\n");
            writer.write("Bella,Beagle,Dog,75%,images/bella.jpg\n");
            writer.write("Charlie,Golden Retriever,Dog,65%,images/charlie.jpg\n");
            writer.write("Milo,Siamese,Cat,55%,images/milo.jpg\n");
        }
    }
}
