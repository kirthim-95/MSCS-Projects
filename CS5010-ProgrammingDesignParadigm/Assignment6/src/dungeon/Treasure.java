package dungeon;

/**
 * An object representing treasure in the dungeon.
 */
public class Treasure {

  private final String description;
  private final int value;

  /**
   * Constructor for a Treasure object.
   * 
   * @param description the description
   * @param value       the value
   */
  public Treasure(String description, int value) {
    this.description = description;
    this.value = value;
  }

  /**
   * Accessor for the description.
   * 
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Accessor for the value.
   * 
   * @return the value.
   */
  public int getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.format("%s (value = %d)", description, value);
  }
}
