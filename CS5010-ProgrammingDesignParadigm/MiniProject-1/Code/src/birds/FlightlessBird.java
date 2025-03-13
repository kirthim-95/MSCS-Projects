package birds;

import java.util.List;

// FlightlessBird class that extends the Bird class, representing birds that cannot fly
public class FlightlessBird extends Bird {

    // Constructor for the FlightlessBird class
    public FlightlessBird(String type, String definingCharacteristic, Boolean isExtinct, int noOfWings,
                          List<PreferredFood> preferredFoodList) {
        // Call to the superclass (Bird) constructor to initialize inherited attributes
        super(type, definingCharacteristic, isExtinct, noOfWings, preferredFoodList);
    }
}