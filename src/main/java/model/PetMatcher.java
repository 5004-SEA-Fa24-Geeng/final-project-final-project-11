package model;

import Database.PetDatabase;
import java.util.*;

/**
 * PetMatcher is responsible for matching a user to the best pet based on the compatibility score
 * calculated by PetSorter and written to a CSV file.
 * The class follows these steps:
 * 1. Read the CSV file containing pet data and their compatibility scores.
 * 2. Return the first pet from the sorted list (the best match).
 * 3. If the first pet’s compatibility score is below 75%, suggest that it might not be the best time for the user to get a pet.
 * 4. After identifying the best match, display the pet's info and open the image on the console.
 */
public class PetMatcher implements IPetMatcher {

    /**
     * This method reads the pets' data from a CSV file, sorts them by compatibility score, and returns the best match.
     * If the first pet's compatibility score is below 75%, a message will be printed to suggest that it might not be
     * the best time for the user to get a pet.
     *
     * @param user The user for whom the best match pet is being sought.
     * @param csvFilePath The path to the CSV file containing the pet data and their compatibility scores.
     * @return The pet with the highest compatibility score.
     */
    @Override
    public Pet sortAndEvaluateBestMatch(User user, String csvFilePath) {

        // TODO: Step 1: Read the CSV file containing pets and their compatibility scores.
        // - Use BufferedReader to read the file.
        // - Parse each line to get pet details (name, breed, score, image path).

        // TODO: Step 2:Find the pets by compatibility score.
        // - The first pet in the sorted list is the best match.

        // TODO: Step 3: Check if the compatibility score of the first pet is below 80%.
        // - If it is, print a message suggesting that it might not be the best time for the user to get a pet.

        // TODO: Step 4: Return the first pet (the best match).
        // - Return the pet with the highest compatibility score (first in the sorted list).

        return null;
    }
}
