package rpg;

import java.util.List;

public class FootGear extends BaseItem {
    public FootGear(String adjective, String noun, int attack, int defense) {
        super(ItemType.FOOTGEAR, adjective, noun, attack, defense);
    }

    @Override
    public ActionResult equip(RpgCharacterImpl character) {
        List<Item> footGearList = character.getFootGearList();
        if (footGearList.size() >= 2) {
            Item combinedFootGear = new CombinedItem(ItemType.FOOTGEAR, footGearList.getFirst(), footGearList.getLast());
            footGearList.set(0, combinedFootGear);
            footGearList.set(1, this);
        } else {
            footGearList.add(this);
        }
        return new ActionResult(true, "Item '" + this.getName() + "' of type '" + this.getType() + "' equipped successfully!");
    }
}
