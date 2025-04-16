package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This test class verifies the correctness of the CompatibilityCalculator,
 * which computes compatibility scores between users and pets based on
 * MBTI, gender, allergy status, space, time, budget, and yard availability.
 */
public class CompatibilityCalculatorTest {

    private CompatibilityCalculator calculator;
    private User baseUser;

    /**
     * Set up a base user with ideal conditions for testing.
     * This user will be reused in most tests unless otherwise specified.
     */
    @BeforeEach
    public void setUp() {
        calculator = new CompatibilityCalculator();

        baseUser = new User(
                "Male",                // user gender
                "Female",              // preferred pet gender
                "ENFP",                // MBTI type
                7,                     // energy level
                500,                   // space in square feet
                200,                   // monthly budget
                false,                // has no allergies
                true,                 // has a yard
                4.0                   // time available per day
        );
    }

    /**
     * Test case: A pet with perfect matching attributes should return a score of 1.0.
     */
    @Test
    public void testPerfectMatchReturnsFullScore() {
        Pet pet = new Pet("Bella", "Dog", "Golden Retriever", "Female", "ENFP",
                7, 500, 200, false, true, 4.0, "images/bella.jpg");

        double score = calculator.calculate(baseUser, pet);
        assertEquals(1.0, score, 0.0001, "Expected full compatibility score for perfect match");
    }

    /**
     * Test case: MBTI mismatch should result in a reduced compatibility score.
     */
    @Test
    public void testMBTIMismatchReducesScore() {
        Pet pet = new Pet("Luna", "Cat", "Siamese", "Female", "ISTJ",
                7, 500, 200, false, true, 4.0, "images/luna.jpg");

        double score = calculator.calculate(baseUser, pet);
        assertTrue(score < 1.0, "MBTI mismatch should reduce compatibility score");
    }

    /**
     * Test case: Preferred pet gender should slightly boost the score.
     */
    @Test
    public void testGenderPreferenceAddsScore() {
        // Matches preferred gender
        Pet femalePet = new Pet("Coco", "Dog", "Bulldog", "Female", "ENFP",
                7, 500, 200, false, true, 4.0, "images/coco.jpg");

        // Does not match preferred gender
        Pet malePet = new Pet("Max", "Dog", "Bulldog", "Male", "ENFP",
                7, 500, 200, false, true, 4.0, "images/max.jpg");

        double score1 = calculator.calculate(baseUser, femalePet);
        double score2 = calculator.calculate(baseUser, malePet);

        assertTrue(score1 > score2, "Preferred gender pet should have a higher score");
    }

    /**
     * Test case: If no pet gender is preferred, both genders should score equally.
     */
    @Test
    public void testNoGenderPreferenceYieldsEqualScore() {
        // Create a user who accepts any pet gender
        User user = new User("Female", "Any", "ENFP", 7, 500, 200, false, true, 4.0);

        Pet malePet = new Pet("Leo", "Dog", "Poodle", "Male", "ENFP",
                7, 500, 200, false, true, 4.0, "images/leo.jpg");

        Pet femalePet = new Pet("Lily", "Dog", "Poodle", "Female", "ENFP",
                7, 500, 200, false, true, 4.0, "images/lily.jpg");

        double score1 = calculator.calculate(user, malePet);
        double score2 = calculator.calculate(user, femalePet);

        assertEquals(score1, score2, 0.0001, "Both pets should yield equal score when gender doesn't matter");
    }

    /**
     * Test case: Allergic user should not be matched with an allergenic pet (score = 0).
     */
    @Test
    public void testAllergyBlocksMatch() {
        // User with pet allergies
        User allergicUser = new User("Male", "Female", "ENFP", 7, 500, 200, true, true, 4.0);

        // Pet that is allergenic
        Pet allergenicPet = new Pet("Sneeze", "Cat", "Persian", "Female", "ENFP",
                7, 500, 200, true, true, 4.0, "images/sneeze.jpg");

        double score = calculator.calculate(allergicUser, allergenicPet);
        assertEquals(0.0, score, 0.0001, "Allergenic pets should be excluded for allergic users");
    }

    /**
     * Test case: A pet with cost higher than user's budget should result in a lower score.
     */
    @Test
    public void testInsufficientBudgetReducesScore() {
        // User with low budget
        User user = new User("Male", "Female", "ENFP", 7, 500, 100, false, true, 4.0);

        // Expensive pet
        Pet pet = new Pet("Lux", "Dog", "Chow Chow", "Female", "ENFP",
                7, 500, 200, false, true, 4.0, "images/lux.jpg");

        double score = calculator.calculate(user, pet);
        assertTrue(score < 1.0, "Expensive pet should reduce score for low-budget user");
    }

    /**
     * Test case: A pet that needs more space than the user has should yield lower score.
     */
    @Test
    public void testInsufficientSpaceReducesScore() {
        User user = new User("Male", "Female", "ENFP", 7, 300, 200, false, true, 4.0);

        Pet pet = new Pet("Tank", "Dog", "Great Dane", "Female", "ENFP",
                7, 500, 200, false, true, 4.0, "images/tank.jpg");

        double score = calculator.calculate(user, pet);
        assertTrue(score < 1.0, "Large pets should score lower for users with limited space");
    }

    /**
     * Test case: A pet that needs a yard should receive a lower score if user lacks one.
     */
    @Test
    public void testLackOfYardReducesScore() {
        User user = new User("Male", "Female", "ENFP", 7, 500, 200, false, false, 4.0);

        Pet pet = new Pet("Buddy", "Dog", "Collie", "Female", "ENFP",
                7, 500, 200, false, true, 4.0, "images/buddy.jpg");

        double score = calculator.calculate(user, pet);
        assertTrue(score < 1.0, "Lack of yard should lower score if pet needs outdoor space");
    }

    /**
     * Test case: A user with very little time should score lower with high-time pets.
     */
    @Test
    public void testLowTimeAvailabilityReducesScore() {
        User user = new User("Male", "Female", "ENFP", 7, 500, 200, false, true, 0.5);

        Pet pet = new Pet("Zoom", "Dog", "Husky", "Female", "ENFP",
                7, 500, 200, false, true, 4.0, "images/zoom.jpg");

        double score = calculator.calculate(user, pet);
        assertTrue(score < 1.0, "Active pets should score lower if user lacks time");
    }
}
