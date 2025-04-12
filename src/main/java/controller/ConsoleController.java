package controller;

import model.*;

/**
 * ConsoleController is responsible for handling user interaction with the console.
 * This class manages the display of pet information based on the user's choice.
 *
 * Notes on image display:
 * - For methods like displayBestMatch(...) and displayRecommendedPets(...), the pet image will be opened
 *   using the system's default image viewer (cross-platform supported).
 * - displayAllPetsWithScores(...) will NOT display any images, only text data.
 */
public class ConsoleController {

    private final User user;
    private final ICompatibilityCalculator calculator;

    // Constructor to initialize with user and calculator
    public ConsoleController(User user, ICompatibilityCalculator calculator) {
        this.user = user;
        this.calculator = calculator;
    }

    /**
     * Displays the best match pet to the user.
     * This method will read the CSV containing sorted pets and display the best matched pet's details.
     * The pet image will also be opened using the system default image viewer.
     *
     * @param csvFilePath The path to the CSV file containing the sorted pet information.
     */
    public void displayBestMatch(String csvFilePath) {
        // TODO: Read the CSV file and display the pet with the highest compatibility score.
        // Print the pet's name, breed, score, and image path.
        // Open the image in the system's default viewer.
    }

    /**
     * Displays recommended pets to the user.
     * This method will filter pets with compatibility scores above a certain threshold (e.g., 80%)
     * and display their details.
     * Each matching pet's image will also be opened using the system default image viewer.
     *
     * @param csvFilePath The path to the CSV file containing pet information.
     */
    public void displayRecommendedPets(String csvFilePath) {
        // TODO: Filter pets with compatibility above a specific threshold (e.g., 80%).
        // Print the name, breed, score, and image path of the recommended pets.
        // Open images for each matching pet in the system viewer.
    }

    /**
     * Displays all pets with their compatibility scores.
     * This method will read the CSV file containing all pets and display their details (name, breed, score).
     * NOTE: This method does NOT open or display any pet images.
     *
     * @param csvFilePath The path to the CSV file containing all pet data.
     */
    public void displayAllPetsWithScores(String csvFilePath) {
        // TODO: Read the CSV file and display all pets with their compatibility scores.
        // Print each pet's name, breed, score, and image path (text only).
    }

    /**
     * Displays search results for a pet by type and/or breed.
     * This method will receive a list of matched pets and display their details.
     * Each matching pet's image will also be opened using the system default image viewer.
     *
     * @param results     The list of matching pets with scores.
     */
    public void displaySearchResult(java.util.List<PetWithScore> results) {
        // TODO: Display all matched pets' names, breeds, scores, image paths.
        // Open each image using system viewer.
    }
}
