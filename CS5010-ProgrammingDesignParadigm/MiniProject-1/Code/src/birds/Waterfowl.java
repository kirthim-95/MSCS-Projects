package birds;

import java.util.List;

// Class representing a waterfowl, which is a specialized type of water bird
public class Waterfowl extends WaterBird {

    // Constructor for the Waterfowl class
    public Waterfowl(String type, String definingCharacteristic, Boolean isExtinct, int noOfWings,
                     List<PreferredFood> preferredFoodList, List<WaterSources> waterSourcesList) {
        // Call the superclass (WaterBird) constructor to initialize common water bird properties
        super(type, definingCharacteristic, isExtinct, noOfWings, preferredFoodList, waterSourcesList);
    }
}