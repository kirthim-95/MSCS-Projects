package rpg;

import java.util.List;

public interface RpgCharacter {
    ActionResult equipItem(Item item);

    ActionResult removeItem(Item item);

    int getAttack();

    int getDefense();

    Item getHeadGear();

    void setHeadGear(Item headGear);

    List<Item> getHandGearList();

    List<Item> getFootGearList();
}