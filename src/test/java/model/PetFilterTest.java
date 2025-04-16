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
 * Unit tests for {@link PetFilter}, verifying that pets are correctly filtered
 * based on their compatibility scores read from a CSV file.
 */
public class PetFilterTest {

    // Directory and CSV file path used for all tests
    private static final String TEST_DIR = "test_output";
    private static final String TEST_CSV_PATH = TEST_DIR + "/pet_test.csv";

    private List<Pet> testPets;
    private PetFilter petFilter;

    @BeforeEach
    public void setUp() throws IOException {
        // Ensure the test directory exists
        new File(TEST_DIR).mkdirs();

        // Prepare in-memory Pet objects for filtering
        setupTestData();

        // Initialize PetFilter pointing at our test CSV (which will be created per test)
        petFilter = new PetFilter(TEST_CSV_PATH);
    }

    @AfterEach
    public void tearDown() {
        // Remove test CSV file and directory after each test
        new File(TEST_CSV_PATH).delete();
        new File(TEST_DIR).delete();
    }

    /**
     * Verifies that only pets with compatibility scores strictly above 80% are returned.
     */
    @Test
    public void testFilterPetsAbove80Percent() throws IOException {
        // Prepare CSV with mixed scores
        createTestCsv(new int[]{95, 85, 75, 65, 50});

        // Execute filter
        List<Pet> filteredPets = petFilter.filterPetsByCompatibility(testPets);

        // Validate results: only "Bella" (95) and "Max" (85) should be included
        assertNotNull(filteredPets, "Filtered list should not be null");
        assertEquals(2, filteredPets.size(), "Expect exactly 2 pets above 80%");
        assertEquals("Bella", filteredPets.get(0).getName(), "First pet should be Bella");
        assertEquals("Max", filteredPets.get(1).getName(), "Second pet should be Max");
    }

    /**
     * Verifies that an empty result is returned when no scores exceed 80%.
     */
    @Test
    public void testFilterNoCompatiblePets() throws IOException {
        // All scores below or equal to 80%
        createTestCsv(new int[]{79, 75, 70, 65, 50});

        List<Pet> filteredPets = petFilter.filterPetsByCompatibility(testPets);

        assertNotNull(filteredPets, "Filtered list should not be null");
        assertTrue(filteredPets.isEmpty(), "Expect no pets when all scores < 80%");
    }

    /**
     * Verifies that all pets are returned when all scores are above 80%.
     */
    @Test
    public void testFilterAllCompatiblePets() throws IOException {
        // All scores well above 80%
        createTestCsv(new int[]{95, 90, 85, 82, 81});

        List<Pet> filteredPets = petFilter.filterPetsByCompatibility(testPets);

        assertNotNull(filteredPets, "Filtered list should not be null");
        assertEquals(5, filteredPets.size(), "Expect all 5 pets when all scores > 80%");
    }

    /**
     * Verifies that a score exactly at 80% is not included (boundary condition).
     */
    @Test
    public void testFilterWithEdgeCase80Percent() throws IOException {
        // One pet exactly at 80% and one above
        createTestCsv(new int[]{95, 80, 70});

        List<Pet> filteredPets = petFilter.filterPetsByCompatibility(testPets);

        assertNotNull(filteredPets, "Filtered list should not be null");
        assertEquals(1, filteredPets.size(), "Expect only pets > 80%");
        assertEquals("Bella", filteredPets.get(0).getName(), "Only Bella (95%) should match");
    }

    /**
     * Verifies behavior when the CSV exists but contains only the header (no data rows).
     */
    @Test
    public void testFilterEmptyCsv() throws IOException {
        // Create a CSV with only header row
        try (FileWriter writer = new FileWriter(TEST_CSV_PATH)) {
            writer.write("Name,Breed,Type,Score,ImagePath\n");
        }

        List<Pet> filteredPets = petFilter.filterPetsByCompatibility(testPets);

        assertNotNull(filteredPets, "Filtered list should not be null");
        assertTrue(filteredPets.isEmpty(), "Expect empty result for header-only CSV");
    }

