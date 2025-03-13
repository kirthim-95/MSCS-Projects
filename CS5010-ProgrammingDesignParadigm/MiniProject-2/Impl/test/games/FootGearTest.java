package games;

import games.config.GearType;
import games.impl.FootGear;
import games.impl.HandGear;
import games.interfaces.Item;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FootGearTest {

    @Test
    public void testFootGearCreation() {
        FootGear gear = new FootGear("Sandals", "Swift", 10, 15);
        assertEquals("Sandals", gear.getNoun());
        assertEquals("Swift", gear.getAdjective());
        assertEquals(GearType.FOOT_GEAR, gear.getType());
        assertEquals(10, gear.getAttackPower());
        assertEquals(15, gear.getDefenseStrength());
    }

    @Test
    public void testCombineItems() {
        FootGear gear1 = new FootGear("Boots", "Mighty", 10, 20);
        FootGear gear2 = new FootGear("Shoes", "Swift", 15, 10);
        Item combined = gear1.combineItems(gear2);
        assertNotNull(combined);
        assertEquals("Mighty Swift Shoes", combined.getName());
        assertEquals(25, combined.getAttackPower());
        assertEquals(30, combined.getDefenseStrength());
    }

    @Test
    public void testCombineItemsInvalidType() {
        FootGear gear = new FootGear("Boots", "Swift", 10, 15);
        HandGear invalidGear = new HandGear("Gloves", "Cold", 8);

        assertThrows(IllegalArgumentException.class, () -> gear.combineItems(invalidGear));
    }
}
