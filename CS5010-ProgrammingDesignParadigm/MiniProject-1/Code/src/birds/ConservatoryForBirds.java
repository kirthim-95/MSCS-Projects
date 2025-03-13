package birds;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConservatoryForBirds {

    // List to store the aviaries in the conservatory
    private final List<Aviary> aviaries;

    // Map to store the location of each bird within the conservatory
    private final Map<Bird, String> locationMapForBirds = new HashMap<>();

    // Temporary reference to the aviary where a bird will be placed
    private Aviary aviary;

    //Constructor to initialize the conservatory with a list of aviaries.
    public ConservatoryForBirds(List<Aviary> aviaries) {
        if (aviaries == null || aviaries.isEmpty() || aviaries.size() > 20) {
            throw new IllegalArgumentException("Our conservatory contains a max of 20 aviaries!");
        }
        this.aviaries = aviaries;  // Initialize the aviaries list
    }

    //Rescues a bird and finds an appropriate aviary for it.
    public void rescueBird(Bird bird) {
        int MAX_AVIARIES = 20;  // Maximum allowed aviaries
        if (aviaries.isEmpty() || aviaries.size() < MAX_AVIARIES) {
            this.aviary = findAviary(bird);  // Find an aviary with space and compatibility for the bird
        }
        if (this.aviary == null) {
            throw new IllegalArgumentException("No Aviary found to house the bird " + bird.getType());
        }
        this.aviary.addBirds(bird);  // Add the bird to the selected aviary
        locationMapForBirds.put(bird, aviary.getLocation());  // Store the bird's location in the map
    }

    //Finds an aviary that can house the given bird.
    public Aviary findAviary(Bird bird) {
        for (Aviary aviary : aviaries) {
            // Check if the aviary has space and the bird can coexist with other birds in it
            if (aviary.hasSpace() && aviary.canExistTogether(bird)) {
                return aviary;  // Return the first suitable aviary
            }
        }
        return null;  // No suitable aviary found
    }

    //Calculates the total food requirements for all birds in the conservatory.
    public Map<String, Integer> calculateFoodRequirements() {
        Map<String, Integer> foodRequirements = new HashMap<>();
        for (Aviary aviary : aviaries) {
            for (Bird birdFood : aviary.getBirds()) {
                for (PreferredFood preferredFood : birdFood.getPreferredFoodList()) {
                    // Increment the count for each preferred food type
                    foodRequirements.merge(preferredFood.name(), 1, Integer::sum);
                }
            }
        }
        return foodRequirements;
    }

    //Looks up the location of a bird within the conservatory.
    public String lookupBirdLocation(Bird bird) {
        return locationMapForBirds.getOrDefault(bird, "Sorry, this is not housed in our conservatory!");
    }

    //Prints a map of all aviaries and the birds housed in them.
    public Map<String, List<Bird>> printMap() {
        Map<String, List<Bird>> map = new HashMap<>();
        for (Aviary aviary : aviaries) {
            map.put(aviary.getLocation(), aviary.getBirds());  // Map each aviary's location to its birds
        }
        return map;
    }

   //Prints an index of all birds in the conservatory, sorted alphabetically by bird type.
    public String printBirdIndex() {
        StringBuilder index = new StringBuilder("Bird Index:\n");
        locationMapForBirds.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparing(Bird::getType)))  // Sort birds by type
                .forEach(entry -> index.append(entry.getKey().getType())
                        .append(" located in ").append(entry.getValue()).append("\n"));  // Add bird and location to index
        return index.toString();
    }

    //Returns the map of birds and their respective locations within the conservatory.
    public Map<Bird, String> getLocationMapForBirds() {
        return locationMapForBirds;  // Returns the map of bird locations
    }
}