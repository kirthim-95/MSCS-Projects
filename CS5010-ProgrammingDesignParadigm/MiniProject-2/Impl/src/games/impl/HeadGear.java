package games.impl;

import games.base.BaseGear;
import games.interfaces.Item;
import games.config.GearType;

// HeadGear class represents a piece of footwear gear, extending from BaseGear
public class HeadGear extends BaseGear {

    private static final double ATTACK_POWER = 0;

    public HeadGear(String noun, String adjective, double attackPower, double defenseStrength) {
        super(GearType.HEAD_GEAR, noun, adjective, ATTACK_POWER, defenseStrength);
    }

    public HeadGear(String noun, String adjective, double defenseStrength) {
        super(GearType.HEAD_GEAR, noun, adjective, ATTACK_POWER, defenseStrength);
    }

    @Override
    public HeadGear combineItems(Item item) {
        return (HeadGear) combineAttributes(item, GearType.HEAD_GEAR, HeadGear::new);
    }
}
