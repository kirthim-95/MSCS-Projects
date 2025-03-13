package dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a level in a dungeon consisting of a number of rooms.
 */
public class Level {

  private final int levelNumber;
  private final List<Room> rooms;

  /**
   * Constructor for a level in our dungeon.
   * 
   * @param levelNumber a numeric representing which level this is.
   */
  public Level(int levelNumber) {
    this.levelNumber = levelNumber;
    this.rooms = new ArrayList<>();
  }
  
  /**
   * Accessor for the level number.
   * @return the level number
   */
  public int getLevelNumber() {
    return levelNumber;
  }
  
  /**
   * Add a room to this level.
   * 
   * @param description the description of the room to add
   */
  public void addRoom(String description) {
    rooms.add(new Room(description));
  }

  /**
   * Adds a monster to a room.
   * 
   * @param roomNumber the room number to add the monster to
   * @param m          the monster to add
   */
  public void addMonster(int roomNumber, Monster m) {
    rooms.get(roomNumber).addMonster(m);
  }

  /**
   * Adds a treasure to a room.
   * 
   * @param roomNumber the room number to add the treasure to
   * @param t          the treasure to add
   */
  public void addTreasure(int roomNumber, Treasure t) {
    rooms.get(roomNumber).addTreasure(t);
  }

  public List<Room> getRooms() {
    return this.rooms;
  }
  
  @Override
  public String toString() {
    String start = String.format("Level %d contains %d rooms:\n", levelNumber, rooms.size());
    StringBuilder sb = new StringBuilder(start);
    for (int i = 0 ; i < rooms.size() ; i++) {
      sb.append("\nRoom ").append(i).append(" -- ").append(rooms.get(i).toString());
    }
    return sb.toString();
  }
}
