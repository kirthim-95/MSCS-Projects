package Midterm;

public interface Department extends Iterable<Engineer> {
  // try to add `e` to the team of id `teamId`.
  // returns false if the team is full,
  // otherwise returns true
  boolean hire(Engineer e, int teamId);

  // compute and call setBonus() on every engineer
  // In your implementation, let's assume everyone is getting
  // the EXCEED_EXPECTATION rating.
  void giveOutBonus();

  // lay off the engineers whose bonus is below the threshold.
  void layoff(double bonusThreshold);
}
