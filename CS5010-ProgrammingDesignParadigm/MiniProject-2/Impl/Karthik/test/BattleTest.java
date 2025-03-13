import org.junit.Test;
import rpg.*;

import static org.junit.Assert.*;

public class BattleTest {

    @Test
    public void testBattleOutcome() {
        RpgCharacterImpl firstCharacter = new RpgCharacterImpl(10, 15);
        RpgCharacterImpl secondCharacter = new RpgCharacterImpl(8, 20);

        Battle battle = new BattleImpl(firstCharacter, secondCharacter);
        ActionResult result = battle.goToBattle();

        assertTrue(result.isSuccess());
        assertTrue(result.getMessage().contains("wins") || result.getMessage().equals("It's a tie!"));
    }

    @Test
    public void testInvalidBattleSetup() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new BattleImpl(null, null);
        });
        assertEquals("Characters must be defined!", exception.getMessage());
    }

    @Test
    public void testEquipItemFromInventorySuccessfully() {
        RpgCharacterImpl firstCharacter = new RpgCharacterImpl(10, 15);
        RpgCharacterImpl secondCharacter = new RpgCharacterImpl(8, 20);

        // Load items into inventory
        ItemInventory inventory = ItemInventory.getInstance();
        inventory.clearInventory(); // Ensure inventory is empty at the start of the test.
        FootGear item = new FootGear("Swift", "Boots", 5, 3);
        inventory.addItem(item);

        Battle battle = new BattleImpl(firstCharacter, secondCharacter);

        // Equip the item from inventory
        ActionResult result = battle.equipItem(1, item);
        assertTrue(result.isSuccess());
        assertEquals("Item 'Swift Boots' of type 'Foot Gear' equipped successfully!", result.getMessage());
        assertEquals(item, firstCharacter.getFootGearList().getFirst());

        // Check that item is removed from inventory
        assertFalse(inventory.getInventory().contains(item));
    }

    @Test
    public void testEquipItemNotAvailableInInventory() {
        RpgCharacterImpl firstCharacter = new RpgCharacterImpl(10, 15);
        RpgCharacterImpl secondCharacter = new RpgCharacterImpl(8, 20);

        // Empty inventory
        ItemInventory inventory = ItemInventory.getInstance();
        inventory.clearInventory(); // Ensure inventory is empty at the start of the test.
        FootGear item = new FootGear("Swift", "Boots", 5, 3);

        Battle battle = new BattleImpl(firstCharacter, secondCharacter);

        // Try to equip an item not in the inventory
        ActionResult result = battle.equipItem(1, item);
        assertFalse(result.isSuccess());
        assertEquals("Item 'Swift Boots' of type 'Foot Gear' not available in inventory!", result.getMessage());
    }

    @Test
    public void testRemoveItemBackToInventory() {
        RpgCharacterImpl firstCharacter = new RpgCharacterImpl(10, 15);
        RpgCharacterImpl secondCharacter = new RpgCharacterImpl(8, 20);

        // Load items into inventory
        ItemInventory inventory = ItemInventory.getInstance();
        inventory.clearInventory(); // Ensure inventory is empty at the start of the test.
        FootGear item = new FootGear("Swift", "Boots", 5, 3);
        inventory.addItem(item);

        Battle battle = new BattleImpl(firstCharacter, secondCharacter);

        // Equip the item
        battle.equipItem(1, item);

        // Remove the item and check inventory
        ActionResult result = battle.removeItem(1, item);
        assertTrue(result.isSuccess());
        assertEquals("Item 'Swift Boots' of type 'Foot Gear' removed successfully!", result.getMessage());

        // Verify the item is back in the inventory
        assertTrue(inventory.getInventory().contains(item));
        assertTrue(firstCharacter.getFootGearList().isEmpty());
    }

    @Test
    public void testRemoveItemNotEquipped() {
        RpgCharacterImpl firstCharacter = new RpgCharacterImpl(10, 15);
        RpgCharacterImpl secondCharacter = new RpgCharacterImpl(8, 20);

        // Load items into inventory
        ItemInventory inventory = ItemInventory.getInstance();
        inventory.clearInventory(); // Ensure inventory is empty at the start of the test.
        FootGear item = new FootGear("Swift", "Boots", 5, 3);
        inventory.addItem(item);

        Battle battle = new BattleImpl(firstCharacter, secondCharacter);

        // Attempt to remove the item without equipping
        ActionResult result = battle.removeItem(1, item);
        assertFalse(result.isSuccess());
        assertEquals("Item 'Swift Boots' of type 'Foot Gear' is not equipped!", result.getMessage());

        // Verify the item remains in the inventory
        assertTrue(inventory.getInventory().contains(item));
    }

    @Test
    public void testGoToBattleWithHeadGearDominance() {
        RpgCharacter firstCharacter = new RpgCharacterImpl(10, 15);
        RpgCharacter secondCharacter = new RpgCharacterImpl(8, 20);

        ItemInventory inventory = ItemInventory.getInstance();
        inventory.clearInventory();

        // Add 4 HeadGear
        inventory.addItem(new HeadGear("Bright", "Helmet", 6));
        inventory.addItem(new HeadGear("Sharp", "Cap", 5));
        inventory.addItem(new HeadGear("Steel", "Visor", 4));
        inventory.addItem(new HeadGear("Light", "Hat", 3));

        // Add 2 HandGear
        inventory.addItem(new HandGear("Powerful", "Gloves", 10));
        inventory.addItem(new HandGear("Mighty", "Bracelet", 9));

        // Add 4 FootGear
        inventory.addItem(new FootGear("Swift", "Boots", 20, 3));
        inventory.addItem(new FootGear("Heavy", "Boots", 19, 5));
        inventory.addItem(new FootGear("Agile", "Sandals", 18, 2));
        inventory.addItem(new FootGear("Durable", "Slippers", 17, 4));

        Battle battle = new BattleImpl(firstCharacter, secondCharacter);

        ActionResult result = battle.goToBattle();
        assertTrue(result.isSuccess());

        // Validate HeadGear
        assertTrue(firstCharacter.getHeadGear() instanceof CombinedItem);
        assertTrue(secondCharacter.getHeadGear() instanceof CombinedItem);

        Item combinedItemOfFirstCharacter = firstCharacter.getHeadGear();
        assertEquals("Steel, Bright Helmet", combinedItemOfFirstCharacter.getName());
        assertEquals(0, combinedItemOfFirstCharacter.getAttack());
        assertEquals(10, combinedItemOfFirstCharacter.getDefense());

        Item combinedItemOfSecondCharacter = secondCharacter.getHeadGear();
        assertEquals("Light, Sharp Cap", combinedItemOfSecondCharacter.getName());
        assertEquals(0, combinedItemOfSecondCharacter.getAttack());
        assertEquals(8, combinedItemOfSecondCharacter.getDefense());

        // Validate HandGear
        assertEquals("Powerful Gloves", firstCharacter.getHandGearList().getFirst().getName());
        assertEquals("Mighty Bracelet", secondCharacter.getHandGearList().getFirst().getName());

        // Validate FootGear
        assertEquals(2, firstCharacter.getFootGearList().size());
        assertEquals(2, secondCharacter.getFootGearList().size());

        // Validate the first FootGear
        assertEquals("Swift Boots", firstCharacter.getFootGearList().getFirst().getName());
        assertEquals("Heavy Boots", secondCharacter.getFootGearList().getFirst().getName());

        // Validate the second FootGear
        assertEquals("Agile Sandals", firstCharacter.getFootGearList().getLast().getName());
        assertEquals("Durable Slippers", secondCharacter.getFootGearList().getLast().getName());
    }

    @Test
    public void testGoToBattleWithHandGearDominance() {
        RpgCharacter firstCharacter = new RpgCharacterImpl(10, 15);
        RpgCharacter secondCharacter = new RpgCharacterImpl(8, 20);

        ItemInventory inventory = ItemInventory.getInstance();
        inventory.clearInventory();

        // Add 2 HeadGear
        inventory.addItem(new HeadGear("Bright", "Helmet", 6));
        inventory.addItem(new HeadGear("Sharp", "Cap", 5));

        // Add 6 HandGear
        inventory.addItem(new HandGear("Powerful", "Gloves", 7));
        inventory.addItem(new HandGear("Mighty", "Bracelet", 6));
        inventory.addItem(new HandGear("Strong", "Gauntlet", 5));
        inventory.addItem(new HandGear("Iron", "Fist", 4));
        inventory.addItem(new HandGear("Steel", "Armlet", 3));
        inventory.addItem(new HandGear("Bronze", "Bangle", 2));

        // Add 2 FootGear
        inventory.addItem(new FootGear("Swift", "Boots", 8, 3));
        inventory.addItem(new FootGear("Heavy", "Boots", 7, 5));

        Battle battle = new BattleImpl(firstCharacter, secondCharacter);

        ActionResult result = battle.goToBattle();
        assertTrue(result.isSuccess());

        // Validate HeadGear
        assertEquals("Bright Helmet", firstCharacter.getHeadGear().getName());
        assertEquals("Sharp Cap", secondCharacter.getHeadGear().getName());

        // Validate HandGear Combination
        assertTrue(firstCharacter.getHandGearList().getFirst() instanceof CombinedItem);
        assertTrue(secondCharacter.getHandGearList().getFirst() instanceof CombinedItem);

        CombinedItem combinedHandGearOfFirstCharacter = (CombinedItem) firstCharacter.getHandGearList().get(0);
        assertEquals("Strong, Powerful Gloves", combinedHandGearOfFirstCharacter.getName());
        assertEquals(12, combinedHandGearOfFirstCharacter.getAttack());
        assertEquals(0, combinedHandGearOfFirstCharacter.getDefense());

        CombinedItem combinedHandGearOfSecondCharacter = (CombinedItem) secondCharacter.getHandGearList().get(0);
        assertEquals("Iron, Mighty Bracelet", combinedHandGearOfSecondCharacter.getName());
        assertEquals(10, combinedHandGearOfSecondCharacter.getAttack());
        assertEquals(0, combinedHandGearOfSecondCharacter.getDefense());

        // Validate FootGear
        assertEquals("Swift Boots", firstCharacter.getFootGearList().getFirst().getName());
        assertEquals("Heavy Boots", secondCharacter.getFootGearList().getFirst().getName());
    }

    @Test
    public void testGoToBattleWithFootGearDominance() {
        RpgCharacter firstCharacter = new RpgCharacterImpl(10, 15);
        RpgCharacter secondCharacter = new RpgCharacterImpl(8, 20);

        ItemInventory inventory = ItemInventory.getInstance();
        inventory.clearInventory();

        // Add 2 HeadGear
        inventory.addItem(new HeadGear("Bright", "Helmet", 6));
        inventory.addItem(new HeadGear("Sharp", "Cap", 5));

        // Add 2 HandGear
        inventory.addItem(new HandGear("Powerful", "Gloves", 7));
        inventory.addItem(new HandGear("Mighty", "Bracelet", 6));

        // Add 6 FootGear
        inventory.addItem(new FootGear("Light", "Sneakers", 10, 2));
        inventory.addItem(new FootGear("Agile", "Sandals", 9, 3));
        inventory.addItem(new FootGear("Swift", "Boots", 8, 3));
        inventory.addItem(new FootGear("Heavy", "Boots", 7, 5));
        inventory.addItem(new FootGear("Sturdy", "Shoes", 4, 2));
        inventory.addItem(new FootGear("Durable", "Slippers", 3, 4));

        Battle battle = new BattleImpl(firstCharacter, secondCharacter);

        ActionResult result = battle.goToBattle();
        assertTrue(result.isSuccess());

        // Validate HeadGear
        assertNotNull(firstCharacter.getHeadGear());
        assertNotNull(secondCharacter.getHeadGear());

        // Validate HandGear
        assertEquals(1, firstCharacter.getHandGearList().size());
        assertEquals(1, secondCharacter.getHandGearList().size());

        // Validate FootGear
        assertEquals(2, firstCharacter.getFootGearList().size());
        assertEquals(2, secondCharacter.getFootGearList().size());

        // Ensure the first FootGear is a CombinedItem
        assertTrue(firstCharacter.getFootGearList().getFirst() instanceof CombinedItem);
        assertTrue(secondCharacter.getFootGearList().getFirst() instanceof CombinedItem);

        // Validate CombinedItem details
        Item combinedItemOfFirstCharacter = firstCharacter.getFootGearList().getFirst();
        assertEquals("Swift, Light Sneakers", combinedItemOfFirstCharacter.getName());
        assertEquals(18, combinedItemOfFirstCharacter.getAttack());
        assertEquals(5, combinedItemOfFirstCharacter.getDefense());

        Item combinedItemOfSecondCharacter = secondCharacter.getFootGearList().getFirst();
        assertEquals("Heavy, Agile Sandals", combinedItemOfSecondCharacter.getName());
        assertEquals(16, combinedItemOfSecondCharacter.getAttack());
        assertEquals(8, combinedItemOfSecondCharacter.getDefense());

        // Validate the second FootGear
        assertEquals("Sturdy Shoes", firstCharacter.getFootGearList().getLast().getName());
        assertEquals("Durable Slippers", secondCharacter.getFootGearList().getLast().getName());
    }

    @Test
    public void testGoToBattleWithTieBreaker() {
        RpgCharacter firstCharacter = new RpgCharacterImpl(10, 15);
        RpgCharacter secondCharacter = new RpgCharacterImpl(8, 20);

        ItemInventory inventory = ItemInventory.getInstance();
        inventory.clearInventory();
        inventory.addItem(new FootGear("Swift", "Boots", 5, 3)); // Tie in attack
        inventory.addItem(new FootGear("Sturdy", "Shoes", 5, 4)); // Higher defense

        Battle battle = new BattleImpl(firstCharacter, secondCharacter);

        ActionResult result = battle.goToBattle();
        assertTrue(result.isSuccess());

        // Verify the item with higher defense is chosen in a tie
        assertEquals("Sturdy Shoes", firstCharacter.getFootGearList().getFirst().getName());
        assertEquals("Swift Boots", secondCharacter.getFootGearList().getFirst().getName());
    }

    @Test
    public void testGoToBattleWithEmptyInventory() {
        RpgCharacter firstCharacter = new RpgCharacterImpl(10, 15);
        RpgCharacter secondCharacter = new RpgCharacterImpl(8, 20);

        ItemInventory inventory = ItemInventory.getInstance();
        inventory.clearInventory();

        Battle battle = new BattleImpl(firstCharacter, secondCharacter);

        ActionResult result = battle.goToBattle();
        assertTrue(result.isSuccess());
        assertEquals("It's a tie!", result.getMessage()); // No items to equip
    }

    @Test
    public void testDetermineWinner() {
        RpgCharacter firstCharacter = new RpgCharacterImpl(15, 20);
        RpgCharacter secondCharacter = new RpgCharacterImpl(18, 10);

        BattleImpl battle = new BattleImpl(firstCharacter, secondCharacter);

        ActionResult result = battle.goToBattle();
        assertTrue(result.isSuccess());

        String message = result.getMessage();
        assertTrue(message.contains("First Character wins!") ||
                message.contains("Second Character wins!") ||
                message.equals("It's a tie!"));
    }
}