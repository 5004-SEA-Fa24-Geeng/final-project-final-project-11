package model;

import Database.PetDatabase;
import java.util.List;

/**
 * PetFilter is responsible for filtering pets based on their compatibility score.
 * This class filters the pets and delegates the printing of the filtered pets' details to the ConsoleController.
 * The pets are already sorted by compatibility score with tie-breaking logic applied (in descending order).
 */
public class PetFilter implements IPetFilter {

    @Override
    public List<Pet> filterPetsByCompatibility(List<Pet> pets) {
        // TODO: Step 1: Initialize an empty list to store pets with compatibility > 85%.

        // TODO: Step 2: Read through the CSV file containing pets and their compatibility scores.
        // - Open and read the "output/pet_compatibility.csv" file.
        // - Iterate through the file and extract each pet's compatibility score.
        // - Maintain the order of the pets as they appear in the CSV, which is already sorted by compatibility score
        //   and includes tie-breaking logic.

        // TODO: Step 3: Filter pets with compatibility > 80%.
        // - For each pet, check if its compatibility score is greater than 80%.
        // - If the pet's score is greater than 85%, add it to the filtered list.
        // - Ensure the filtered list preserves the order from the CSV, which already includes tie-breaking logic.

        // TODO: Step 4: Return the filtered list of pets.
        // - Return the list that contains only pets with compatibility > 85%.
        // - The list should preserve the order and tie-breaking rules applied by PetSorter.

        // TODO: Step 5: Ask ConsoleController to print the filtered pets' details.
        // - Pass the filtered list to ConsoleController's method to print the name, breed, score, and image path of each filtered pet.

        return null;  // Placeholder return, to be implemented later
    }
}
