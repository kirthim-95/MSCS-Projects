package birds;

import java.util.List;

// BirdOfPrey class that extends the Bird class, representing birds of prey
public class BirdOfPrey extends Bird {

    // Constructor for the BirdOfPrey class
    public BirdOfPrey(String type, String definingCharacteristic, Boolean isExtinct, int noOfWings,
                      List<PreferredFood> preferredFoodList) {
        // Call to the superclass (Bird) constructor to initialize inherited attributes
        super(type, definingCharacteristic, isExtinct, noOfWings, preferredFoodList);
    }
}