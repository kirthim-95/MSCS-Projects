package games.core;

import games.config.Constants;
import games.config.GearType;
import games.interfaces.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Battle {

    // Players involved in the battle
    private final Player player1;
    private final Player player2;

    // List of available items for the battle
    private final List<Item> items;

    public Battle(Player player1, Player player2, List<Item> items) {

        if(player1 == null || player2 == null || items.size() != 10) {
            throw new IllegalArgumentException("A battle needs 2 players and at least 10 gears");
        }

        this.player1 = player1;
        this.player2 = player2;
        this.items = new ArrayList<>(items);
    }

    /**
     * Initiates the combat between two players by letting them pick items until all items are used.
     */
    public String initiateCombat() {
        for (int index = 0; index < 10; index++) {
            // Determine the current player: player1 on even turns, player2 on odd turns
            Player currentPlayer = (index % 2 == 0) ? player1 : player2;
            Item bestItem = selectBestItem(currentPlayer);
            if (bestItem != null) {
                currentPlayer.addGear(bestItem);
                items.remove(bestItem);
            }
            printCurrentStatus(currentPlayer);
        }
        return determineWinner();
    }

    /**
     * Selects the best item for the given player based on available slots and rules.
     * @param player The player selecting an item.
     * @return The best available item for the player, or null if no items can be selected.
     */
    public Item selectBestItem(Player player) {

        // If there are no items left, there's nothing to choose. Return null.
        if (items.isEmpty()) {
            System.out.println("Null");
            return null;
        }
        System.out.println("Items size: " + items.size());
        // Step 1: Filter items that can fit into the player's available slots
        List<Item> preferredSlotItemList = items.stream()
                .filter(item -> hasAvailableSlot(player, item))
                .collect(Collectors.toList());

        // If no items fit the available slots, we'll consider all remaining items.
        List<Item> itemListToConsider = preferredSlotItemList.isEmpty() ? items : preferredSlotItemList;

        return itemListToConsider.stream()
                .max(Comparator.comparingDouble(Item::getAttackPower)  // Prioritize max attack power
                        .thenComparingDouble(Item::getDefenseStrength)   // Then prioritize max defense
                        .thenComparing(item -> new Random().nextInt()))  // Random tie-breaker for final choice
                .orElse(null); // If somehow no items qualify, return null.
    }

    /**
     * Checks if the player has an available slot to equip the given item.
     * @param player The player attempting to equip the item.
     * @param item   The item being checked for slot availability.
     * @return True if the player has space to equip the item; false otherwise.
     */
    public boolean hasAvailableSlot(Player player, Item item) {
        boolean result = false;

        if (item.getType() == GearType.HEAD_GEAR) {
            result = player.getHeadGear() == null;
        } else if (item.getType() == GearType.HAND_GEAR) {
            result = player.getHandGearList().size() < 2;
        } else if (item.getType() == GearType.FOOT_GEAR) {
            result = player.getFootGearList().size() < 2;
        }

        return result;
    }


    /**
     * Determines the winner based on the damage calculation.
     * @return A string indicating the winner or a tie.
     */
    public String determineWinner() {
        double damage1 = calculateDamage(player1, player2);
        double damage2 = calculateDamage(player2, player1);

        if (damage1 > damage2) {
            return Constants.PLAYER_1_WINS;
        } else if (damage1 < damage2) {
            return Constants.PLAYER_2_WINS;
        } else {
            return Constants.TIE;
        }
    }

    /**
     * Prints the current status of a player, including their name, attack and defense strength, and gear.
     * @param player The player whose status is being printed.
     */
    private void printCurrentStatus(Player player) {
        System.out.println("====================================");
        System.out.println("        ** PLAYER STATUS **");
        System.out.println("====================================");
        System.out.println("Name: " + player.getName());
        System.out.println("------------------------------------");
        System.out.println("Attack Power: " + player.getTotalAttackPower());
        System.out.println("Defense Strength: " + player.getTotalDefenseStrength());
        System.out.println("------------------------------------");
        // Print player's gear list
        System.out.println("Equipped Gear:");
        List<Item> gears = player.getAllGears();
        if (gears.isEmpty()) {
            System.out.println("   No gear equipped.");
        } else {
            for (Item gear : gears) {
                if (gear != null) {  // Null check before accessing properties
                    System.out.println("   - " + gear.getName() + " (" + gear.getType() + ")");
                } else {
                    System.out.println("   - [Null Gear]");
                }
            }
        }
        System.out.println("====================================");
    }

    /**
     * Calculates the damage dealt by one player to another based on attack and defense values.
     * @param playerOne The attacking player.
     * @param playerTwo The defending player.
     * @return The calculated damage.
     */
    public double calculateDamage(Player playerOne, Player playerTwo) {
        double attack = playerOne.getTotalAttackPower();
        double defense = playerTwo.getTotalDefenseStrength();
        return Math.max(0, attack - defense);
    }

    public List<Item> getItems() {
        return items;
    }
}
