package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * The {@code PetMatcher} class implements the {@link IPetMatcher} interface
 * and is responsible for finding the best match for a given user based on
 * compatibility scores exported to a CSV file.
 *
 * <p>The best match is determined by reading the sorted compatibility results
 * and selecting the top pet whose score is at least 80%. If no such pet is found,
 * the method returns {@code null}.
 */
public class PetMatcher implements IPetMatcher {

    /** A list of all pets available for matching. */
    private final List<Pet> petDatabase;

    /**
     * Constructs a {@code PetMatcher} with the provided pet database.
     *
     * @param petDatabase the full list of pets to be used for matching
     */
    public PetMatcher(List<Pet> petDatabase) {
        this.petDatabase = petDatabase;
    }

    /**
     * Reads the compatibility CSV file and returns the best pet match for the user.
     * Only pets with a score ≥ 80% are considered valid matches.
     *
     * @param user         the user seeking a compatible pet
     * @param csvFilePath  the path to the CSV file containing scored pets
     * @return the {@link Pet} object representing the best match, or {@code null} if no match meets the criteria
     */
    @Override
    public Pet sortAndEvaluateBestMatch(User user, String csvFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            // Skip header
            reader.readLine();

            // Read top result
            String line = reader.readLine();
            if (line == null) return null;

            String[] parts = line.split(",");
            if (parts.length < 5) {
                System.err.println("Invalid CSV format");
                return null;
            }

            String name = parts[0];
            String breed = parts[1];
            double score = Double.parseDouble(parts[3].replace("%", "")) / 100.0;

            // Match against pet database
            for (Pet pet : petDatabase) {
                if (pet.getName().equals(name) && pet.getBreed().equals(breed)) {
                    return score >= 0.80 ? pet : null;
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }

        return null;
    }
}
