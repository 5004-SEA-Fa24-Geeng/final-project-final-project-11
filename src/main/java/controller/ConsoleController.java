package controller;

import model.*;

/**
 * ConsoleController is responsible for handling user interaction with the console.
 * This class manages the display of pet information based on the user's choice.
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
     *
     * @param csvFilePath The path to the CSV file containing the sorted pet information.
     */
    public void displayBestMatch(String csvFilePath) {
        // TODO: Read the CSV file and display the pet with the highest compatibility score.
        // Print the pet's name, breed, score, and image path.
    }

    /**
     * Displays recommended pets to the user.
     * This method will filter pets with compatibility scores above a certain threshold
     * and display their details.
     *
     * @param csvFilePath The path to the CSV file containing pet information.
     */
    public void displayRecommendedPets(String csvFilePath) {
        // TODO: Filter pets with compatibility above a specific threshold (e.g., 85%).
        // Print the name, breed, score, and image path of the recommended pets.
    }

    /**
     * Displays all pets with their compatibility scores.
     * This method will read the CSV file containing all pets and display their details (name, breed, score).
     *
     * @param csvFilePath The path to the CSV file containing all pet data.
     */
    public void displayAllPetsWithScores(String csvFilePath) {
        // TODO: Read the CSV file and display all pets with their compatibility scores.
        // Print each pet's name, breed, score, and image path.
    }

    /**
     * Searches for a pet by its name or breed.
     * This method will search through the pet list and display the matching pet's details.
     *
     * @param searchTerm The name or breed of the pet to search for.
     * @param csvFilePath The path to the CSV file containing pet information.
     */
    public void displaySearchResult(String searchTerm, String csvFilePath) {
        // TODO: Search for a pet by name or breed from the CSV.
        // If found, display the pet's details (name, breed, score, image path).
    }
}
