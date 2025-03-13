package birds;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class BirdTest {

    private Aviary aviary1;
    private ConservatoryForBirds birdConservatory;

    @Before
    public void setUp() {

        //Initialise an aviary and bird conservatory

        aviary1 = new Aviary("Aviary 1");
        birdConservatory = new ConservatoryForBirds(List.of(aviary1));
    }

    @Test
    public void testRescueBird() {

        //A bird rescued and added to the conservatory

        Bird parrot = new Parrot("Parrot", "Has a cute voice", false,
                2, List.of(PreferredFood.SEEDS, PreferredFood.FRUITS),
                20, "Hey beautiful!");
        birdConservatory.rescueBird(parrot);
        assertEquals(parrot, aviary1.getBirds().get(0));
    }

    @Test
    public void testAddBirdSuccess() {

        //Bird added when the aviary is within its limit and there is no problem of coexistence

        Bird parrot = new Parrot("Parrot", "Has a cute voice", false, 2,
                List.of(PreferredFood.SEEDS, PreferredFood.FRUITS), 20, "Hey beautiful!");
        Bird owl = new Owl("Owl", "Has big eyes", false, 2, List.of(PreferredFood.SEEDS, PreferredFood.BUDS), "Round");

        birdConservatory.rescueBird(parrot);
        birdConservatory.rescueBird(owl);

        assertEquals(2, aviary1.getBirds().size());
    }

    @Test
    public void testAviaryFull() {

        //An Aviary can contain a maximum of 5 birds

        Bird parrot1 = new Parrot("Parrot1", "Has a cute voice", false, 2,
                List.of(PreferredFood.SEEDS, PreferredFood.FRUITS), 20, "Hey beautiful!");
        Bird parrot2 = new Parrot("Parrot2", "Has a cute voice", false, 2,
                List.of(PreferredFood.SEEDS, PreferredFood.FRUITS), 20, "Hey beautiful!");
        Bird parrot3 = new Parrot("Parrot3", "Has a cute voice", false, 2,
                List.of(PreferredFood.SEEDS, PreferredFood.FRUITS), 20, "Hey beautiful!");
        Bird parrot4 = new Parrot("Parrot4", "Has a cute voice", false, 2,
                List.of(PreferredFood.SEEDS, PreferredFood.FRUITS), 20, "Hey beautiful!");
        Bird parrot5 = new Parrot("Parrot5", "Has a cute voice", false, 2,
                List.of(PreferredFood.SEEDS, PreferredFood.FRUITS), 20, "Hey beautiful!");
        Bird parrot6 = new Parrot("Parrot6", "Has a cute voice", false, 2,
                List.of(PreferredFood.SEEDS, PreferredFood.FRUITS), 20, "Hey beautiful!");

        birdConservatory.rescueBird(parrot1);
        birdConservatory.rescueBird(parrot2);
        birdConservatory.rescueBird(parrot3);
        birdConservatory.rescueBird(parrot4);
        birdConservatory.rescueBird(parrot5);

        assertThrows(IllegalStateException.class, () -> birdConservatory.rescueBird(parrot6));
    }

    @Test
    public void testExtinctBirdRescue() {

        //Extinct birds cannot be housed in the Conservatory

        Bird extinctBird = new Parrot("Dodo", "Extinct bird", true, 2,
                List.of(PreferredFood.SEEDS, PreferredFood.OTHER_BIRDS), 0, "I'm extinct!");
        assertThrows(IllegalArgumentException.class, () -> birdConservatory.rescueBird(extinctBird));
    }

    @Test
    public void testCalculateFoodRequirements() {

        //Find total food requirements for the bird conservatory

        Bird parrot = new Parrot("Parrot", "Has a cute voice", false, 2,
                List.of(PreferredFood.SEEDS, PreferredFood.FRUITS), 20, "Hey beautiful!");
        Bird owl = new Owl("Owl", "Has big eyes", false, 2, List.of(PreferredFood.FISH, PreferredFood.OTHER_BIRDS), "Round");

        birdConservatory.rescueBird(parrot);
        birdConservatory.rescueBird(owl);

        Map<String, Integer> foodRequirements = birdConservatory.calculateFoodRequirements();

        assertEquals(1, (int) foodRequirements.get("FRUITS"));
        assertEquals(1, (int) foodRequirements.get("SEEDS"));
    }

    @Test
    public void testPreferredFoodListSize() {

        //Preferred Food List size should be between 2 and 5

        assertThrows(IllegalArgumentException.class, () -> new Parrot("Parrot", "Has a cute voice", false, 2,
                List.of(PreferredFood.SEEDS), 20, "Hey beautiful!"));
    }

    @Test
    public void testPrintMap() {

        //Printing the map containing Aviaries and the birds present in it

        Bird parrot = new Parrot("Parrot", "Has a cute voice", false, 2,
                List.of(PreferredFood.SEEDS, PreferredFood.FRUITS), 20, "Hey beautiful!");
        Bird owl = new Owl("Owl", "Has big eyes", false, 2, List.of(PreferredFood.SEEDS, PreferredFood.FRUITS), "Round");

        birdConservatory.rescueBird(parrot);
        birdConservatory.rescueBird(owl);

        Map<String, List<Bird>> map = birdConservatory.printMap();

        assertEquals(2, map.get("Aviary 1").size());
    }

    @Test
    public void testBirdIndexPrint() {

        //Printing the bird conservatory index

        Bird parrot = new Parrot("Parrot", "Has a cute voice", false, 2,
                List.of(PreferredFood.SEEDS, PreferredFood.FRUITS), 20, "Hey beautiful!");
        Bird owl = new Owl("Owl", "Has big eyes", false, 2, List.of(PreferredFood.FISH, PreferredFood.LARVAE), "Round");

        birdConservatory.rescueBird(parrot);
        birdConservatory.rescueBird(owl);

        String birdIndex = birdConservatory.printBirdIndex();

        assertTrue(birdIndex.contains("Parrot located in Aviary 1"));
        assertTrue(birdIndex.contains("Owl located in Aviary 1"));
    }

    @Test
    public void testAviaryLimit() {

        //Maximum of 20 aviaries in the conservatory

        Aviary aviary1 = new Aviary("Aviary 1");
        Aviary aviary2 = new Aviary("Aviary 2");
        List<Aviary> aviaries = Arrays.asList(aviary1, aviary2, aviary1, aviary2, aviary1, aviary2, aviary1, aviary2, aviary1, aviary2, aviary1, aviary2, aviary1, aviary2, aviary1, aviary2, aviary1, aviary2, aviary1, aviary2, aviary1);
        assertThrows(IllegalArgumentException.class, () -> new ConservatoryForBirds(aviaries));
    }

    @Test
    public void testCoexistence() {

        //Bird of prey, Flightless birds and Waterfowl cannot coexist

        Bird birdOfPrey = new BirdOfPrey("Prey1", "Predator", false, 2,
                List.of(PreferredFood.SEEDS, PreferredFood.FRUITS));
        Bird flightlessBird = new FlightlessBird("Flightless1", "Flightless", false, 2,
                List.of(PreferredFood.SEEDS, PreferredFood.FRUITS));
        birdConservatory.rescueBird(birdOfPrey);
        assertThrows(IllegalArgumentException.class, () -> birdConservatory.rescueBird(flightlessBird));
    }

    @Test
    public void testPrintSign() {
        Bird parrot = new Parrot("Parrot", "Has a cute voice", false, 2,
                List.of(PreferredFood.SEEDS, PreferredFood.FRUITS), 20, "Hey beautiful!");

        birdConservatory.rescueBird(parrot);

        // Expected sign
        String expectedSign = "Aviary 1 Aviary of Birds!!!\n" +
                "Parrot with its unique characteristic Has a cute voice!!";

        // Check if the sign matches the expected value
        assertEquals(expectedSign, birdConservatory.findAviary(parrot).printSign());
    }
}
