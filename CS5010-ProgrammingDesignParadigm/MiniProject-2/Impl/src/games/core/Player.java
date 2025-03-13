package games.core;

import games.config.Constants;
import games.config.GearType;
import games.interfaces.Item;
import games.impl.FootGear;
import games.impl.HandGear;
import games.impl.HeadGear;

import java.util.ArrayList;
import java.util.List;

public class Player {

    // Player's name, base attack, base defense, and lists for their gear
    private final String name;
    private final double baseAttack;
    private final double baseDefense;
    private final List<HandGear> handGearList;
    private final List<FootGear> footGearList;
    private HeadGear headGear;

    /**
     * Constructor for the Player class. Initializes a player with a name, base attack, and base defense.
     * @param name The name of the player.
     * @param baseAttack The base attack power of the player.
     * @param baseDefense The base defense strength of the player.
     * @throws IllegalArgumentException If the name is null or empty.
     */
    public Player(String name, double baseAttack, double baseDefense) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be null or empty");
        }
        this.name = name;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
        this.headGear = null;
        this.handGearList = new ArrayList<>();
        this.footGearList = new ArrayList<>();
    }

    /**
     * Constructor for the Player class that initializes a player with just a name (default attack and defense are 0).
     * @param name The name of the player.
     */
    public Player(String name) {
        this(name, 0, 0);
    }

    public void addGear(Item gear) {
        if (gear == null) {
            throw new IllegalArgumentException("Gear cannot be null!");
        }
        // Process the gear based on its type
        switch (gear.getType()) {
            case HEAD_GEAR:
                HeadGear newHeadGear = new HeadGear(gear.getNoun(), gear.getAdjective(), gear.getDefenseStrength());
                if (headGear == null) {
                    this.headGear = newHeadGear;
                } else {
                    this.headGear = this.headGear.combineItems(newHeadGear);
                }
                break;
            case HAND_GEAR:
                HandGear newHandGear = new HandGear(gear.getNoun(), gear.getAdjective(), gear.getAttackPower(), gear.getDefenseStrength());
                if (handGearList.size() < Constants.MAX_GEARS) {
                    this.handGearList.add(newHandGear);
                } else {
                    this.handGearList.add(this.handGearList.get(1).combineItems(newHandGear));
                }
                break;
            case FOOT_GEAR:
                FootGear newFootGear = new FootGear(gear.getNoun(), gear.getAdjective(), gear.getAttackPower(), gear.getDefenseStrength());
                if (footGearList.size() < Constants.MAX_GEARS) {
                    this.footGearList.add(newFootGear);
                } else {
                    this.footGearList.add(this.footGearList.get(1).combineItems(newFootGear));
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid gear type!");
        }
    }

    /**
     * Calculates the player's total attack power, including base attack and all hand/foot gear.
     * @return The total attack power.
     */
    public double getTotalAttackPower() {
        double totalAttackPower = baseAttack;
        for (HandGear handGear : handGearList) {
            totalAttackPower += handGear.getAttackPower();
        }
        for (FootGear footGear : footGearList) {
            totalAttackPower += footGear.getAttackPower();
        }
        return totalAttackPower;
    }

    /**
     * Calculates the player's total defense strength, including base defense and all head/foot gear.
     * @return The total defense strength.
     */
    public double getTotalDefenseStrength() {
        double totalDefenseStrength = baseDefense;
        if (headGear != null) {
            totalDefenseStrength += headGear.getDefenseStrength();
        }
        for (FootGear footGear : footGearList) {
            totalDefenseStrength += footGear.getDefenseStrength();
        }
        return totalDefenseStrength;
    }

    /**
     * Gets a list of all the gears (headgear, handgear, and footgear) that the player is wearing.
     * @return A list of the player's gears.
     */
    public List<Item> getAllGears() {
        List<Item> gears = new ArrayList<>();
        if (this.headGear != null) {
            gears.add(this.headGear);
        }
        if (this.handGearList != null && !this.handGearList.isEmpty()) {
            gears.addAll(this.handGearList);
        }
        if (this.footGearList != null && !this.footGearList.isEmpty()) {
            gears.addAll(this.footGearList);
        }
        return gears;
    }

    public String getName() {
        return name;
    }

    public List<HandGear> getHandGearList() {
        return handGearList;
    }

    public List<FootGear> getFootGearList() {
        return footGearList;
    }

    public HeadGear getHeadGear() {
        return headGear;
    }
}
