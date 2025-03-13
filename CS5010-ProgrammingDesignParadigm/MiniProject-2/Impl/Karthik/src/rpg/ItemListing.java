package rpg;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum ItemListing {
    SCURRYING_SANDALS(ItemType.FOOTGEAR, "Scurrying", "Sandals", 0, 1),
    HAPPY_HOVERBOARD(ItemType.FOOTGEAR, "Happy", "Hoverboard", 1, 3),
    MIGHTY_SHIELD(ItemType.HANDGEAR, "Mighty", "Shield", 2, 0),
    BRIGHT_HELMET(ItemType.HEADGEAR, "Bright", "Helmet", 0, 2);

    private final ItemType type;
    private final String adjective;
    private final String noun;
    private final int attack;
    private final int defense;

    private static final Map<String, ItemListing> itemMap = Arrays.stream(values()).collect(Collectors.toMap(Enum::name, item -> item));

    ItemListing(ItemType type, String adjective, String noun, int attack, int defense) {
        this.type = type;
        this.adjective = adjective;
        this.noun = noun;
        this.attack = attack;
        this.defense = defense;
    }

    // Factory method to create an item based on its name.
    public static Item createItem(String itemName) {
        ItemListing itemListing = itemMap.get(itemName.toUpperCase());
        if (itemListing == null) {
            throw new IllegalArgumentException("No item found with name: " + itemName);
        }
        return itemListing.type.createItem(itemListing.adjective, itemListing.noun, itemListing.attack, itemListing.defense);
    }
}
