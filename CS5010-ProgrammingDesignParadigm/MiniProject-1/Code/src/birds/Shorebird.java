package birds;

import java.util.List;

// Class representing a shorebird, which is a type of water bird
public class Shorebird extends WaterBird {

    // Constructor for the Shorebird class
    public Shorebird(String type, String definingCharacteristic, Boolean isExtinct, int noOfWings,
                     List<PreferredFood> preferredFoodList, List<WaterSources> waterSourcesList) {
        // Call the superclass (WaterBird) constructor to initialize common properties
        super(type, definingCharacteristic, isExtinct, noOfWings, preferredFoodList, waterSourcesList);
    }
}