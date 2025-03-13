package games.impl;

import games.base.BaseGear;
import games.interfaces.Item;
import games.config.GearType;

// HandGear class represents a piece of footwear gear, extending from BaseGear
public class HandGear extends BaseGear {

    private static final double DEFENSE_STRENGTH = 0;

    public HandGear(String noun, String adjective, double attackPower, double DEFENSE_STRENGTH) {
        super(GearType.HAND_GEAR, noun, adjective, attackPower, DEFENSE_STRENGTH);
    }

    public HandGear(String noun, String adjective, double attackPower) {
        super(GearType.HAND_GEAR, noun, adjective, attackPower, DEFENSE_STRENGTH);
    }

    @Override
    public HandGear combineItems(Item item) {
        return (HandGear) combineAttributes(item, GearType.HAND_GEAR, HandGear::new);
    }
}
