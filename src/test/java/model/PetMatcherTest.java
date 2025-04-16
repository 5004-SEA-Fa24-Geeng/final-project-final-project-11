package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link PetMatcher}, ensuring that the best pet match
 * is correctly identified (or null returned) based on compatibility CSV data.
 */
public class PetMatcherTest {

    // Directory and CSV path shared across tests
    private static final String TEST_DIR = "test_output";
    private static final String TEST_CSV_PATH = TEST_DIR + "/pet_test.csv";

    private List<Pet> testPets;
    private User testUser;
    private PetMatcher petMatcher;

    @BeforeEach
    public void setUp() throws IOException {
        // Ensure the output directory exists
        new File(TEST_DIR).mkdirs();

        // Prepare in-memory test data
        setupTestData();

        // Instantiate matcher with available pets
        petMatcher = new PetMatcher(testPets);
    }

    @AfterEach
    public void tearDown() {
        // Clean up test files and directory
        new File(TEST_CSV_PATH).delete();
        new File(TEST_DIR).delete();
    }

    /**
     * When CSV has high scores (≥ 80%), the pet with the highest score should be returned.
     */
    @Test
    public void testFindBestMatch() throws IOException {
        // Scores: Bella=90%, Max=80%, Luna=70%
        createTestCsv(90, 80, 70);

        Pet bestMatch = petMatcher.sortAndEvaluateBestMatch(testUser, TEST_CSV_PATH);

        assertNotNull(bestMatch, "Best match should not be null when high scores exist");
        assertEquals("Bella", bestMatch.getName(), "Expected Bella as the top match");
        assertEquals("Dog", bestMatch.getType(), "Expected type to be Dog");
        assertEquals("Golden Retriever", bestMatch.getBreed(), "Expected breed to be Golden Retriever");
    }

    /**
     * When all scores are below 80%, no match should be returned.
     */
    @Test
    public void testLowCompatibilityReturnsNull() throws IOException {
        // Scores: Bella=79%, Max=75%, Luna=70%
        createTestCsv(79, 75, 70);

        Pet bestMatch = petMatcher.sortAndEvaluateBestMatch(testUser, TEST_CSV_PATH);

        assertNull(bestMatch, "Best match should be null if no score ≥ 80%");
    }

    /**
     * An empty CSV (header only) should yield no match.
     */
    @Test
    public void testEmptyCsvReturnsNull() throws IOException {
        // Write only header row
        try (FileWriter writer = new FileWriter(TEST_CSV_PATH)) {
            writer.write("Name,Breed,Type,Score,ImagePath\n");
        }

        Pet bestMatch = petMatcher.sortAndEvaluateBestMatch(testUser, TEST_CSV_PATH);

        assertNull(bestMatch, "Best match should be null for empty CSV");
    }

    /**
     * A non-existent CSV path should result in no match.
     */
    @Test
    public void testNonExistentCsvReturnsNull() {
        Pet bestMatch = petMatcher.sortAndEvaluateBestMatch(testUser, "non_existent_file.csv");

        assertNull(bestMatch, "Best match should be null for missing CSV file");
    }

    /**
     * If the CSV contains a pet not in the initial pet list, it should return null.
     */
    @Test
    public void testPetNotInDatabaseReturnsNull() throws IOException {
        // Write a row for an unknown pet
        try (FileWriter writer = new FileWriter(TEST_CSV_PATH)) {
            writer.write("Name,Breed,Type,Score,ImagePath\n");
            writer.write("Unknown,Unknown Breed,Unknown Type,95%,images/unknown.jpg\n");
        }

        Pet bestMatch = petMatcher.sortAndEvaluateBestMatch(testUser, TEST_CSV_PATH);

        assertNull(bestMatch, "Best match should be null if CSV lists a pet not in database");
    }

    /**
     * A CSV with malformed headers or data should yield no match.
     */
    @Test
    public void testInvalidCsvFormatReturnsNull() throws IOException {
        // Write invalid CSV content
        try (FileWriter writer = new FileWriter(TEST_CSV_PATH)) {
            writer.write("InvalidHeader\n");
            writer.write("InvalidData\n");
        }

        Pet bestMatch = petMatcher.sortAndEvaluateBestMatch(testUser, TEST_CSV_PATH);

        assertNull(bestMatch, "Best match should be null for invalid CSV format");
    }

    // ---------------------- Helper Methods ----------------------

    /**
     * Populates the list of test pets and a sample user.
     */
    private void setupTestData() {
        testPets = new ArrayList<>();
        testPets.add(new Pet("Bella", "Dog", "Golden Retriever", "Female", "ENFP",
                7, 500, 120, false, true, 3.5, "images/bella.jpg"));
        testPets.add(new Pet("Max", "Dog", "German Shepherd", "Male", "ISTJ",
                9, 700, 150, false, true, 4.0, "images/max.jpg"));
        testPets.add(new Pet("Luna", "Cat", "Siamese", "Female", "INTJ",
                5, 200, 80, true, false, 1.5, "images/luna.jpg"));

        // User preferences used in matching
        testUser = new User("Male", "Any", "ENFJ", 6, 1000, 200, false, true, 4.0);
    }

    /**
     * Writes a CSV file with three pets and their compatibility scores.
     *
     * @param score1 Bella's score percentage (e.g., 90 for "90%")
     * @param score2 Max's score percentage
     * @param score3 Luna's score percentage
     * @throws IOException if file operations fail
     */
    private void createTestCsv(int score1, int score2, int score3) throws IOException {
        try (FileWriter writer = new FileWriter(TEST_CSV_PATH)) {
            writer.write("Name,Breed,Type,Score,ImagePath\n");
            writer.write(String.format("Bella,Golden Retriever,Dog,%d%%,images/bella.jpg\n", score1));
            writer.write(String.format("Max,German Shepherd,Dog,%d%%,images/max.jpg\n", score2));
            writer.write(String.format("Luna,Siamese,Cat,%d%%,images/luna.jpg\n", score3));
        }
    }
}
