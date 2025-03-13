package birds;

import java.util.List;

// Parrot class that extends the Bird class, representing parrot species
public class Parrot extends Bird {

    // Attribute to hold the vocabulary size of the parrot
    private int vocabularySize;
    // Attribute to hold the parrot's favorite saying
    private String favouriteSaying;

    // Constructor for the Parrot class
    public Parrot(String type, String definingCharacteristic, Boolean isExtinct, int noOfWings,
                  List<PreferredFood> preferredFoodList, int vocabularySize, String favouriteSaying) {
        // Call to the superclass (Bird) constructor to initialize inherited attributes
        super(type, definingCharacteristic, isExtinct, noOfWings, preferredFoodList);
        // Initialize the unique attributes for the Parrot class
        this.vocabularySize = vocabularySize;
        this.favouriteSaying = favouriteSaying;
    }

    // Getter method for the vocabularySize attribute
    public int getVocabularySize() {
        return vocabularySize;
    }

    // Setter method for the vocabularySize attribute
    public void setVocabularySize(int vocabularySize) {
        this.vocabularySize = vocabularySize;
    }

    // Getter method for the favouriteSaying attribute
    public String getFavouriteSaying() {
        return favouriteSaying;
    }

    // Setter method for the favouriteSaying attribute
    public void setFavouriteSaying(String favouriteSaying) {
        this.favouriteSaying = favouriteSaying;
    }
}