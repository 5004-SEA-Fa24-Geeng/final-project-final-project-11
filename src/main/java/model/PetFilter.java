package model;

import Database.PetDatabase;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code PetFilter} class implements the {@link IPetFilter} interface and
 * provides functionality for filtering pets based on their compatibility scores.
 * <p>
 * This filter reads precomputed compatibility scores from a CSV file, then selects
 * only the pets from a provided list whose score is greater than 80%.
 * The filtered pets are returned in the form of {@code List<Pet>}.
 * <p>
 * It is assumed that the CSV contains rows in the format:
 * {@code Name, Breed, Type, CompatibilityScore (%), ImagePath}
 */
public class PetFilter implements IPetFilter {

    /** The path to the CSV file containing compatibility scores. */
    private final String csvPath;

    /**
     * Constructs a {@code PetFilter} using the given path to a CSV file.
     *
     * @param csvPath the file path to the CSV containing scored pet records
     */
    public PetFilter(String csvPath) {
        this.csvPath = csvPath;
    }

    /**
     * Filters the provided list of pets by matching their names and breeds against
     * entries in the CSV file. Pets with a score greater than 80% are selected.
     *
     * @param pets the list of all pets to be filtered
     * @return a list of pets whose compatibility score exceeds 80%
     */
    @Override
    public List<Pet> filterPetsByCompatibility(List<Pet> pets) {
        if (pets == null) {
            return new ArrayList<>();
        }

        List<PetWithScore> filteredPets = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvPath))) {
            // Skip header
            String line = reader.readLine();

            // Process each line of the CSV
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 5) continue; // Skip malformed rows

                String name = parts[0];
                String breed = parts[1];
                String type = parts[2];
                String scoreStr = parts[3].replace("%", "");  // Remove percent sign
                String imagePath = parts[4];

                // Parse score and normalize
                double score = Double.parseDouble(scoreStr) / 100.0;

                if (score > 0.80) {
                    for (Pet pet : pets) {
                        if (pet.getName().equals(name) && pet.getBreed().equals(breed)) {
                            filteredPets.add(new PetWithScore(pet, score));
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }

        // Return the matched pets only
        List<Pet> result = new ArrayList<>();
        for (PetWithScore petWithScore : filteredPets) {
            result.add(petWithScore.getPet());
        }

        return result;
    }
}
