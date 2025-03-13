package rpg;

import java.util.ArrayList;
import java.util.List;

public class ItemInventory {
    private static ItemInventory instance;
    private final List<Item> inventory;

    private ItemInventory() {
        this.inventory = new ArrayList<>();
    }

    public static ItemInventory getInstance() {
        if (instance == null) {
            instance = new ItemInventory();
        }
        return instance;
    }

    public boolean addItem(Item item) {
        if (!inventory.contains(item)) {
            inventory.add(item);
            return true;
        }
        return false;
    }

    public boolean removeItem(Item item) {
        return inventory.remove(item);
    }

    public void clearInventory() {
        inventory.clear();
    }

    public List<Item> getInventory() {
        return new ArrayList<>(inventory);
    }
}
