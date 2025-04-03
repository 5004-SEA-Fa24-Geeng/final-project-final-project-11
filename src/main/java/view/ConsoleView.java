package view;

import model.*;

import java.util.*;

public class ConsoleView {

    public UserProfile getUserProfileFromInput() {
        // TODO: Implement questionnaire to collect:

        // - Energy level (1-10)
        // - Allergies (true/false)
        // - Yard (true/false)
        // - Time available per day (int)
        // - Monthly budget (int)
        // - Preferred pet types (e.g., DOG, CAT)
        // - Preferred breeds (e.g., Syrian, Betta)

        // Use Scanner to ask the user for each input
        // Parse and validate input
        // Return a UserProfile object

        return null;
    }

    private MBTI askMBTIQuestions() {
        // TODO: Implement MBTI questionnaire using 8 questions
        // For each of the 4 dimensions (E/I, S/N, T/F, J/P), ask 2 questions
        // Count user's A/B choices and determine dominant side for each pair
        // Example:
        // int eCount = 0, iCount = 0; ...

        // Return the final MBTI as:
        // return MBTI.valueOf(mbtiString); // e.g., "INFP"

        return null;
    }

    public void displayPetMatch(Pet pet) {
        // TODO: Show best match result
    }

    public void displayMessage(String message) {
        // TODO: Show general output messages
    }
}
