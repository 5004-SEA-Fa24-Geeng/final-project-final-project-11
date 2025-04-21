package model;

/**
 * The {@code User} class represents a user profile containing lifestyle preferences,
 * environment factors, and MBTI personality data used to evaluate pet compatibility.
 */
public class User {

    private final String gender;
    private final String preferredPetGender;
    private final String mbti;
    private final int energyLevel;
    private final double space;
    private final double budget;
    private final boolean allergic;
    private final boolean hasYard;
    private final double timePerDay;

    /**
     * Constructs a {@code User} instance with the specified lifestyle and preference data.
     *
     * @param gender              the user's gender
     * @param preferredPetGender  the user's preferred pet gender
     * @param mbti                the user's MBTI personality type
     * @param energyLevel         the user's energy level (1–10)
     * @param space               available space in square feet
     * @param budget              monthly pet budget in USD
     * @param allergic            whether the user has pet allergies
     * @param hasYard             whether the user has a yard
     * @param timePerDay          available time per day to spend with a pet
     */
    public User(String gender, String preferredPetGender, String mbti,
                int energyLevel, double space, double budget,
                boolean allergic, boolean hasYard, double timePerDay) {
        this.gender = gender;
        this.preferredPetGender = preferredPetGender;
        this.mbti = mbti;
        this.energyLevel = energyLevel;
        this.space = space;
        this.budget = budget;
        this.allergic = allergic;
        this.hasYard = hasYard;
        this.timePerDay = timePerDay;
    }

    /** @return the user's gender */
    public String getGender() {
        return gender;
    }

    /** @return the user's preferred pet gender */
    public String getPreferredPetGender() {
        return preferredPetGender;
    }

    /** @return the user's MBTI type */
    public String getMbti() {
        return mbti;
    }

    /** @return the user's energy level */
    public int getEnergyLevel() {
        return energyLevel;
    }

    /** @return the available living space in square feet */
    public double getSpace() {
        return space;
    }

    /** @return the user's monthly budget for pet care */
    public double getBudget() {
        return budget;
    }

    /** @return {@code true} if the user has pet allergies */
    public boolean isAllergic() {
        return allergic;
    }

    /** @return {@code true} if the user has a yard */
    public boolean hasYard() {
        return hasYard;
    }

    /** @return available time per day to spend with a pet */
    public double getTimePerDay() {
        return timePerDay;
    }
}
