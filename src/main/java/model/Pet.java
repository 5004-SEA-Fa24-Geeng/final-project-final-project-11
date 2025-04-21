package model;

import Database.PetDatabase;

/**
 * The {@code Pet} class represents an individual pet with specific characteristics
 * used for compatibility matching in the PetForU system.
 * <p>
 * Each pet is defined by attributes such as species type, breed, gender, MBTI type,
 * energy level, space and care requirements, and compatibility-related metadata.
 */
public class Pet {

    private final String name;
    private final String type;               // e.g., Dog, Cat, Hamster
    private final String breed;
    private final String gender;             // Male or Female
    private final String mbti;               // 4-letter MBTI string
    private final int energyLevel;           // Scale: 1–10
    private final double requiredSpace;      // in square feet
    private final double monthlyCost;        // estimated monthly cost in USD
    private final boolean allergenic;        // true if likely to cause allergies
    private final boolean requiresYard;      // true if pet needs outdoor space
    private final double timeNeededPerDay;   // time required in hours per day
    private final String imagePath;          // path to image file (if needed for GUI)

    /**
     * Constructs a {@code Pet} instance with all relevant attributes.
     *
     * @param name              the pet's name
     * @param type              the pet's species (e.g., Dog, Cat)
     * @param breed             the breed of the pet
     * @param gender            the pet's gender ("Male" or "Female")
     * @param mbti              the MBTI personality type of the pet
     * @param energyLevel       the pet's activity level on a scale of 1–10
     * @param requiredSpace     the minimum space required to care for the pet
     * @param monthlyCost       estimated monthly cost to maintain the pet
     * @param allergenic        whether the pet may cause allergic reactions
     * @param requiresYard      whether the pet needs a yard
     * @param timeNeededPerDay  number of hours of attention/exercise per day
     * @param imagePath         optional path to a photo for GUI display
     */
    public Pet(String name, String type, String breed, String gender, String mbti,
               int energyLevel, double requiredSpace, double monthlyCost,
               boolean allergenic, boolean requiresYard, double timeNeededPerDay,
               String imagePath) {
        this.name = name;
        this.type = type;
        this.breed = breed;
        this.gender = gender;
        this.mbti = mbti;
        this.energyLevel = energyLevel;
        this.requiredSpace = requiredSpace;
        this.monthlyCost = monthlyCost;
        this.allergenic = allergenic;
        this.requiresYard = requiresYard;
        this.timeNeededPerDay = timeNeededPerDay;
        this.imagePath = imagePath;
    }

    /** @return the name of the pet */
    public String getName() {
        return name;
    }

    /** @return the pet type (e.g., Dog, Cat) */
    public String getType() {
        return type;
    }

    /** @return the breed of the pet */
    public String getBreed() {
        return breed;
    }

    /** @return the pet's gender */
    public String getGender() {
        return gender;
    }

    /** @return the MBTI personality type of the pet */
    public String getMbti() {
        return mbti;
    }

    /** @return the pet's energy level from 1 to 10 */
    public int getEnergyLevel() {
        return energyLevel;
    }

    /** @return the space required to accommodate this pet */
    public double getRequiredSpace() {
        return requiredSpace;
    }

    /** @return the estimated monthly cost for maintaining this pet */
    public double getMonthlyCost() {
        return monthlyCost;
    }

    /** @return {@code true} if the pet is allergenic */
    public boolean isAllergenic() {
        return allergenic;
    }

    /** @return {@code true} if the pet requires a yard */
    public boolean requiresYard() {
        return requiresYard;
    }

    /** @return the time required to care for the pet each day (in hours) */
    public double getTimeNeededPerDay() {
        return timeNeededPerDay;
    }

    /** @return the image path for the pet, used for display or GUI */
    public String getImagePath() {
        return imagePath;
    }
}
