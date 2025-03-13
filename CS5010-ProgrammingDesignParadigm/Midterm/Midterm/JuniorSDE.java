package Midterm;

public class JuniorSDE implements Engineer {

    private final String name;
    private final double baseSalary;
    private double bonus;
    private final int linesOfCode;

    public JuniorSDE(String name, double baseSalary, int linesOfCode) {
        this.name = name;
        this.baseSalary = baseSalary;
        this.linesOfCode = linesOfCode;
        this.bonus = 0;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setBonus(Rating rating) {
        double R = linesOfCode / 100.0;
        if(rating == Rating.SUPERB) {
            R = R * 2.0;
        }
        this.bonus = computeAndSetBonus(rating, R);
    }

    private double computeAndSetBonus(Rating rating, double R) {
        double bonus = baseSalary;
        bonus += pullDepartmentProfit() + pullNASDQIndex() + pullManagerMood() + pullCPI();
        return bonus * R;
    }

    @Override
    public double getBonus() {
        return this.bonus;
    }

    @Override
    public int compareTo(Engineer o) {
        return this.getName().compareTo(o.getName());
    }
}
