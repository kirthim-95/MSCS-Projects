package rpg;

public class HeadGear extends BaseItem {
    public HeadGear(String adjective, String noun, int defense) {
        super(ItemType.HEADGEAR, adjective, noun, 0, defense);
    }

    @Override
    public ActionResult equip(RpgCharacterImpl character) {
        if (character.getHeadGear() != null) {
            // Combine with the existing headgear
            character.setHeadGear(new CombinedItem(ItemType.HEADGEAR, character.getHeadGear(), this));
        } else {
            character.setHeadGear(this);
        }
        return new ActionResult(true, "Item '" + this.getName() + "' of type '" + this.getType() + "' equipped successfully!");
    }
}
