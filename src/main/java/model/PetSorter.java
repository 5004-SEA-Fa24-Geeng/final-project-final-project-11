package model;

import Database.PetDatabase;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * The {@code PetSorter} class implements the {@link IPetSorter} interface
 * and is responsible for scoring, sorting, and exporting pets based on their
 * compatibility with a given user.
 * <p>
 * Each pet is scored using a provided {@link ICompatibilityCalculator}, then sorted
 * using a multi-level tie-breaking strategy. The final sorted list is exported to a CSV file.
 */
public class PetSorter implements IPetSorter {

    private final User user;
    private final ICompatibilityCalculator calculator;

    /**
     * Constructs a {@code PetSorter} with the given user and calculator.
     *
     * @param user       the user to match pets against
     * @param calculator the scoring algorithm used to compute compatibility
     */
    public PetSorter(User user, ICompatibilityCalculator calculator) {
        this.user = user;
        this.calculator = calculator;
    }

    /**
     * Sorts the given list of pets by compatibility score and multiple tie-breaking criteria,
     * then exports the sorted result to a CSV file.
     *
     * @param pets          the list of pets to sort and score
     * @param outputCsvPath the output file path to write the CSV to
     */
    @Override
    public void sortAndExportToCSV(List<Pet> pets, String outputCsvPath) {
        List<PetWithScore> scoredPets = new ArrayList<>();

        // Calculate compatibility score for each pet
        for (Pet pet : pets) {
            double score = calculator.calculate(user, pet);
            scoredPets.add(new PetWithScore(pet, score));
        }

        // Sort using score and tie-breaking strategies
        scoredPets.sort(Comparator
                .comparing(PetWithScore::getScore).reversed()
                .thenComparing(this::genderMatchPriority)
                .thenComparing(this::mbtiMatchCount)
                .thenComparing(this::spaceRatio)
                .thenComparing(this::energyDiff)
                .thenComparing(this::timeRatio)
                .thenComparing(this::budgetRatio)
                .thenComparing(this::yardMatch)
        );

        // Write sorted results to CSV
        try (FileWriter writer = new FileWriter(outputCsvPath)) {
            writer.write("Name,Breed,Type,Score,ImagePath\n");
            for (PetWithScore p : scoredPets) {
                Pet pet = p.getPet();
                writer.write(String.format(
                        "%s,%s,%s,%s,%s\n",
                        pet.getName(),
                        pet.getBreed(),
                        pet.getType(),
                        formatPercentage(p.getScore()),
                        pet.getImagePath()
                ));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to CSV: " + e.getMessage(), e);
        }
    }

    /**
     * Formats the score as a percentage string (e.g., 85%).
     *
     * @param score the score between 0.0 and 1.0
     * @return a string representing the score as a whole-number percentage
     */
    private String formatPercentage(double score) {
        return String.format("%.0f%%", score * 100);
    }

    // ---------- Tie-Breaking Criteria (Private Helpers) ----------

    /**
     * Ranks pets based on how closely their gender matches the user’s preference.
     * "Any" is neutral, exact matches are preferred.
     *
     * @param p a pet with score
     * @return -1 for match, 1 for mismatch, 0 for "Any"
     */
    private int genderMatchPriority(PetWithScore p) {
        String pref = user.getPreferredPetGender();
        if (pref.equalsIgnoreCase("Any")) return 0;
        return pref.equalsIgnoreCase(p.getPet().getGender()) ? -1 : 1;
    }

    /**
     * Ranks pets based on the number of MBTI character matches with the user.
     *
     * @param p a pet with score
     * @return negative of the number of matches (to prioritize higher match count)
     */
    private int mbtiMatchCount(PetWithScore p) {
        String userMBTI = user.getMbti();
        String petMBTI = p.getPet().getMbti();
        int match = 0;
        for (int i = 0; i < 4; i++) {
            if (i < userMBTI.length() && i < petMBTI.length() &&
                    Character.toUpperCase(userMBTI.charAt(i)) == Character.toUpperCase(petMBTI.charAt(i))) {
                match++;
            }
        }
        return -match;
    }

    /**
     * Compares the ratio between user's space and the pet's required space.
     *
     * @param p a pet with score
     * @return negative ratio for sorting (higher ratio preferred)
     */
    private double spaceRatio(PetWithScore p) {
        double space = user.getSpace();
        double required = p.getPet().getRequiredSpace();
        return -Math.min(space / required, 1.0);
    }

    /**
     * Computes absolute difference between user's and pet's energy level.
     *
     * @param p a pet with score
     * @return difference in energy (lower is better)
     */
    private double energyDiff(PetWithScore p) {
        int userEnergy = user.getEnergyLevel();
        int petEnergy = p.getPet().getEnergyLevel();
        return Math.abs(userEnergy - petEnergy);
    }

    /**
     * Compares time availability versus pet's required time.
     *
     * @param p a pet with score
     * @return negative time ratio (higher ratio preferred)
     */
    private double timeRatio(PetWithScore p) {
        double userTime = user.getTimePerDay();
        double petTime = p.getPet().getTimeNeededPerDay();
        return -Math.min(userTime / petTime, 1.0);
    }

    /**
     * Compares budget to cost for tie-breaking.
     *
     * @param p a pet with score
     * @return negative budget ratio (higher ratio preferred)
     */
    private double budgetRatio(PetWithScore p) {
        double userBudget = user.getBudget();
        double petCost = p.getPet().getMonthlyCost();
        return -Math.min(userBudget / petCost, 1.0);
    }

    /**
     * Returns a ranking based on yard requirement.
     *
     * @param p a pet with score
     * @return -1 if user meets pet's yard need, otherwise 1
     */
    private int yardMatch(PetWithScore p) {
        boolean hasYard = user.hasYard();
        boolean needsYard = p.getPet().requiresYard();
        return (!needsYard || hasYard) ? -1 : 1;
    }
}
