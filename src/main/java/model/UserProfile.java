package model;

import java.util.List;

public class UserProfile {
    private MBTI mbti;
    private int energyLevel;
    private boolean hasAllergies;
    private boolean hasYard;
    private int availableTimePerDay;
    private int budget;
    private List<PetType> preferredTypes;
    private List<String> preferredBreeds;

    public UserProfile(MBTI mbti, int energyLevel, boolean hasAllergies, boolean hasYard,
                       int availableTimePerDay, int budget, List<PetType> preferredTypes,
                       List<String> preferredBreeds) {
        // TODO: implement constructor logic here
    }

    public MBTI getMbti() {
        // TODO: implement getter
        return null;
    }

    public int getEnergyLevel() {
        // TODO: implement getter
        return 0;
    }

    public boolean hasAllergies() {
        // TODO: implement getter
        return false;
    }

    public boolean hasYard() {
        // TODO: implement getter
        return false;
    }

    public int getAvailableTimePerDay() {
        // TODO: implement getter
        return 0;
    }

    public int getBudget() {
        // TODO: implement getter
        return 0;
    }

    public List<PetType> getPreferredTypes() {
        // TODO: implement getter
        return null;
    }

    public List<String> getPreferredBreeds() {
        // TODO: implement getter
        return null;
    }

    @Override
    public String toString() {
        // TODO: implement summary output
        return null;
    }

}
