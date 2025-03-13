package birds;

import java.util.*;

public class Aviary {

    // List to store the birds in this aviary
    private final List<Bird> birdList;

    // The location of the aviary
    private final String location;

    //Constructor to initialize an aviary with its location.
    public Aviary(String location) {
        this.location = location;
        this.birdList = new ArrayList<>();  // Initializes an empty bird list
    }

    //Adds a bird to the aviary.
    public void addBirds(Bird bird) {
        if (bird.isExtinct()) {
            throw new IllegalArgumentException("Extinct birds cannot be added!");
        }
        this.birdList.add(bird);  // Adds the bird to the aviary
    }

    //Gets the list of birds currently in this aviary.
    public List<Bird> getBirds() {
        return this.birdList;  // Returns the list of birds in the aviary
    }

    //Gets the location of the aviary.
    public String getLocation() {
        return this.location;  // Returns the aviary's location
    }

    //Prints a sign for the aviary listing the birds and their defining characteristics.
    public String printSign() {
        StringBuilder sign = new StringBuilder(this.location + " Aviary of Birds!!!");
        for (Bird bird : this.birdList) {
            // Append bird information to the sign
            sign.append("\n");
            sign.append(bird.getType()).append(" with its unique characteristic ")
                    .append(bird.getDefiningCharacteristic()).append("!!");
        }
        return sign.toString();
    }

    //Checks if there is space to add more birds to the aviary.
    public Boolean hasSpace() {
        int MAX_BIRDS = 5;  // Maximum capacity of the aviary
        if (birdList.size() < MAX_BIRDS) {
            return true;  // There is space in the aviary
        }
        throw new IllegalStateException("Aviary has reached its maximum capacity, so no more birds can be added!");
    }

    //Checks if the new bird can coexist with the current birds in the aviary.
    public Boolean canExistTogether(Bird newBird) {
        for (Bird bird : this.birdList) {
            // Check if the new bird is a bird of prey, flightless bird, or waterfowl
            if (newBird instanceof BirdOfPrey || bird instanceof BirdOfPrey ||
                    newBird instanceof FlightlessBird || bird instanceof FlightlessBird ||
                    newBird instanceof Waterfowl || bird instanceof Waterfowl) {
                throw new IllegalArgumentException(newBird.getType() + " cannot coexist with other birds in the conservatory!");
            }
        }
        return true;  // The new bird can coexist with the current birds
    }
}