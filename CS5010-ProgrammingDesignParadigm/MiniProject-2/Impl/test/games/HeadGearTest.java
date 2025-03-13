package games;

import games.config.GearType;
import games.impl.HeadGear;
import games.impl.HandGear;
import games.interfaces.Item;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeadGearTest {

    @Test
    void testHeadGearCreation() {
        HeadGear gear = new HeadGear("Helmet", "Strong", 20);
        assertEquals("Helmet", gear.getNoun());
        assertEquals("Strong", gear.getAdjective());
        assertEquals(GearType.HEAD_GEAR, gear.getType());
        assertEquals(0, gear.getAttackPower());
        assertEquals(20, gear.getDefenseStrength());
    }

    @Test
    void testCombineItems() {
        HeadGear gear1 = new HeadGear("Helmet", "Swift", 15);
        HeadGear gear2 = new HeadGear("Crown", "Majestic", 10);
        Item combined = gear1.combineItems(gear2);

        assertInstanceOf(HeadGear.class, combined);
        assertEquals("Swift", combined.getAdjective());
        assertEquals(25, combined.getDefenseStrength());
    }

    @Test
    void testCombineItemsInvalidType() {
        HeadGear gear = new HeadGear("Helmet", "Strong", 20);
        HandGear invalidGear = new HandGear("Gloves", "Warm", 10);
        assertThrows(IllegalArgumentException.class, () -> gear.combineItems(invalidGear));
    }
}