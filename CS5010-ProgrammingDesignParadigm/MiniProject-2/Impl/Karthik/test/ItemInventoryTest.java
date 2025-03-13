import org.junit.Test;
import rpg.FootGear;
import rpg.Item;
import rpg.ItemInventory;

import java.util.List;

import static org.junit.Assert.*;

public class ItemInventoryTest {

    @Test
    public void testAddItem() {
        ItemInventory inventory = ItemInventory.getInstance();
        FootGear item = new FootGear("Swift", "Boots", 5, 3);

        assertTrue(inventory.addItem(item)); // Successfully added
        assertFalse(inventory.addItem(item)); // Duplicate, should return false
    }

    @Test
    public void testRemoveItem() {
        ItemInventory inventory = ItemInventory.getInstance();
        FootGear item = new FootGear("Swift", "Boots", 5, 3);

        inventory.addItem(item); // Add the item first
        assertTrue(inventory.removeItem(item)); // Successfully removed
        assertFalse(inventory.removeItem(item)); // Already removed, should return false
    }

    @Test
    public void testInventoryContents() {
        ItemInventory inventory = ItemInventory.getInstance();
        FootGear item = new FootGear("Swift", "Boots", 5, 3);

        inventory.addItem(item);
        List<Item> items = inventory.getInventory();

        assertEquals(1, items.size());
        assertTrue(items.contains(item));
    }
}
