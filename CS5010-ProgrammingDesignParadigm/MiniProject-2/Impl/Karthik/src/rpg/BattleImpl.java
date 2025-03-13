package rpg;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class BattleImpl implements Battle {
    private final RpgCharacter firstCharacter;
    private final RpgCharacter secondCharacter;
    private final List<Item> itemList;

    public BattleImpl(RpgCharacter firstCharacter, RpgCharacter secondCharacter) {
        if (firstCharacter == null || secondCharacter == null) {
            throw new IllegalArgumentException("Characters must be defined!");
        }
        this.firstCharacter = firstCharacter;
        this.secondCharacter = secondCharacter;
        this.itemList = ItemInventory.getInstance().getInventory();
    }

    @Override
    public ActionResult equipItem(int characterIndex, Item item) {
        RpgCharacter character = (characterIndex == 1) ? firstCharacter : secondCharacter;
        return character.equipItem(item);
    }

    @Override
    public ActionResult removeItem(int characterIndex, Item item) {
        RpgCharacter character = (characterIndex == 1) ? firstCharacter : secondCharacter;
        return character.removeItem(item);
    }

    @Override
    public ActionResult goToBattle() {
        for (int index = 0; index < 10; index++) {
            RpgCharacter currentCharacter = (index % 2 == 0) ? firstCharacter : secondCharacter;

            Item bestItem = selectBestItem(currentCharacter);

            if (bestItem != null) {
                currentCharacter.equipItem(bestItem);
                itemList.remove(bestItem);
            }

            System.out.printf("Turn %d: %s!", index + 1, currentCharacter);
        }

        return determineWinner();
    }

    private Item selectBestItem(RpgCharacter character) {
        if (itemList.isEmpty()) {
            return null;
        }

        // Step 1: Filter items that fit available slots
        List<Item> preferredSlotItemList = itemList.stream()
                .filter(item -> hasAvailableSlot(character, item))
                .toList();

        // Step 2: Determine the list of items to consider
        List<Item> itemListToConsider = preferredSlotItemList.isEmpty() ? itemList : preferredSlotItemList;

        // Step 3: Choose the best item based on rules
        return itemListToConsider.stream()
                .max(Comparator.comparingInt(Item::getAttack)  // Rule 2: Max attack
                        .thenComparingInt(Item::getDefense)   // Rule 3: Max defense
                        .thenComparing(item -> new Random().nextInt())) // Rule 4: Random tie-breaker
                .orElse(null); // Return null if no items are available
    }

    private boolean hasAvailableSlot(RpgCharacter character, Item item) {
        boolean result = false;

        if (item instanceof HeadGear) {
            result = character.getHeadGear() == null;
        } else if (item instanceof HandGear) {
            result = character.getHandGearList().size() < 2;
        } else if (item instanceof FootGear) {
            result = character.getFootGearList().size() < 2;
        }

        return result;
    }

    private ActionResult determineWinner() {
        int damageToFirstCharacter = Math.max(0, secondCharacter.getAttack() - firstCharacter.getDefense());
        int damageToSecondCharacter = Math.max(0, firstCharacter.getAttack() - secondCharacter.getDefense());

        System.out.printf("First Character: Attack = %d, Defense = %d, Damage Taken = %d\n",
                firstCharacter.getAttack(), firstCharacter.getDefense(), damageToFirstCharacter);
        System.out.printf("Second Character: Attack = %d, Defense = %d, Damage Taken = %d\n",
                secondCharacter.getAttack(), secondCharacter.getDefense(), damageToSecondCharacter);

        if (damageToFirstCharacter < damageToSecondCharacter) {
            return new ActionResult(true, "First Character wins!");
        } else if (damageToSecondCharacter < damageToFirstCharacter) {
            return new ActionResult(true, "Second Character wins!");
        } else {
            return new ActionResult(true, "It's a tie!");
        }
    }
}