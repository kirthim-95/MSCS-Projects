package games.interfaces;

public interface GearFactory {
    Item createItem(String noun, String adjective, double attackPower, double defenseStrength);
}
