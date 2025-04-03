package model;

public class Pet {
    private String name;
    private PetType type;
    private String breed; // e.g., Syrian, Betta, African Grey
    private boolean hypoallergenic;
    private int energyLevel;
    private int careTime;
    private int costPerMonth;

    public Pet(String name, PetType type, String breed, boolean hypoallergenic, int energyLevel, int careTime, int costPerMonth) {
        // To be implemented
    }

    public String getName() { return null; }
    public PetType getType() { return null; }
    public String getBreed() { return null; }
    public boolean isHypoallergenic() { return false; }
    public int getEnergyLevel() { return 0; }
    public int getCareTime() { return 0; }
    public int getCostPerMonth() { return 0; }

    @Override
    public String toString() { return null; }
}
