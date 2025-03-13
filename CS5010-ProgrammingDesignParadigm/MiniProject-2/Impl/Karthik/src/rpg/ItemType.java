package rpg;

public enum ItemType {
    HEADGEAR("Head Gear") {
        @Override
        public Item createItem(String adjective, String noun, int attack, int defense) {
            return new HeadGear(adjective, noun, defense);
        }
    },
    HANDGEAR("Hand Gear") {
        @Override
        public Item createItem(String adjective, String noun, int attack, int defense) {
            return new HandGear(adjective, noun, attack);
        }
    },
    FOOTGEAR("Foot Gear") {
        @Override
        public Item createItem(String adjective, String noun, int attack, int defense) {
            return new FootGear(adjective, noun, attack, defense);
        }
    };

    private final String displayName;

    ItemType(String displayName) {
        this.displayName = displayName;
    }

    public String getName() {
        return displayName;
    }

    public abstract Item createItem(String adjective, String noun, int attack, int defense);
}
