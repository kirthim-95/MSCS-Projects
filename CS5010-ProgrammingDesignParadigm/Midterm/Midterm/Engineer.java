package Midterm;

public interface Engineer extends Comparable<Engineer> {
  // get the name
  String getName();

  // compute and set bonus according to the rules
  void setBonus(Rating rating);

  // get the computed bonus
  double getBonus();

  // made-up functions to make setBonus long enough so that it shouldn't be duplicated
  // Those should remain as blackboxes to you.
  default double pullDepartmentProfit() {return 0;}
  default double pullNASDQIndex() {return 0;}
  default double pullManagerMood() {return 0;}
  default double pullCPI() {return 0;}
}
