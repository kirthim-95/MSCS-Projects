package games;

import games.base.BaseGear;
import games.config.GearType;
import games.interfaces.Item;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaseGearTest {

    @Test
    void testConstructorValidation() {
        assertThrows(IllegalArgumentException.class, () -> new BaseGear(null, "Noun", "Adjective", 10, 10) {
            @Override
            public Item combineItems(Item item) {
                return null;
            }
        });
    }

    @Test
    void testCombineNames() {
        BaseGear gear1 = new BaseGear(GearType.HAND_GEAR, "Sword", "Sharp", 10, 10) {
            @Override
            public Item combineItems(Item item) {
                return null;
            }
        };
        BaseGear gear2 = new BaseGear(GearType.HAND_GEAR, "Shield", "Sturdy", 5, 15) {
            @Override
            public Item combineItems(Item item) {
                return null;
            }
        };
        String combinedName = gear1.combineNames(gear2);
        assertNotNull(combinedName);
    }

    @Test
    void testCombineNamesWithEqualPowers() {
        BaseGear gear1 = new BaseGear(GearType.HAND_GEAR, "Sword", "Sharp", 10, 10) {
            @Override
            public Item combineItems(Item item) {
                return null;
            }
        };
        BaseGear gear2 = new BaseGear(GearType.HAND_GEAR, "Shield", "Sturdy", 10, 10) {
            @Override
            public Item combineItems(Item item) {
                return null;
            }
        };
        String combinedName = gear1.combineNames(gear2);
        assertNotNull(combinedName);
    }

}