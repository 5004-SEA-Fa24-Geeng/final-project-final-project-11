package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link PetSorter}, ensuring that pets are correctly
 * sorted according to compatibility and exported to CSV, including
 * tie-breaking and allergen handling.
 */
public class PetSorterTest {

    private static final String TEST_DIR      = "test_output";
    private static final String TEST_CSV_PATH = TEST_DIR + "/pet_test.csv";

    private List<Pet> testPets;
    private User testUser;
    private ICompatibilityCalculator calculator;
    private PetSorter petSorter;

    @BeforeEach
    public void setUp() {
        // Ensure test directory exists
        new File(TEST_DIR).mkdirs();

        // Prepare test data
        setupTestData();

        // Use a real compatibility calculator
        calculator = new CompatibilityCalculator();
        petSorter  = new PetSorter(testUser, calculator);
    }

    @AfterEach
    public void tearDown() {
        // Remove test CSV and directory
        new File(TEST_CSV_PATH).delete();
        new File(TEST_DIR).delete();
    }

    /**
     * Verifies that sorting and exporting writes a CSV with:
     * - A header row
     * - One row per pet
     * - Pets ordered by descending compatibility score
     */
    @Test
    public void testSortAndExportToCSV() throws IOException {
        // Act: sort and write CSV
        petSorter.sortAndExportToCSV(testPets, TEST_CSV_PATH);

        // Assert: file exists
        File csvFile = new File(TEST_CSV_PATH);
        assertTrue(csvFile.exists(), "CSV file should be created");

        // Read and inspect CSV contents
        List<String[]> csvLines = readCSV(TEST_CSV_PATH);
        assertNotNull(csvLines, "CSV data should not be null");
        assertTrue(csvLines.size() > 1, "CSV should contain header and pet rows");

        // Header checks
        String[] header = csvLines.get(0);
        assertEquals(5, header.length, "Header should have 5 columns");
        assertArrayEquals(new String[]{"Name","Breed","Type","Score","ImagePath"}, header, "Header columns should match");

        // Row count = pets + header
        assertEquals(testPets.size() + 1, csvLines.size(), "CSV should contain one row per pet plus header");

        // The first data row should be the pet with highest calculated score
        String expectedTopName = findPetWithHighestScore();
        assertEquals(expectedTopName, csvLines.get(1)[0],
                "Top row should be pet with highest compatibility");
    }

    /**
     * When two pets share the same compatibility score, the one matching
     * the user's preferred gender should come first.
     */
    @Test
    public void testTieBreakingLogic() throws IOException {
        // Both pets will calculate to the same score
        Pet pet1 = new Pet("Pet1","Dog","Breed1",
                testUser.getPreferredPetGender(), testUser.getMbti(),
                testUser.getEnergyLevel(), testUser.getSpace()*0.5,
                testUser.getBudget()*0.5,false, testUser.hasYard(),
                testUser.getTimePerDay()*0.5,"images/pet1.jpg");
        Pet pet2 = new Pet("Pet2","Dog","Breed2",
                /* opposite gender */ testUser.getPreferredPetGender().equals("Male") ? "Female" : "Male",
                testUser.getMbti(), testUser.getEnergyLevel(),
                testUser.getSpace()*0.5, testUser.getBudget()*0.5,
                false, testUser.hasYard(),
                testUser.getTimePerDay()*0.5,"images/pet2.jpg");

        List<Pet> tiePets = new ArrayList<>();
        tiePets.add(pet2);
        tiePets.add(pet1);

        petSorter.sortAndExportToCSV(tiePets, TEST_CSV_PATH);
        List<String[]> csvLines = readCSV(TEST_CSV_PATH);

        assertEquals("Pet1", csvLines.get(1)[0], "Preferred-gender pet should appear first");
    }

