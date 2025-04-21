package model;

/**
 * The {@code CompatibilityCalculator} class implements the {@link ICompatibilityCalculator}
 * interface to provide logic for computing compatibility scores between a user and a pet.
 * <p>
 * The final score is calculated based on seven weighted criteria:
 * space availability, time availability, budget, yard requirement, energy level,
 * MBTI similarity, and pet gender preference. A pet that triggers a user's allergy
 * will receive an automatic score of 0.0.
 */
public class CompatibilityCalculator implements ICompatibilityCalculator {

    /**
     * Computes the compatibility score between a given user and pet.
     *
     * @param user the user profile
     * @param pet  the pet profile
     * @return a value between 0.0 and 1.0 representing the match quality
     */
    @Override
    public double calculate(User user, Pet pet) {
        // Allergy = hard fail
        if (user.isAllergic() && pet.isAllergenic()) {
            return 0.0;
        }

        double finalScore =
                0.15 * getSpaceScore(user.getSpace(), pet.getRequiredSpace()) +
                        0.15 * getTimeScore(user.getTimePerDay(), pet.getTimeNeededPerDay()) +
                        0.15 * getBudgetScore(user.getBudget(), pet.getMonthlyCost()) +
                        0.10 * getYardScore(user.hasYard(), pet.requiresYard()) +
                        0.15 * getEnergyLevelScore(user.getEnergyLevel(), pet.getEnergyLevel()) +
                        0.25 * getMBTIScore(user.getMbti(), pet.getMbti()) +
                        0.05 * getGenderScore(user.getPreferredPetGender(), pet.getGender());

        return finalScore;
    }

    /**
     * Calculates the space compatibility score.
     *
     * @param userSpace the user's available space
     * @param petSpace  the space required by the pet
     * @return 1.0 if sufficient, otherwise a fractional score
     */
    private double getSpaceScore(double userSpace, double petSpace) {
        return userSpace >= petSpace ? 1.0 : userSpace / petSpace;
    }

    /**
     * Calculates the time compatibility score.
     *
     * @param userTime the user's available time per day
     * @param petTime  the pet's required time per day
     * @return 1.0 if sufficient, otherwise a fractional score
     */
    private double getTimeScore(double userTime, double petTime) {
        return userTime >= petTime ? 1.0 : userTime / petTime;
    }

    /**
     * Calculates the budget compatibility score.
     *
     * @param userBudget the user's monthly budget
     * @param petCost    the pet's monthly cost
     * @return 1.0 if sufficient, otherwise a fractional score
     */
    private double getBudgetScore(double userBudget, double petCost) {
        return userBudget >= petCost ? 1.0 : userBudget / petCost;
    }

    /**
     * Calculates the yard compatibility score.
     *
     * @param userHasYard  whether the user has a yard
     * @param petNeedsYard whether the pet requires a yard
     * @return 1.0 if compatible, 0.0 otherwise
     */
    private double getYardScore(boolean userHasYard, boolean petNeedsYard) {
        return !petNeedsYard || userHasYard ? 1.0 : 0.0;
    }

    /**
     * Calculates the energy level compatibility score.
     *
     * @param userEnergy the user's energy level
     * @param petEnergy  the pet's energy level
     * @return a score between 0.0 and 1.0 based on similarity
     */
    private double getEnergyLevelScore(int userEnergy, int petEnergy) {
        int diff = Math.abs(userEnergy - petEnergy);
        return 1.0 - (diff / 10.0);
    }

    /**
     * Calculates the MBTI compatibility score.
     *
     * @param userMBTI the user's MBTI type
     * @param petMBTI  the pet's MBTI type
     * @return a score from 0.0 to 1.0 based on character-by-character match
     */
    private double getMBTIScore(String userMBTI, String petMBTI) {
        if (userMBTI.equalsIgnoreCase(petMBTI)) {
            return 1.0;
        }

        int matchCount = 0;
        for (int i = 0; i < 4; i++) {
            if (i < userMBTI.length() && i < petMBTI.length() &&
                    Character.toUpperCase(userMBTI.charAt(i)) == Character.toUpperCase(petMBTI.charAt(i))) {
                matchCount++;
            }
        }

        return switch (matchCount) {
            case 4 -> 1.0;
            case 3 -> 0.75;
            case 2 -> 0.5;
            case 1 -> 0.25;
            default -> 0.0;
        };
    }

    /**
     * Calculates the gender preference compatibility score.
     *
     * @param preferredGender the user's preferred pet gender ("Male", "Female", or "Any")
     * @param petGender       the pet's actual gender
     * @return 1.0 if compatible, 0.0 otherwise
     */
    private double getGenderScore(String preferredGender, String petGender) {
        if (preferredGender.equalsIgnoreCase("Any")) return 1.0;
        return preferredGender.equalsIgnoreCase(petGender) ? 1.0 : 0.0;
    }
}
