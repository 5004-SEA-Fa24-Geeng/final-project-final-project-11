package controller;

import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link PetManager}, verifying that:
 * - A User is initialized correctly from constructor parameters
 * - The CSV path is as expected
 * - The output CSV file is created upon manager instantiation
 */
class PetManagerTest {

    private static final String EXPECTED_CSV_PATH = "output/pet_compatibility.csv";

    private PetManager manager;

    @BeforeEach
    void setUp() {
        // Construct PetManager with specific user preferences and MBTI flags (ISTJ)
        manager = new PetManager(
                /* gender */               "Female",
                /* preferredPetGender */   "Male",
                /* mbtiI */                1,
                /* mbtiS */                1,
                /* mbtiT */                1,
                /* mbtiJ */                1,
                /* energyLevel */          5,
                /* space */                30.0,
                /* budget */               20.0,
                /* isAllergic */           false,
                /* hasYard */              true,
                /* timePerDay */           2.0
        );
    }

    @AfterEach
    void tearDown() {
        // Remove the generated CSV file, if it exists
        File output = new File(manager.getCsvPath());
        if (output.exists()) {
            output.delete();
        }
    }

    /**
     * Verifies that the User returned by PetManager matches
     * the constructor parameters for gender, MBTI, and other preferences.
     */
    @Test
    void testUserInitialization() {
        User user = manager.getUser();

        assertEquals("Female", user.getGender(),                "Gender should match");
        assertEquals("Male",   user.getPreferredPetGender(),    "Preferred pet gender should match");
        assertEquals("ISTJ",   user.getMbti(),                  "MBTI should be concatenated correctly");
        assertEquals(5,        user.getEnergyLevel(),           "Energy level should match");
        assertEquals(30.0,     user.getSpace(),                 "Space value should match");
        assertEquals(20.0,     user.getBudget(),                "Budget should match");
        assertFalse(user.isAllergic(),                          "Allergy flag should match");
        assertTrue(user.hasYard(),                              "Yard availability should match");
        assertEquals(2.0,      user.getTimePerDay(),            "Daily time commitment should match");
    }

    /**
     * Verifies that the CSV path returned by PetManager
     * matches the expected static path.
     */
    @Test
    void testCsvPath() {
        assertEquals(EXPECTED_CSV_PATH, manager.getCsvPath(),
                "CSV path should match the expected output location");
    }

    /**
     * Verifies that the output CSV file is created when the
     * PetManager is instantiated (or upon first access).
     */
    @Test
    void testOutputCsvIsCreated() {
        File outputFile = new File(manager.getCsvPath());
        assertTrue(outputFile.exists(), "Expected the CSV file to be created at instantiation");
    }
}
