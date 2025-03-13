package birds;

import java.util.List;

// Pigeon class that extends the Bird class, representing pigeon species
public class Pigeon extends Bird {

    // Constructor for the Pigeon class
    public Pigeon(String type, String definingCharacteristic, Boolean isExtinct, int noOfWings,
                  List<PreferredFood> preferredFoodList) {
        // Call to the superclass (Bird) constructor to initialize inherited attributes
        super(type, definingCharacteristic, isExtinct, noOfWings, preferredFoodList);
    }
}