package birds;

import java.util.List;

// Class representing a water bird, which is a specialized type of bird that has access to water sources
public class WaterBird extends Bird {

    // List of water sources that the water bird inhabits or prefers
    public List<WaterSources> waterSourcesList;

    // Constructor for the WaterBird class
    public WaterBird(String type, String definingCharacteristic, Boolean isExtinct, int noOfWings,
                     List<PreferredFood> preferredFoodList, List<WaterSources> waterSourcesList) {
        // Call the superclass (Bird) constructor to initialize common bird properties
        super(type, definingCharacteristic, isExtinct, noOfWings, preferredFoodList);
        // Initialize the waterSourcesList specific to water birds
        this.waterSourcesList = waterSourcesList;
    }
}