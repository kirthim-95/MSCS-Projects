package games;

import games.config.AvailableGears;
import games.core.Battle;
import games.core.Player;
import games.interfaces.Item;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Player player1 = new Player("JoyJester", 50, 30);
        Player player2 = new Player("QuestKing", 40, 40);

        List<Item> availableItems = new ArrayList<>();
        Random random = new Random();
        Set<AvailableGears> selectedGears = new HashSet<>(); // Track selected unique gears
        int numberOfItemsToChoose = 10; // Number of unique items to pick

        // Randomly choose unique items from the AvailableGears enum
        while (selectedGears.size() < numberOfItemsToChoose) {
            int randomIndex = random.nextInt(AvailableGears.values().length);
            AvailableGears randomGear = AvailableGears.values()[randomIndex];
            if (!selectedGears.contains(randomGear)) { // Ensure no duplicates
                selectedGears.add(randomGear);
                availableItems.add(randomGear.chooseItem());
            }
        }

        Battle battle = new Battle(player1, player2, availableItems);

        String result = battle.initiateCombat();
        System.out.println(result);

    }
}