    /**
     * Verifies behavior when the CSV file does not exist at all.
     */
    @Test
    public void testFilterNonExistentCsv() {
        // Point filter at a non-existent file
        PetFilter badFilter = new PetFilter("non_existent_file.csv");
        List<Pet> filteredPets = badFilter.filterPetsByCompatibility(testPets);

        assertNotNull(filteredPets, "Filtered list should not be null");
        assertTrue(filteredPets.isEmpty(), "Expect empty result for missing CSV");
    }

    /**
     * Verifies behavior when the CSV has an invalid structure or headers.
     */
    @Test
    public void testFilterInvalidCsvFormat() throws IOException {
        // Write malformed CSV content
        try (FileWriter writer = new FileWriter(TEST_CSV_PATH)) {
            writer.write("InvalidHeader\n");
            writer.write("BadData\n");
        }

        List<Pet> filteredPets = petFilter.filterPetsByCompatibility(testPets);

        assertNotNull(filteredPets, "Filtered list should not be null");
        assertTrue(filteredPets.isEmpty(), "Expect empty result for invalid CSV format");
    }

    /**
     * Verifies behavior when passing in an empty pet list.
     */
    @Test
    public void testFilterWithEmptyPetList() throws IOException {
        createTestCsv(new int[]{95, 85, 75});
        List<Pet> filteredPets = petFilter.filterPetsByCompatibility(new ArrayList<>());

        assertNotNull(filteredPets, "Filtered list should not be null");
        assertTrue(filteredPets.isEmpty(), "Expect empty result for empty input list");
    }

    /**
     * Verifies behavior when passing in a null pet list.
     */
    @Test
    public void testFilterWithNullPetList() throws IOException {
        createTestCsv(new int[]{95, 85, 75});
        List<Pet> filteredPets = petFilter.filterPetsByCompatibility(null);

        assertNotNull(filteredPets, "Filtered list should not be null");
        assertTrue(filteredPets.isEmpty(), "Expect empty result for null input list");
    }

    // ---------------- Helper methods for test data preparation ----------------

    /**
     * Constructs a list of five Pet objects with known names and properties.
     */
    private void setupTestData() {
        testPets = new ArrayList<>();
        testPets.add(new Pet("Bella", "Dog", "Golden Retriever", "Female", "ENFP",
                7, 500, 120, false, true, 3.5, "images/bella.jpg"));
        testPets.add(new Pet("Max", "Dog", "German Shepherd", "Male", "ISTJ",
                9, 700, 150, false, true, 4.0, "images/max.jpg"));
        testPets.add(new Pet("Luna", "Cat", "Siamese", "Female", "INTJ",
                5, 200, 80, true, false, 1.5, "images/luna.jpg"));
        testPets.add(new Pet("Rocky", "Hamster", "Dwarf", "Male", "ESFP",
                3, 50, 30, true, false, 1.0, "images/rocky.jpg"));
        testPets.add(new Pet("Daisy", "Dog", "Bulldog", "Female", "ESTP",
                6, 400, 100, false, true, 2.5, "images/daisy.jpg"));
    }

    /**
     * Writes a CSV file with given compatibility scores (as percentages) for the first N pets.
     *
     * @param scores array of integer percentages to write (e.g., 85 means "85%")
     * @throws IOException if writing fails
     */
    private void createTestCsv(int[] scores) throws IOException {
        try (FileWriter writer = new FileWriter(TEST_CSV_PATH)) {
            // Header
            writer.write("Name,Breed,Type,Score,ImagePath\n");

            String[] names = {"Bella", "Max", "Luna", "Rocky", "Daisy"};
            String[] breeds = {"Golden Retriever", "German Shepherd", "Siamese", "Dwarf", "Bulldog"};
            String[] types = {"Dog", "Dog", "Cat", "Hamster", "Dog"};

            // Write rows for each score up to the number of names
            for (int i = 0; i < Math.min(scores.length, names.length); i++) {
                writer.write(String.format(
                        "%s,%s,%s,%d%%,images/%s.jpg\n",
                        names[i], breeds[i], types[i], scores[i], names[i].toLowerCase()
                ));
            }
        }
    }
}
