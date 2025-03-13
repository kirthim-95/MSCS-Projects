package rpg;

public interface Battle {
    ActionResult equipItem(int characterIndex, Item item);

    ActionResult removeItem(int characterIndex, Item item);

    ActionResult goToBattle();
}
