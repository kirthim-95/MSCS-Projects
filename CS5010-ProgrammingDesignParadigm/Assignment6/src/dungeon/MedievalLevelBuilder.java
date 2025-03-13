package dungeon;

public class MedievalLevelBuilder {

    private final Level level;
    private int noOfRooms;
    private int noOfMonsters;
    private int noOfTreasures;

    public MedievalLevelBuilder(int level, int noOfRooms, int noOfMonsters, int noOfTreasures) {

        if(level < 0 || noOfRooms < 0 || noOfMonsters < 0 || noOfTreasures < 0) {
            throw new IllegalArgumentException("Illegal arguments");
        }
        this.level = new Level(level);
        this.noOfRooms = noOfRooms;
        this.noOfMonsters = noOfMonsters;
        this.noOfTreasures = noOfTreasures;

    }

    public void addRoom(String description) {

        if(this.noOfRooms == 0) {
            throw new IllegalStateException("Rooms exceeding for the level!");
        }
        this.noOfRooms--;
        this.level.addRoom(description);
    }

    public void addGoblins(int roomNo, int noOfGoblins) {

        if(noOfMonsters - noOfGoblins < 0) {
            throw new IllegalStateException("Target number of Monsters reached!");
        }

        if(this.level.getRooms().get(roomNo) == null) {
            throw new IllegalStateException("Target room does not exist!");
        }

        while(noOfGoblins-- != 0) {
            this.level.addMonster(roomNo, new Monster("goblin", "mischievous and very unpleasant, vengeful, and greedy creature whose primary purpose is to cause trouble to humankind", 7));
            this.noOfMonsters--;
        }
    }

    public  void addOrc(int roomNo) {

        if(this.noOfMonsters == 0) {
            throw new IllegalStateException("Target number of Monsters reached!");
        }

        if(this.level.getRooms().get(roomNo) == null) {
            throw new IllegalStateException("Target room does not exist!");
        }

        this.level.addMonster(roomNo, new Monster("orc", "brutish, aggressive, malevolent being serving evil", 20));
        this.noOfMonsters--;
    }

    public void addOgre(int roomNo) {

        if(this.noOfMonsters == 0) {
            throw new IllegalStateException("Target number of Monsters reached!");
        }

        if(this.level.getRooms().get(roomNo) == null) {
            throw new IllegalStateException("Target room does not exist!");
        }

        this.level.addMonster(roomNo, new Monster("ogre", "large, hideous man-like being that likes to eat humans for lunch", 50));
        this.noOfMonsters--;
    }

    public void addHuman(int roomNo, String name, String description, int hitPoints) {

        if(this.noOfMonsters == 0) {
            throw new IllegalStateException("Target number of Monsters reached!");
        }

        if(this.level.getRooms().get(roomNo) == null) {
            throw new IllegalStateException("Target room does not exist!");
        }

        this.level.addMonster(roomNo, new Monster(name, description, hitPoints));
        this.noOfMonsters--;
    }

    public void addPotion(int roomNo) {

        if(this.noOfTreasures == 0) {
            throw new IllegalStateException("Target number of Treasures reached!");
        }

        if(this.level.getRooms().get(roomNo) == null) {
            throw new IllegalStateException("Target room does not exist!");
        }

        this.level.addTreasure(roomNo, new Treasure("a healing potion", 1));
        this.noOfTreasures--;
    }

    public void addGold(int roomNo, int noOfPieces) {

        if(this.noOfTreasures == 0) {
            throw new IllegalStateException("Target number of Treasures reached!");
        }

        if(this.level.getRooms().get(roomNo) == null) {
            throw new IllegalStateException("Target room does not exist!");
        }

        this.level.addTreasure(roomNo, new Treasure("pieces of gold", noOfPieces));
        this.noOfTreasures--;
    }

    public void addWeapon(int roomNo, String description) {

        if(this.noOfTreasures == 0) {
            throw new IllegalStateException("Target number of Treasures reached!");
        }

        if(this.level.getRooms().get(roomNo) == null) {
            throw new IllegalStateException("Target room does not exist!");
        }

        this.level.addTreasure(roomNo, new Treasure(description, 10));
        this.noOfTreasures--;
    }

    public void addSpecial(int roomNo, String description, int value) {

        if(this.noOfTreasures == 0) {
            throw new IllegalStateException("Target number of Treasures reached!");
        }

        if(this.level.getRooms().get(roomNo) == null) {
            throw new IllegalStateException("Target room does not exist!");
        }

        this.level.addTreasure(roomNo, new Treasure(description, value));
        this.noOfTreasures--;
    }

    public Level build() {

        if(this.noOfTreasures != 0 || this.noOfMonsters != 0 || this.noOfRooms != 0) {
            throw new IllegalStateException("Level is not constructed yet!");
        }

        return this.level;
    }
}