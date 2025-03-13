package birds;

import java.util.ArrayList;
import java.util.List;

public class Bird {

    // Attributes of the Bird class
    private String type; // Type of the bird (e.g., Parrot, Owl)
    private String definingCharacteristic; // Unique characteristic of the bird
    private Boolean isExtinct; // Indicator of whether the bird is extinct
    private int noOfWings; // Number of wings the bird has
    private List<PreferredFood> preferredFoodList; // List of preferred food items for the bird

    // Constructor to initialize a Bird object
    public Bird(String type, String definingCharacteristic, boolean isExtinct, int noOfWings,
                List<PreferredFood> preferredFoodList) {
        // Validate the preferred food list to ensure it has between 2 and 5 items
        if (preferredFoodList == null || preferredFoodList.size() < 2 || preferredFoodList.size() > 5) {
            throw new IllegalArgumentException("Preferred food list size must be between 2 and 5.");
        }

        // Assign parameter values to class attributes
        this.type = type;
        this.definingCharacteristic = definingCharacteristic;
        this.isExtinct = isExtinct;
        this.noOfWings = noOfWings;
        this.preferredFoodList = preferredFoodList;
    }

    // Getter method for bird type
    public String getType() {
        return type;
    }

    // Setter method for bird type
    public void setType(String type) {
        this.type = type;
    }

    // Getter method for defining characteristic
    public String getDefiningCharacteristic() {
        return definingCharacteristic;
    }

    // Setter method for defining characteristic
    public void setDefiningCharacteristic(String definingCharacteristic) {
        this.definingCharacteristic = definingCharacteristic;
    }

    // Method to check if the bird is extinct
    public boolean isExtinct() {
        return isExtinct;
    }

    // Setter method for the extinct status of the bird
    public void setExtinct(boolean extinct) {
        isExtinct = extinct;
    }

    // Getter method for the number of wings
    public int getNoOfWings() {
        return noOfWings;
    }

    // Setter method for the number of wings
    public void setNoOfWings(int noOfWings) {
        this.noOfWings = noOfWings;
    }

    // Getter method for the preferred food list
    public List<PreferredFood> getPreferredFoodList() {
        return preferredFoodList;
    }

    // Setter method for the preferred food list
    public void setPreferredFoodList(List<PreferredFood> preferredFoodList) {
        this.preferredFoodList = preferredFoodList;
    }
}