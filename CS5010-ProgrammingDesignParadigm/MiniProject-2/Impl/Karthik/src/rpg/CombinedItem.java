package rpg;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CombinedItem extends BaseItem {
    private final List<Item> combinedItemList;

    public CombinedItem(ItemType type, Item firstItem, Item secondItem) {
        super(type, "", "", 0, 0);
        this.combinedItemList = new ArrayList<>();
        this.combinedItemList.add(firstItem);
        this.combinedItemList.add(secondItem);

        this.attack = firstItem.getAttack() + secondItem.getAttack();
        this.defense = firstItem.getDefense() + secondItem.getDefense();

        // Determine combined name
        if (firstItem.getAttack() > secondItem.getAttack() ||
                (firstItem.getAttack() == secondItem.getAttack() && firstItem.getDefense() > secondItem.getDefense())) {
            this.adjective = secondItem.getAdjective() + ", " + firstItem.getAdjective();
            this.noun = firstItem.getNoun();
        } else if (firstItem.getAttack() < secondItem.getAttack() ||
                (firstItem.getAttack() == secondItem.getAttack() && firstItem.getDefense() < secondItem.getDefense())) {
            this.adjective = firstItem.getAdjective() + ", " + secondItem.getAdjective();
            this.noun = secondItem.getNoun();
        } else {
            Random random = new Random();
            if (random.nextBoolean()) {
                this.adjective = secondItem.getAdjective() + ", " + firstItem.getAdjective();
                this.noun = firstItem.getNoun();
            } else {
                this.adjective = firstItem.getAdjective() + ", " + secondItem.getAdjective();
                this.noun = secondItem.getNoun();
            }
        }
    }

    public List<Item> getCombinedItemList() {
        return combinedItemList;
    }

    @Override
    public ActionResult equip(RpgCharacterImpl character) {
        // Only a BaseItem can be equipped, and this is handled in each of the ItemType directly.
        // CombinedItem, although it extends BaseItem, doesn't need to handle this, as a CombinedItem can never be equipped directly to the Character.
        return null;
    }
}
