package model;

import java.util.List;
public class PetMatcher implements iMatcher {

    /**
     * Finds the best matching pet from the given list, based on user profile preferences.
     *
     * @param user the user's preferences and lifestyle profile
     * @param pets the list of available pets to consider
     * @return the best-matched Pet, or null if no suitable match found
     */
    @Override
    public Pet findBestMatch(UserProfile user, List<Pet> pets) {
        // TODO: implement match logic using scores and rules
        return null;
    }
}
