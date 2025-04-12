package model;

import controller.ConsoleController;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PetSearcher implements IPetSearcher {

    private final ConsoleController consoleController;

    // Constructor that accepts ConsoleController
    public PetSearcher(ConsoleController consoleController) {
        this.consoleController = consoleController;
    }

    @Override
    public List<PetWithScore> searchPetsByTypeAndBreed(String petType, String petBreed, String csvPath) {
        // TODO: Step 1: Open the CSV file located at csvPath and read its content.

        // TODO: Step 2: For each line in the CSV file:
        // - Parse the pet data (name, breed, type, score, etc.)
        // - Check if the pet matches the provided petType and petBreed.
        // - If breed is empty, consider all pets of the given type.

        // TODO: Step 3: For matching pets, create a PetWithScore object (pet, compatibilityScore)
        // and add it to a list.

        // TODO: Step 4: Pass the list of PetWithScore objects to the ConsoleController to display the results.
        // Call consoleController.displaySearchResult(searchResults) here

        return null;  // Placeholder return, to be implemented later
    }
}
