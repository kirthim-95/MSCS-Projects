package rpg;

import java.util.ArrayList;
import java.util.List;

public class RpgCharacterImpl implements RpgCharacter {
    private final int baseAttack;
    private final int baseDefense;
    private Item headGear;
    private final List<Item> handGearList;
    private final List<Item> footGearList;

    public RpgCharacterImpl(int baseAttack, int baseDefense) {
        if ((baseAttack < 0) || (baseDefense < 0)) {
            throw new IllegalArgumentException("Attack and defense must be non-negative!");
        }

        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
        this.handGearList = new ArrayList<>();
        this.footGearList = new ArrayList<>();
    }

    @Override
    public ActionResult equipItem(Item item) {
        ItemInventory inventory = ItemInventory.getInstance();

        if (!inventory.getInventory().contains(item)) {
            return new ActionResult(false, "Item '" + item.getName() + "' of type '" + item.getType() + "' not available in inventory!");
        }

        ActionResult equipResult = item.equip(this);
        if (equipResult.isSuccess()) {
            inventory.removeItem(item);
        }

        return equipResult;
    }

    @Override
    public ActionResult removeItem(Item item) {
        ItemInventory inventory = ItemInventory.getInstance();

        boolean isRemoved = false;

        if ((headGear != null) && (headGear.equals(item))) {
            headGear = null;
            isRemoved = true;
        } else if (handGearList.remove(item)) {
            isRemoved = true;
        } else if (footGearList.remove(item)) {
            isRemoved = true;
        }

        if (isRemoved) {
            inventory.addItem(item);
            return new ActionResult(true, "Item '" + item.getName() + "' of type '" + item.getType() + "' removed successfully!");
        }

        return new ActionResult(false, "Item '" + item.getName() + "' of type '" + item.getType() + "' is not equipped!");
    }

    @Override
    public int getAttack() {
        int attack = baseAttack;
        for (Item item : handGearList) {
            attack += item.getAttack();
        }
        for (Item item : footGearList) {
            attack += item.getAttack();
        }
        return attack;
    }

    @Override
    public int getDefense() {
        int defense = baseDefense;
        if (headGear != null) {
            defense += headGear.getDefense();
        }
        for (Item item : footGearList) {
            defense += item.getDefense();
        }
        return defense;
    }

    @Override
    public Item getHeadGear() {
        return headGear;
    }

    @Override
    public void setHeadGear(Item headGear) {
        this.headGear = headGear;
    }

    @Override
    public List<Item> getHandGearList() {
        return handGearList;
    }

    @Override
    public List<Item> getFootGearList() {
        return footGearList;
    }
}
