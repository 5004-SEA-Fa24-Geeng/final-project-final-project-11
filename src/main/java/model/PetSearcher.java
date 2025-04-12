package model;

import controller.ConsoleController;
import java.util.List;

/**
 * PetSearcher is responsible for searching pets by type and optional breed
 * from the sorted compatibility CSV file written by PetSorter.
 * This class reads the CSV, filters pets by user input, and passes results to ConsoleController.
 */
public class PetSearcher implements IPetSearcher {

    private final ConsoleController consoleController;

    public PetSearcher(ConsoleController consoleController) {
        this.consoleController = consoleController;
    }

    /**
     * Searches for pets by type and optional breed and sends results to the console.
     *
     * @param petType  the pet type to search for (e.g., Dog, Cat)
     * @param petBreed optional breed (can be empty to match all breeds of that type)
     * @param csvPath  the CSV file written by PetSorter
     */
    public void searchAndDisplay(String petType, String petBreed, String csvPath) {
        // TODO: Step 1: Read the CSV file located at csvPath.
        // TODO: Step 2: Parse each pet's data from each line.
        // TODO: Step 3: Check if the pet matches the given type (case-insensitive).
        //              If breed is non-empty, also check for breed match.
        // TODO: Step 4: Create PetWithScore objects for matched pets.
        // TODO: Step 5: Call consoleController.displaySearchResult(matchingPets) to display results.
    }
}
