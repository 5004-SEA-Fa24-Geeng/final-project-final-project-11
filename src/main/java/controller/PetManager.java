package controller;

import model.*;

import java.io.File;
import java.util.List;

/**
 * The {@code PetManager} class coordinates the core logic of the PetForU application.
 * It is responsible for constructing the user profile, loading pet data from the database,
 * initializing the compatibility calculator, and triggering the compatibility scoring and export process.
 * <p>
 * This class acts as a bridge between user input and model-layer components such as
 * {@code PetSorter}, {@code PetFilter}, {@code PetSearcher}, and {@code PetMatcher}.
 */
public class PetManager {

    /** The user profile derived from questionnaire input. */
    private final User user;

    /** The file path where compatibility results will be exported. */
    private final String csvPath;

    /** The compatibility calculator used to compute match scores. */
    private final ICompatibilityCalculator calculator;

    /** All pets loaded from the database. */
    private final List<Pet> allPets;

    /**
     * Constructs a {@code PetManager} instance based on user questionnaire responses.
     * It creates a {@link User} object, loads all pets from the database, initializes
     * the compatibility calculator, and exports compatibility scores to a CSV file.
     *
     * @param gender             user's gender
     * @param preferredPetGender preferred pet gender
     * @param iScore             introversion/extroversion score (1 for I, 0 for E)
     * @param sScore             sensing/intuition score (1 for S, 0 for N)
     * @param tScore             thinking/feeling score (1 for T, 0 for F)
     * @param jScore             judging/perceiving score (1 for J, 0 for P)
     * @param energy             user's energy level
     * @param space              available living space
     * @param budget             user's monthly pet care budget
     * @param allergy            whether the user has pet allergies
     * @param hasYard            whether the user has a yard
     * @param time               time available to spend with a pet per day
     */
    public PetManager(String gender, String preferredPetGender, int iScore, int sScore,
                      int tScore, int jScore, int energy, double space, double budget,
                      boolean allergy, boolean hasYard, double time) {

        // Construct MBTI string from four trait flags
        this.user = new User(gender, preferredPetGender,
                (iScore == 1 ? "I" : "E")
                        + (sScore == 1 ? "S" : "N")
                        + (tScore == 1 ? "T" : "F")
                        + (jScore == 1 ? "J" : "P"),
                energy, space, budget, allergy, hasYard, time);

        this.calculator = new CompatibilityCalculator();
        this.allPets = Database.PetDatabase.getAllPets();
        this.csvPath = "output/pet_compatibility.csv";

        // Ensure output directory exists
        new File("output").mkdirs();

        // Precompute and export compatibility scores
        new PetSorter(user, calculator).sortAndExportToCSV(allPets, csvPath);
    }

    /**
     * Returns the {@link User} profile associated with this session.
     *
     * @return the user object
     */
    public User getUser() {
        return user;
    }

    /**
     * Returns the file path of the compatibility CSV.
     *
     * @return path to the output CSV file
     */
    public String getCsvPath() {
        return csvPath;
    }
}
