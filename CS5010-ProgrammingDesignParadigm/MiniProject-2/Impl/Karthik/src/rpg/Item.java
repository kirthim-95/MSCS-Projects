package rpg;

public interface Item {
    String getType();

    String getAdjective();

    String getNoun();

    String getName();

    int getAttack();

    int getDefense();

    ActionResult equip(RpgCharacterImpl character);
}