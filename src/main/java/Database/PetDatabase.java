package Database;

import model.Pet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PetDatabase {

    private static List<Pet> petList = new ArrayList<>();

    static {
        populateDatabase();
    }

    private static void populateDatabase() {
        // Populate pets with mixed types and breeds

        petList.add(new Pet("Bella", "Dog", "Beagle", "Female", "ISFJ", 7, 50.0, 30.0, false, true, 2.0, "images/bella.jpg"));
        petList.add(new Pet("Charlie", "Dog", "Golden Retriever", "Male", "ENTJ", 9, 75.0, 50.0, false, true, 2.5, "images/charlie.jpg"));
        petList.add(new Pet("Milo", "Cat", "Siamese", "Male", "INFP", 5, 25.0, 20.0, false, false, 1.5, "images/milo.jpg"));
        petList.add(new Pet("Gizmo", "Guinea Pig", "Abyssinian", "Male", "ENTP", 5, 5.5, 6.0, true, false, 0.6, "images/gizmo.jpg"));
        petList.add(new Pet("Zoe", "Hamster", "Syrian Hamster", "Female", "ENFP", 3, 5.0, 10.0, true, false, 0.5, "images/zoe.jpg"));
        petList.add(new Pet("Shelly", "Turtle", "Red-eared Slider", "Female", "ISTP", 2, 2.0, 5.0, false, false, 0.1, "images/shelly.jpg"));
        petList.add(new Pet("Whiskers", "Rat", "Fancy Rat", "Male", "INTP", 6, 1.0, 3.0, true, false, 0.2, "images/whiskers.jpg"));
        petList.add(new Pet("Kiki", "Parrot", "African Grey", "Male", "INTP", 5, 10.0, 15.0, false, false, 1.0, "images/kiki.jpg"));
        petList.add(new Pet("Misty", "Cat", "Bengal", "Female", "INFJ", 6, 35.0, 30.0, false, false, 1.6, "images/misty.jpg"));
        petList.add(new Pet("Rocky", "Hamster", "Roborovski Dwarf Hamster", "Male", "ENFP", 3, 5.0, 8.0, true, false, 0.4, "images/rocky.jpg"));
        petList.add(new Pet("Bubbles", "Fish", "Goldfish", "Male", "INFP", 2, 1.0, 5.0, false, false, 0.2, "images/bubbles.jpg"));
        petList.add(new Pet("Sunny", "Parrot", "Macaw", "Female", "ENFJ", 7, 15.0, 20.0, false, false, 2.0, "images/sunny.jpg"));
        petList.add(new Pet("Max", "Dog", "Rottweiler", "Male", "ESTJ", 8, 80.0, 60.0, true, true, 3.0, "images/max.jpg"));
        petList.add(new Pet("Slither", "Snake", "Ball Python", "Male", "INTJ", 8, 5.0, 8.0, false, false, 1.0, "images/slither.jpg"));
        petList.add(new Pet("Cooper", "Dog", "Beagle", "Male", "ENFP", 7, 50.0, 35.0, false, true, 2.5, "images/cooper.jpg"));
        petList.add(new Pet("Shelly", "Guinea Pig", "American", "Male", "ISFJ", 4, 5.0, 5.0, true, false, 0.5, "images/shelly.jpg"));
        petList.add(new Pet("Squeaky", "Rat", "Dumbo Rat", "Female", "INFJ", 5, 1.2, 3.5, true, false, 0.3, "images/squeaky.jpg"));
        petList.add(new Pet("Pumpkin", "Hamster", "Winter White Dwarf Hamster", "Female", "ENFP", 2, 4.5, 7.0, true, false, 0.4, "images/pumpkin.jpg"));
        petList.add(new Pet("Daisy", "Dog", "Bulldog", "Female", "ESTP", 6, 50.0, 35.0, true, true, 2.5, "images/daisy.jpg"));
        petList.add(new Pet("Tiny", "Hamster", "Campbell's Dwarf Hamster", "Male", "INTP", 4, 5.0, 9.0, false, false, 0.4, "images/tiny.jpg"));
        petList.add(new Pet("Ziw", "Cat", "Maine Coon", "Female", "ISFP", 7, 50.0, 35.0, false, false, 2.0, "images/ziw.jpg"));
        petList.add(new Pet("Tiger", "Cat", "British Shorthair", "Female", "INTJ", 8, 45.0, 25.0, false, false, 2.0, "images/tiger.jpg"));
        petList.add(new Pet("Giz", "Guinea Pig", "Peruvian", "Female", "INFP", 3, 6.0, 7.0, true, false, 0.4, "images/giz.jpg"));
        petList.add(new Pet("Tommy", "Turtle", "Box Turtle", "Male", "INTJ", 3, 3.5, 4.0, false, false, 0.2, "images/tommy.jpg"));
        petList.add(new Pet("Slit", "Snake", "Ball Python", "Male", "INTJ", 8, 5.0, 8.0, false, false, 1.0, "images/slit.jpg"));
        petList.add(new Pet("Sadie", "Dog", "Poodle", "Female", "INFP", 8, 55.0, 45.0, false, true, 2.0, "images/sadie.jpg"));
        petList.add(new Pet("Spike", "Lizard", "Bearded Dragon", "Female", "ISFJ", 6, 10.0, 15.0, false, false, 2.0, "images/spike.jpg"));
        petList.add(new Pet("Coop", "Dog", "Chihuahua", "Male", "ENFP", 5, 25.0, 20.0, false, true, 2.5, "images/coop.jpg"));
        petList.add(new Pet("Maggie", "Parrot", "Cockatoo", "Female", "ENFJ", 7, 12.0, 18.0, false, false, 2.0, "images/maggie.jpg"));
        petList.add(new Pet("Rock", "Hamster", "Roborovski Dwarf Hamster", "Male", "ENFP", 3, 5.0, 8.0, true, false, 0.4, "images/rock.jpg"));
        petList.add(new Pet("Windy", "Parrot", "Macaw", "Female", "ENFJ", 7, 15.0, 20.0, false, false, 2.0, "images/windy.jpg"));
        petList.add(new Pet("Penny", "Guinea Pig", "Peruvian", "Female", "INFP", 3, 5.5, 6.5, true, false, 0.5, "images/penny.jpg"));
    }

    /**
     * Fetches the list of all pets from the "database".
     *
     * @return List of all pets stored in the database.
     */
    public static List<Pet> getAllPets() {
        // Shuffle the list to randomize the order
        Collections.shuffle(petList);
        return petList;
    }
}
