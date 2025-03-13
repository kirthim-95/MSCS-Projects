package birds;

import java.util.List;

// Owl class that extends the Bird class, representing owl species
public class Owl extends Bird {

    // Attribute to hold the shape of the owl's facial disk
    private String facialDiskShape;

    // Constructor for the Owl class
    public Owl(String type, String definingCharacteristic, Boolean isExtinct, int noOfWings,
               List<PreferredFood> preferredFoodList, String facialDiskShape) {
        // Call to the superclass (Bird) constructor to initialize inherited attributes
        super(type, definingCharacteristic, isExtinct, noOfWings, preferredFoodList);
        // Initialize the unique attribute for the Owl class
        this.facialDiskShape = facialDiskShape;
    }

    // Getter method for the facialDiskShape attribute
    public String getFacialDiskShape() {
        return facialDiskShape;
    }

    // Setter method for the facialDiskShape attribute
    public void setFacialDiskShape(String facialDiskShape) {
        this.facialDiskShape = facialDiskShape;
    }
}