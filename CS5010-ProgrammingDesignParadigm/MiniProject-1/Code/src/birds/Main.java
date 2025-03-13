package birds;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        // Initialize aviaries with distinct names and add them to the list of aviaries
        Aviary aviary1 = new Aviary("Feather Heaven");
        Aviary aviary2 = new Aviary("Bird Bliss");
        Aviary aviary3 = new Aviary("Sky Nest");
        Aviary aviary4 = new Aviary("Aerial Sanctuary");
        Aviary aviary5 = new Aviary("Winged Wonders");

        // Create a list to store all the aviaries
        List<Aviary> aviaries = new ArrayList<>();
        aviaries.add(aviary1);
        aviaries.add(aviary2);
        aviaries.add(aviary3);
        aviaries.add(aviary4);
        aviaries.add(aviary5);

        // Create a conservatory and pass the list of aviaries into it
        ConservatoryForBirds birdConservatory = new ConservatoryForBirds(aviaries);

        // Create a collection of bird species to be housed within the conservatory
        Bird parrot = new Parrot("Lola", "Has a cute voice", false,
                2, List.of(PreferredFood.SEEDS, PreferredFood.FRUITS), 20, "Hey beautiful!");
        Bird owl = new Owl("Hedwig", "So fast", false,
                2, List.of(PreferredFood.FISH, PreferredFood.LARVAE, PreferredFood.BUDS), "Spherical");
        Bird flightlessBird = new FlightlessBird("Emu", "Tallest", false,
                2, List.of(PreferredFood.FISH, PreferredFood.FRUITS));
        Bird birdOfPrey = new BirdOfPrey("Falcon", "Predatory Birds", false,
                2, List.of(PreferredFood.FISH, PreferredFood.OTHER_BIRDS, PreferredFood.SMALL_MAMMALS));
        Bird waterFowl = new Waterfowl("Ducks", "Aquatic", false,
                2, List.of(PreferredFood.LARVAE, PreferredFood.BUDS), List.of(WaterSources.FRESHWATER));

        // Rescue the birds by adding them to the conservatory
        birdConservatory.rescueBird(parrot);
        birdConservatory.rescueBird(owl);
        /* Uncomment the lines below to rescue birds that cannot coexist with other bird types
        birdConservatory.rescueBird(flightlessBird);
        birdConservatory.rescueBird(birdOfPrey);
        birdConservatory.rescueBird(waterFowl); */

        // Print bird locations within the conservatory by accessing the location map
        Map<Bird, String> birdLocations = birdConservatory.getLocationMapForBirds();
        System.out.println("Bird locations:");
        for (Map.Entry<Bird, String> entry : birdLocations.entrySet()) {
            System.out.println("Bird: " + entry.getKey().getType() + " | " + entry.getValue());
        }

        // Print the overall map of the conservatory showing aviaries and the birds they house
        Map<String, List<Bird>> aviaryMap = birdConservatory.printMap();
        System.out.println("\nConservatory Map:");
        for (Map.Entry<String, List<Bird>> entry : aviaryMap.entrySet()) {
            System.out.println("-------------------------------------------");
            System.out.println("Aviary: " + entry.getKey());
            System.out.println("Houses the following birds:");
            for (Bird bird : entry.getValue()) {
                System.out.println(" --> " + bird.getType());
            }
        }

        // Calculate and print the preferred food requirements for the conservatory
        System.out.println("\nFood Requirements for the Conservatory: " + birdConservatory.calculateFoodRequirements());

        // Look up the location of a specific bird and print it
        System.out.println(parrot.getType() + " is found in " + birdConservatory.lookupBirdLocation(parrot));

        // Print the bird index, which lists all birds in the conservatory sorted by type
        System.out.println("\nIndex for the conservatory: " + birdConservatory.printBirdIndex());
    }
}