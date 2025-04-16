package model;

import controller.ConsoleController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link PetSearcher}, verifying that search queries
 * by type and/or breed produce the correct list of {@link PetWithScore}
 * and that the {@link ConsoleController} is invoked appropriately.
 */
public class PetSearcherTest {

    private static final String TEST_DIR       = "test_output";
    private static final String TEST_CSV_PATH  = TEST_DIR + "/pet_test.csv";

    @Mock
    private ConsoleController mockConsoleController;

    private PetSearcher petSearcher;
    private List<Pet>    testPets;
    private AutoCloseable mocksCloseable;

    @BeforeEach
    public void setUp() throws IOException {
        // Initialize Mockito annotations for @Mock fields
        mocksCloseable = MockitoAnnotations.openMocks(this);

        // Ensure test directory exists
        new File(TEST_DIR).mkdirs();

        // Build in-memory pet list and corresponding CSV
        createTestDatabase();
        createTestCsvFromDatabase();

        // Create a PetSearcher that will call our mock controller
        petSearcher = new PetSearcher(mockConsoleController, testPets);
    }

    @AfterEach
    public void tearDown() throws Exception {
        // Release mock resources
        mocksCloseable.close();

        // Clean up test files
        new File(TEST_CSV_PATH).delete();
        new File(TEST_DIR).delete();
    }

    /**
     * Searching by type only should return all pets of that type.
     * Here: expect 3 dogs.
     */
    @Test
    public void testSearchByTypeOnly() {
        petSearcher.searchAndDisplay("Dog", "", TEST_CSV_PATH);

        // Capture the list passed to the controller
        ArgumentCaptor<List<PetWithScore>> captor = ArgumentCaptor.forClass(List.class);
        verify(mockConsoleController).displaySearchResult(captor.capture());

        List<PetWithScore> result = captor.getValue();
        assertEquals(3, result.size(), "Expected 3 dogs in the result");

        // All returned pets must be of type "Dog"
        result.forEach(pws ->
                assertEquals("Dog", pws.getPet().getType(), "Every result should be a Dog")
        );
    }

    /**
     * Searching by both type and breed should narrow down to the single correct pet.
     * Here: expect only the Golden Retriever "Bella".
     */
    @Test
    public void testSearchByTypeAndBreed() {
        petSearcher.searchAndDisplay("Dog", "Golden Retriever", TEST_CSV_PATH);

        ArgumentCaptor<List<PetWithScore>> captor = ArgumentCaptor.forClass(List.class);
        verify(mockConsoleController).displaySearchResult(captor.capture());

        List<PetWithScore> result = captor.getValue();
        assertEquals(1, result.size(), "Expected exactly one Golden Retriever");
        PetWithScore match = result.get(0);
        assertEquals("Bella", match.getPet().getName(), "Should find Bella");
        assertEquals("Golden Retriever", match.getPet().getBreed(), "Breed must match");
    }

    /**
     * Searching for a non-existent type should yield an empty list.
     */
    @Test
    public void testSearchNoMatches() {
        petSearcher.searchAndDisplay("Bird", "", TEST_CSV_PATH);

        ArgumentCaptor<List<PetWithScore>> captor = ArgumentCaptor.forClass(List.class);
        verify(mockConsoleController).displaySearchResult(captor.capture());

        assertTrue(captor.getValue().isEmpty(), "Expected no matches for type 'Bird'");
    }

    /**
     * Search should be case-insensitive for both type and breed inputs.
     */
    @Test
    public void testSearchCaseInsensitive() {
        petSearcher.searchAndDisplay("dog", "golden retriever", TEST_CSV_PATH);

        verify(mockConsoleController).displaySearchResult(argThat(list ->
                list.size() == 1
                        && list.get(0).getPet().getType().equalsIgnoreCase("Dog")
                        && list.get(0).getPet().getBreed().equalsIgnoreCase("Golden Retriever")
        ));
    }

    /**
     * Searching for an existing type but a non-existent breed returns empty list.
     */
    @Test
    public void testSearchExistingTypeNonExistingBreed() {
        petSearcher.searchAndDisplay("Dog", "NonExistentBreed", TEST_CSV_PATH);

        verify(mockConsoleController).displaySearchResult(argThat(List::isEmpty));
    }

    /**
     * A null breed parameter should be treated as an empty breed filter.
     */
    @Test
    public void testSearchWithNullBreed() {
        petSearcher.searchAndDisplay("Dog", null, TEST_CSV_PATH);

        verify(mockConsoleController).displaySearchResult(argThat(list ->
                list.size() == 3
                        && list.stream().allMatch(pws ->
                        pws.getPet().getType().equalsIgnoreCase("Dog"))
        ));
    }

    /**
     * If the CSV file does not exist, the searcher should return no results.
     */
    @Test
    public void testSearchNonExistentFile() {
        petSearcher.searchAndDisplay("Dog", "", "non_existent_file.csv");

        verify(mockConsoleController).displaySearchResult(argThat(List::isEmpty));
    }

    // ---------------------- Helper Methods ----------------------

    /**
     * Constructs a fixed list of 5 pets: 3 dogs and 2 cats.
     */
    private void createTestDatabase() {
        testPets = new ArrayList<>();
        testPets.add(new Pet("Bella",   "Dog", "Golden Retriever", "Female", "ENFP",
                7, 500, 120, false, true, 3.5, "images/bella.jpg"));
        testPets.add(new Pet("Max",     "Dog", "German Shepherd",  "Male",   "ISTJ",
                9, 700, 150, false, true, 4.0, "images/max.jpg"));
        testPets.add(new Pet("Charlie", "Dog", "Poodle",           "Male",   "ENFJ",
                6, 450, 130, false, false,2.5, "images/charlie.jpg"));
        testPets.add(new Pet("Luna",    "Cat", "Siamese",          "Female", "INTJ",
                5, 200,  80, true,  false,1.5, "images/luna.jpg"));
        testPets.add(new Pet("Misty",   "Cat", "Bengal",           "Female", "INFJ",
                6, 250,  85, false, false,1.8, "images/misty.jpg"));
    }

    /**
     * Writes a CSV file reflecting the test database, assigning:
     * - Dogs a score of 90%
     * - Cats a score of 80%
     *
     * @throws IOException if writing the file fails
     */
    private void createTestCsvFromDatabase() throws IOException {
        try (FileWriter writer = new FileWriter(TEST_CSV_PATH)) {
            writer.write("Name,Breed,Type,Score,ImagePath\n");
            for (Pet pet : testPets) {
                String score = pet.getType().equalsIgnoreCase("Dog") ? "90%" : "80%";
                writer.write(String.format("%s,%s,%s,%s,%s\n",
                        pet.getName(),
                        pet.getBreed(),
                        pet.getType(),
                        score,
                        pet.getImagePath()
                ));
            }
        }
    }
}
