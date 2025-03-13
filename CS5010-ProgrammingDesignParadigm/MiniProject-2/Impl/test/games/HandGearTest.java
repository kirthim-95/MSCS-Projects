package games;

import games.config.GearType;
import games.impl.FootGear;
import games.impl.HandGear;
import games.interfaces.Item;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HandGearTest {

    @Test
    void testHandGearCreation() {
        HandGear gear = new HandGear("Gloves", "Warm", 10);
        assertEquals("Gloves", gear.getNoun());
        assertEquals("Warm", gear.getAdjective());
        assertEquals(GearType.HAND_GEAR, gear.getType());
        assertEquals(10, gear.getAttackPower());
        assertEquals(0, gear.getDefenseStrength());
    }

    @Test
    void testCombineItems() {
        HandGear gear1 = new HandGear("Gloves", "Swift", 10);
        HandGear gear2 = new HandGear("Bracers", "Strong", 15);
        Item combined = gear1.combineItems(gear2);
        assertNotNull(combined);
        assertEquals("Swift Strong Bracers", combined.getName());
        assertEquals(25, combined.getAttackPower());
    }

    @Test
    void testCombineItemsInvalidType() {
        HandGear gear = new HandGear("Gloves", "Warm", 10);
        FootGear invalidGear = new FootGear("Boots", "Mighty", 15, 10);

        assertThrows(IllegalArgumentException.class, () -> gear.combineItems(invalidGear));
    }
}