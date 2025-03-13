import org.junit.Test;
import rpg.*;

import java.util.List;

import static org.junit.Assert.*;

public class ItemTest {

    @Test
    public void testItemCreation() {
        Item item = new FootGear("Swift", "Boots", 5, 3);
        assertEquals(ItemType.FOOTGEAR.getName(), item.getType());
        assertEquals("Swift Boots", item.getName());
        assertEquals(5, item.getAttack());
        assertEquals(3, item.getDefense());
    }

    @Test
    public void testImmutability() {
        Item item = new HandGear("Powerful", "Gloves", 10);
        assertEquals(10, item.getAttack());
        assertEquals(0, item.getDefense()); // HandGear has default defense of 0
    }

    @Test
    public void testInvalidInputs() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new FootGear("Broken", "Sandals", -1, 2);
        });
        assertEquals("Attack and defense must be non-negative!", exception.getMessage());
    }

    @Test
    public void testCombinationLogic() {
        FootGear firstItem = new FootGear("Fast", "Boots", 3, 2);
        FootGear secondItem = new FootGear("Sturdy", "Shoes", 1, 4);

        CombinedItem combinedItem = new CombinedItem(ItemType.FOOTGEAR, firstItem, secondItem);

        assertEquals(ItemType.FOOTGEAR.getName(), combinedItem.getType());
        assertEquals("Sturdy, Fast Boots", combinedItem.getName());
        assertEquals(4, combinedItem.getAttack());
        assertEquals(6, combinedItem.getDefense());
    }

    @Test
    public void testCombinedItemList() {
        FootGear firstItem = new FootGear("Fast", "Boots", 3, 2);
        FootGear secondItem = new FootGear("Sturdy", "Shoes", 1, 4);

        CombinedItem combinedItem = new CombinedItem(ItemType.FOOTGEAR, firstItem, secondItem);
        List<Item> combinedItemList = combinedItem.getCombinedItemList();

        // Verify the combined item list
        assertEquals(2, combinedItemList.size());
        assertEquals(firstItem, combinedItemList.getFirst());
        assertEquals(secondItem, combinedItemList.getLast());
    }

    @Test
    public void testCombinationLogicWithTie() {
        FootGear firstItem = new FootGear("Agile", "Boots", 3, 5);
        FootGear secondItem = new FootGear("Resilient", "Sandals", 3, 5);

        CombinedItem combinedItem = new CombinedItem(ItemType.FOOTGEAR, firstItem, secondItem);

        assertTrue((combinedItem.getName().equals("Resilient, Agile Boots")) || (combinedItem.getName().equals("Agile, Resilient Sandals")));
    }

    @Test
    public void testEquipMethodInCombinedItem() {
        FootGear firstItem = new FootGear("Agile", "Boots", 3, 2);
        FootGear secondItem = new FootGear("Sturdy", "Shoes", 1, 4);

        CombinedItem combinedItem = new CombinedItem(ItemType.FOOTGEAR, firstItem, secondItem);

        // Verify that equip() returns null
        ActionResult result = combinedItem.equip(new RpgCharacterImpl(10, 15));
        assertNull(result);
    }
}