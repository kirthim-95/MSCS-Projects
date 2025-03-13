package games.impl;

import games.base.BaseGear;
import games.interfaces.Item;
import games.config.GearType;

// FootGear class represents a piece of footwear gear, extending from BaseGear
public class FootGear extends BaseGear {

    public FootGear(String noun, String adjective, double attackPower, double defenseStrength) {
        super(GearType.FOOT_GEAR, noun, adjective, attackPower, defenseStrength);
    }

    @Override
    public FootGear combineItems(Item item) {
        return (FootGear) combineAttributes(item, GearType.FOOT_GEAR, FootGear::new);
    }
}
