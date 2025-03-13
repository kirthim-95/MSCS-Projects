package games.config;

import games.impl.FootGear;
import games.impl.HandGear;
import games.impl.HeadGear; // Assuming HeadGear class is defined
import games.interfaces.Item;

public enum AvailableGears {

    // Foot Gear
    DRAGON_SCALE_BOOTS,
    SILVER_HUNTER_SHOES,
    ELVEN_LEATHER_SANDALS,
    MYSTIC_MOON_SLIPPERS,
    TITAN_CLEATS,

    // Hand Gear
    FROST_GLOVES,
    QUICKSTART_BRACERS,
    DEMONIC_GAUNTLETS,
    DRAGONBALL_WRAPS,
    VIGOROUS_BANDS,

    // Head Gear
    TITAN_HELMET,
    DRAGON_SCALED_CROWN,
    ELVEN_MYSTIC_HOOD,
    MAGE_TURBAN,
    WARRIOR_CROWN;

    public Item chooseItem() {
        switch (this) {
            // Foot Gear
            case DRAGON_SCALE_BOOTS:
                return new FootGear("Dragon Scale Boots", "Legendary", 10, 20);
            case SILVER_HUNTER_SHOES:
                return new FootGear("Silver Hunter Shoes", "Shiny", 8, 12);
            case ELVEN_LEATHER_SANDALS:
                return new FootGear("Elven Leather Sandals", "Graceful", 6, 10);
            case MYSTIC_MOON_SLIPPERS:
                return new FootGear("Mystic Moon Slippers", "Mysterious", 7, 13);
            case TITAN_CLEATS:
                return new FootGear("Titan Cleats", "Powerful", 15, 25);

            // Hand Gear
            case FROST_GLOVES:
                return new HandGear("Frost Gloves", "Cold", 10);
            case QUICKSTART_BRACERS:
                return new HandGear("Quickstart Bracers", "Swift", 8);
            case DEMONIC_GAUNTLETS:
                return new HandGear("Demonic Gauntlets", "Cursed", 12);
            case DRAGONBALL_WRAPS:
                return new HandGear("Dragonball Wraps", "Fiery", 14);
            case VIGOROUS_BANDS:
                return new HandGear("Vigorous Bands", "Strength", 6);

            // Head Gear
            case TITAN_HELMET:
                return new HeadGear("Titan Helmet", "Mighty", 18);
            case DRAGON_SCALED_CROWN:
                return new HeadGear("Dragon Scaled Crown", "Majestic", 20);
            case ELVEN_MYSTIC_HOOD:
                return new HeadGear("Elven Mystic Hood", "Enchanted", 14);
            case MAGE_TURBAN:
                return new HeadGear("Mage Turban", "Magical", 12);
            case WARRIOR_CROWN:
                return new HeadGear("Warrior Crown", "Warrior's Honor", 16);

            default:
                throw new IllegalArgumentException("Unknown item type");
        }
    }
}