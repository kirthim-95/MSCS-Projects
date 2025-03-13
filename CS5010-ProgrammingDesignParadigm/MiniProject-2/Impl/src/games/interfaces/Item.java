package games.interfaces;

import games.config.GearType;

// The Item interface defines the common behaviors and properties of gear items in the game
public interface Item {

    String getNoun();

    String getAdjective();

    GearType getType();

    double getAttackPower();

    double getDefenseStrength();

    String getName();

    Item combineItems(Item item);
}
