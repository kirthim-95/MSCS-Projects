package games;

import games.config.AvailableGears;
import games.config.Constants;
import games.core.Battle;
import games.core.Player;
import games.impl.*;
import games.interfaces.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class BattleTest {

    private Player player1;
    private Player player2;
    private List<Item> items;

    @BeforeEach
    void setUp() {
        // Initialize players
        player1 = new Player("JoyJester", 50, 30);
        player2 = new Player("QuestKing", 40, 40);
        items = new ArrayList<>();

        Random random = new Random();
        Set<AvailableGears> selectedGears = new HashSet<>(); // Track selected unique gears
        int numberOfItemsToChoose = 10; // Number of unique items to pick

        // Randomly choose unique items from the AvailableGears enum
        while (selectedGears.size() < numberOfItemsToChoose) {
            int randomIndex = random.nextInt(AvailableGears.values().length);
            AvailableGears randomGear = AvailableGears.values()[randomIndex];
            if (!selectedGears.contains(randomGear)) { // Ensure no duplicates
                selectedGears.add(randomGear);
                items.add(randomGear.createItem());
            }
        }
    }

    @Test
    void testEmptyPlayerName() {
        assertThrows(IllegalArgumentException.class, () -> new Player("", 50, 30));
    }

    @Test
    void testPlayerWithNoPowers() {
        Player player = new Player("JoyJester");
        assertEquals(0.0, player.getTotalAttackPower());
    }

    @Test
    void testBattleInitialization() {
        // Valid initialization
        Battle battle = new Battle(player1, player2, items);
        assertNotNull(battle);

        // Invalid initialization: less than 10 items
        List<Item> insufficientItems = items.subList(0, 9);
        Exception exception1 = assertThrows(IllegalArgumentException.class,
                () -> new Battle(player1, player2, insufficientItems));
        assertEquals("A battle needs 2 players and at least 10 gears", exception1.getMessage());

        // Invalid initialization: null player
        Exception exception2 = assertThrows(IllegalArgumentException.class,
                () -> new Battle(null, player2, items));
        assertEquals("A battle needs 2 players and at least 10 gears", exception2.getMessage());

        Exception exception3 = assertThrows(IllegalArgumentException.class,
                () -> new Battle(player1, null, items));
        assertEquals("A battle needs 2 players and at least 10 gears", exception3.getMessage());
    }

    @Test
    void testInitiateCombat() {
        Battle battle = new Battle(player1, player2, items);
        String result = battle.initiateCombat();
        assertNotNull(result);
        assertTrue(result.equals(Constants.PLAYER_1_WINS) ||
                result.equals(Constants.PLAYER_2_WINS) ||
                result.equals(Constants.TIE));
    }

    @Test
    void testSelectBestItem() {
        items = new ArrayList<>();
        items.add(new HeadGear("Titan Crown", "Powerful", 5.0));
        items.add(new HandGear("Warrior Gauntlets", "Sturdy", 10.0));
        items.add(new FootGear("Powerful Titan Cleats", "Fast", 20.0, 3.0));
        items.add(new FootGear("Speedy Boots", "Swift", 12.0, 10.0));
        items.add(new HeadGear("Iron Helm", "Solid", 15.0));
        items.add(new HandGear("Shielded Bracers", "Heavy", 8.0));
        items.add(new FootGear("Swift Sneakers", "Light", 10.0, 6.0));
        items.add(new FootGear("Titanic Boots", "Powerful", 25.0, 2.0));
        items.add(new HandGear("Gloves of Strength", "Mighty", 18.0));
        items.add(new HeadGear("Crown of Wisdom", "Wise", 12.0));

        Battle battle = new Battle(player1, player2, items);

        Item bestItem = battle.selectBestItem(player1);

        assertNotNull(bestItem);
        assertEquals("Powerful Titanic Boots", bestItem.getName());

        battle.getItems().clear();
        assertNull(battle.selectBestItem(player1));
    }

    @Test
    void testNullItem() {
        Player player = new Player("JoyJester", 50, 30);
        Item gear = null;
        assertThrows(IllegalArgumentException.class, () -> player.addGear(gear));
    }

    @Test
    void testHasAvailableSlot() {
        // Initially, all slots are available
        Battle battle = new Battle(player1, player2, items);

        Item headGear = new HeadGear("Head", "Gear", 5.0);
        assertTrue(battle.hasAvailableSlot(player1, headGear));

        Item handGear = new HandGear("Hand" , "Gear", 4.0);
        assertTrue(battle.hasAvailableSlot(player1, handGear));

        Item footGear = new FootGear("Foot", "Gear", 3.0, 1.0);
        assertTrue(battle.hasAvailableSlot(player1, footGear));

        // Equip items and check again
        player1.addGear(headGear);
        player1.addGear(handGear);
        player1.addGear(footGear);

        assertFalse(battle.hasAvailableSlot(player1, headGear));
        assertTrue(battle.hasAvailableSlot(player1, handGear));
        assertTrue(battle.hasAvailableSlot(player1, footGear));
    }

    @Test
    void testCalculateDamage() {

        Battle battle = new Battle(player1, player2, items);

        assertEquals(10.0, battle.calculateDamage(player1, player2));
        assertEquals(10.0, battle.calculateDamage(player2, player1));
    }

}