package model;

import Database.PetDatabase;

/**
 * The {@code PetWithScore} class associates a {@link Pet} object with a
 * compatibility score for a given user. It is primarily used during sorting
 * and ranking operations within the application.
 */
public class PetWithScore {

    /** The original pet object. */
    private final Pet pet;

    /** The computed compatibility score between the pet and the user. */
    private final double score;

    /**
     * Constructs a {@code PetWithScore} instance with a pet and its associated score.
     *
     * @param pet   the pet object
     * @param score the compatibility score for this pet
     */
    public PetWithScore(Pet pet, double score) {
        this.pet = pet;
        this.score = score;
    }

    /**
     * Returns the {@link Pet} object associated with this scored entry.
     *
     * @return the pet object
     */
    public Pet getPet() {
        return pet;
    }

    /**
     * Returns the compatibility score for the pet.
     *
     * @return the score as a {@code double} between 0.0 and 1.0
     */
    public double getScore() {
        return score;
    }
}
