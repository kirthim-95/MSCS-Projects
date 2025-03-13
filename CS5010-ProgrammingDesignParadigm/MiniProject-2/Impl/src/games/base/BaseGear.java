package games.base;

import games.interfaces.GearFactory;
import games.interfaces.Item;
import games.config.GearType;

import java.util.Random;

/**
 * This is the abstract class representing base gear items in the game.
 * It contains essential attributes and methods related to gear.
 * Each gear has attack and defense properties, a name, and a type.
 */
public abstract class BaseGear implements Item {

    private final String noun;
    private final String adjective;
    private final GearType type;
    private final double attackPower;
    private final double defenseStrength;

    //Constructor to initialize a gear item.
    public BaseGear(GearType type, String noun, String adjective, double attackPower, double defenseStrength) {

        // Validate that the gear has valid attributes
        if (type == null || adjective == null || noun == null || adjective.isEmpty() || noun.isEmpty()) {
            throw new IllegalArgumentException("Invalid gear item!");
        }

        this.type = type;
        this.noun = noun;
        this.adjective = adjective;
        this.attackPower = attackPower;
        this.defenseStrength = defenseStrength;
    }

    /**
     * Abstract method that subclasses must implement to define how to combine items.
     *
     * @param item the item to combine with
     * @return Item the combined item
     */
    @Override
    public abstract Item combineItems(Item item);

    /**
     * Combines the names of two gear items based on their stats.
     * The gear with higher attack power will have its full name preserved,
     * and the other will only contribute its adjective to the name.
     * In case of a tie, a random choice is made between the two.
     */
    public String combineNames(Item other) {

        if (this.attackPower < other.getAttackPower() || (this.attackPower == other.getAttackPower() && this.defenseStrength > other.getDefenseStrength())) {
            return this.adjective + ", " + other.getName();
        } else if (this.attackPower > other.getAttackPower() || (this.attackPower == other.getAttackPower() && this.defenseStrength < other.getDefenseStrength())) {
            return other.getAdjective() + ", " + this.getName();
        } else {
            Random random = new Random();
            return random.nextBoolean() ? this.adjective + ", " + other.getName() : other.getAdjective() + ", " + this.getName();
        }
    }

    protected Item combineAttributes(Item other, GearType expectedType, GearFactory factory) {
        if (other.getType() != expectedType) {
            throw new IllegalArgumentException("Similar gear items can only be combined!");
        }

        String newName = combineNames(other);
        String[] nameParts = newName.split(",", 2);
        String newNoun = nameParts[1].trim();
        String newAdjective = nameParts[0].trim();

        double newAttackPower = this.attackPower + other.getAttackPower();
        double newDefenseStrength = this.defenseStrength + other.getDefenseStrength();

        // Delegate the creation of the new item to the factory
        return factory.createItem(newNoun, newAdjective, newAttackPower, newDefenseStrength);
    }


    @Override
    public String getNoun() {
        return this.noun;
    }

    @Override
    public String getAdjective() {
        return this.adjective;
    }

    @Override
    public GearType getType() {
        return this.type;
    }

    @Override
    public double getAttackPower() {
        return this.attackPower;
    }

    @Override
    public double getDefenseStrength() {
        return this.defenseStrength;
    }

    @Override
    public String getName() {
        return this.adjective + " " + this.noun;
    }
}