    /**
     * Exporting an empty pet list should still produce a CSV
     * with only the header row and no exception.
     */
    @Test
    public void testEmptyPetList() {
        assertDoesNotThrow(() ->
                        petSorter.sortAndExportToCSV(new ArrayList<>(), TEST_CSV_PATH),
                "Should not throw on empty list"
        );

        try {
            List<String[]> csvLines = readCSV(TEST_CSV_PATH);
            assertEquals(1, csvLines.size(), "Only header row should be present");
        } catch (IOException e) {
            fail("Reading CSV failed: " + e.getMessage());
        }
    }

    /**
     * If the user is allergic, any pet marked allergenic must receive
     * a 0% score and be placed after non-allergenic pets.
     */
    @Test
    public void testAllergicUserWithAllergenicPet() throws IOException {
        // Create an allergic user
        User allergicUser = new User(
                testUser.getGender(), testUser.getPreferredPetGender(),
                testUser.getMbti(), testUser.getEnergyLevel(),
                testUser.getSpace(), testUser.getBudget(),
                /* allergic */ true, testUser.hasYard(), testUser.getTimePerDay()
        );
        PetSorter allergySorter = new PetSorter(allergicUser, calculator);

        Pet allergenicPet = new Pet("Sneeze","Cat","Allergenic","Male",
                testUser.getMbti(), testUser.getEnergyLevel(),
                testUser.getSpace(), testUser.getBudget(),
                true, /* no yard */ false, testUser.getTimePerDay(),
                "images/sneeze.jpg");
        Pet hypoPet = new Pet("Safe","Dog","Hypoallergenic","Male",
                testUser.getMbti(), testUser.getEnergyLevel(),
                testUser.getSpace(), testUser.getBudget(),
                false, false, testUser.getTimePerDay(),
                "images/safe.jpg");

        List<Pet> allergyPets = List.of(allergenicPet, hypoPet);
        allergySorter.sortAndExportToCSV(allergyPets, TEST_CSV_PATH);

        List<String[]> csvLines = readCSV(TEST_CSV_PATH);
        // Header + 2 pets = 3 rows
        assertEquals("Safe", csvLines.get(1)[0],  "Non-allergenic pet should be listed first");
        assertEquals("Sneeze", csvLines.get(2)[0], "Allergenic pet should be last");
        assertTrue(csvLines.get(2)[3].endsWith("0%"), "Allergenic pet must have 0% score");
    }

    // ---------------------- Helper Methods ----------------------

    /**
     * Initializes testPets and testUser:
     * - Three sample pets with varied attributes
     * - A user whose preferences align best with the first pet
     */
    private void setupTestData() {
        testPets = new ArrayList<>();
        testPets.add(new Pet("Bella","Dog","Beagle","Female","ISFJ",
                7, 50.0, 30.0, false, true, 2.0, "images/bella.jpg"));
        testPets.add(new Pet("Charlie","Dog","Golden Retriever","Male","ENTJ",
                9, 75.0, 50.0, false, true, 2.5, "images/charlie.jpg"));
        testPets.add(new Pet("Milo","Cat","Siamese","Male","INFP",
                5, 25.0, 20.0, false, false,1.5, "images/milo.jpg"));

        // User preferences match Bella best
        testUser = new User("Male", "Female", "ISFJ",
                7, 500, 200, false, true, 4.0);
    }

    /**
     * Reads a CSV file into a list of String arrays (one per line, split on commas).
     */
    private List<String[]> readCSV(String path) throws IOException {
        List<String[]> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String row;
            while ((row = reader.readLine()) != null) {
                lines.add(row.split(","));
            }
        }
        return lines;
    }

    /**
     * Determines which pet has the highest compatibility score
     * according to the calculator and testUser.
     */
    private String findPetWithHighestScore() {
        double maxScore = Double.NEGATIVE_INFINITY;
        String bestName = null;
        for (Pet pet : testPets) {
            double score = calculator.calculate(testUser, pet);
            if (score > maxScore) {
                maxScore = score;
                bestName = pet.getName();
            }
        }
        return bestName;
    }
}
