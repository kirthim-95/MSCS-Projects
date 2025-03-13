import org.junit.Test;
import rpg.*;

import static org.junit.Assert.*;

public class RpgCharacterImplTest {

    @Test
    public void testCharacterCreation() {
        RpgCharacterImpl character = new RpgCharacterImpl(10, 15);
        assertEquals(10, character.getAttack());
        assertEquals(15, character.getDefense());
        assertTrue(character.getHandGearList().isEmpty());
        assertTrue(character.getFootGearList().isEmpty());
        assertNull(character.getHeadGear());
    }

    @Test
    public void testCharacterCreationWithInvalidStats() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new RpgCharacterImpl(-5, 10);
        });
        assertEquals("Attack and defense must be non-negative!", exception.getMessage());
    }

    @Test
    public void testEquipHeadGear() {
        RpgCharacterImpl character = new RpgCharacterImpl(10, 15);
        ItemInventory inventory = ItemInventory.getInstance();
        HeadGear headGear = new HeadGear("Bright", "Helmet", 5);

        // Add headGear to inventory
        inventory.addItem(headGear);

        // Equip the headGear
        ActionResult result = character.equipItem(headGear);
        assertTrue(result.isSuccess());
        assertEquals("Item 'Bright Helmet' of type 'Head Gear' equipped successfully!", result.getMessage());
        assertEquals(headGear, character.getHeadGear());

        // Verify the headGear is removed from inventory
        assertFalse(inventory.getInventory().contains(headGear));
    }

    @Test
    public void testEquipItemNotInInventory() {
        RpgCharacterImpl character = new RpgCharacterImpl(10, 15);
        FootGear footGear = new FootGear("Swift", "Boots", 3, 2);

        // Try to equip without adding to inventory
        ActionResult result = character.equipItem(footGear);
        assertFalse(result.isSuccess());
        assertEquals("Item 'Swift Boots' of type 'Foot Gear' not available in inventory!", result.getMessage());
    }

    @Test
    public void testEquipHandGear() {
        RpgCharacterImpl character = new RpgCharacterImpl(10, 15);
        ItemInventory inventory = ItemInventory.getInstance();
        HandGear handGear = new HandGear("Mighty", "Sword", 5);

        // Add handGear to inventory
        inventory.addItem(handGear);

        // Equip the handGear
        ActionResult result = character.equipItem(handGear);
        assertTrue(result.isSuccess());
        assertEquals("Item 'Mighty Sword' of type 'Hand Gear' equipped successfully!", result.getMessage());
        assertTrue(character.getHandGearList().contains(handGear));

        // Verify the handGear is removed from inventory
        assertFalse(inventory.getInventory().contains(handGear));
    }

    @Test
    public void testRemoveEquippedHeadGear() {
        RpgCharacterImpl character = new RpgCharacterImpl(10, 15);
        ItemInventory inventory = ItemInventory.getInstance();
        HeadGear headGear = new HeadGear("Bright", "Helmet", 5);

        // Add and equip the headGear
        inventory.addItem(headGear);
        character.equipItem(headGear);

        // Remove the equipped headGear
        ActionResult result = character.removeItem(headGear);
        assertTrue(result.isSuccess());
        assertEquals("Item 'Bright Helmet' of type 'Head Gear' removed successfully!", result.getMessage());
        assertNull(character.getHeadGear());

        // Verify the headGear is back in inventory
        assertTrue(inventory.getInventory().contains(headGear));
    }

    @Test
    public void testRemoveNonEquippedItem() {
        RpgCharacterImpl character = new RpgCharacterImpl(10, 15);
        FootGear footGear = new FootGear("Swift", "Boots", 3, 2);

        // Try to remove an item that was not equipped
        ActionResult result = character.removeItem(footGear);
        assertFalse(result.isSuccess());
        assertEquals("Item 'Swift Boots' of type 'Foot Gear' is not equipped!", result.getMessage());
    }

    @Test
    public void testCalculateAttack() {
        RpgCharacterImpl character = new RpgCharacterImpl(10, 15);
        HandGear handGear1 = new HandGear("Mighty", "Sword", 5);
        HandGear handGear2 = new HandGear("Swift", "Shield", 3);
        FootGear footGear = new FootGear("Fast", "Boots", 2, 1);

        // Equip items
        character.getHandGearList().add(handGear1);
        character.getHandGearList().add(handGear2);
        character.getFootGearList().add(footGear);

        // Verify attack calculation
        assertEquals(20, character.getAttack());
    }

    @Test
    public void testCalculateDefense() {
        RpgCharacterImpl character = new RpgCharacterImpl(10, 15);
        HeadGear headGear = new HeadGear("Bright", "Helmet", 5);
        FootGear footGear = new FootGear("Fast", "Boots", 2, 1);

        // Equip items
        character.setHeadGear(headGear);
        character.getFootGearList().add(footGear);

        // Verify defense calculation
        assertEquals(21, character.getDefense());
    }
}