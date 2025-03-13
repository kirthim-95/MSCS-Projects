package rpg;

import java.util.List;

public class HandGear extends BaseItem {
    public HandGear(String adjective, String noun, int attack) {
        super(ItemType.HANDGEAR, adjective, noun, attack, 0);
    }

    @Override
    public ActionResult equip(RpgCharacterImpl character) {
        List<Item> handGearList = character.getHandGearList();
        if (handGearList.size() >= 2) {
            Item combinedHandGear = new CombinedItem(ItemType.HANDGEAR, handGearList.getFirst(), handGearList.getLast());
            handGearList.set(0, combinedHandGear);
            handGearList.set(1, this);
        } else {
            handGearList.add(this);
        }
        return new ActionResult(true, "Item '" + this.getName() + "' of type '" + this.getType() + "' equipped successfully!");
    }
}
