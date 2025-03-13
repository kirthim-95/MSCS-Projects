import org.junit.Test;
import rpg.*;

import static org.junit.Assert.*;

public class ItemListingTest {

    @Test
    public void testCreateValidHeadGear() {
        Item item = ItemListing.createItem("BRIGHT_HELMET");
        assertTrue(item instanceof HeadGear);
        assertEquals("Bright Helmet", item.getName());
        assertEquals(0, item.getAttack());
        assertEquals(2, item.getDefense());
    }

    @Test
    public void testCreateValidHandGear() {
        Item item = ItemListing.createItem("MIGHTY_SHIELD");
        assertTrue(item instanceof HandGear);
        assertEquals("Mighty Shield", item.getName());
        assertEquals(2, item.getAttack());
        assertEquals(0, item.getDefense());
    }

    @Test
    public void testCreateValidFootGear() {
        Item item = ItemListing.createItem("SCURRYING_SANDALS");
        assertTrue(item instanceof FootGear);
        assertEquals("Scurrying Sandals", item.getName());
        assertEquals(0, item.getAttack());
        assertEquals(1, item.getDefense());
    }

    @Test
    public void testInvalidItemName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ItemListing.createItem("INVALID_ITEM");
        });
        assertEquals("No item found with name: INVALID_ITEM", exception.getMessage());
    }

    @Test
    public void testCaseInsensitiveItemName() {
        Item item = ItemListing.createItem("happy_HOVERBOARD");
        assertTrue(item instanceof FootGear);
        assertEquals("Happy Hoverboard", item.getName());
        assertEquals(1, item.getAttack());
        assertEquals(3, item.getDefense());
    }

    @Test
    public void testItemUniqueness() {
        Item item1 = ItemListing.createItem("HAPPY_HOVERBOARD");
        Item item2 = ItemListing.createItem("HAPPY_HOVERBOARD");

        assertNotSame(item1, item2); // Factory creates new instances each time
    }
}