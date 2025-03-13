package Midterm;

public class SeniorSDE implements Engineer {

    private final String name;
    private final double baseSalary;
    private double bonus;
    private final int noOfReports;

    public SeniorSDE(String name, double baseSalary, int noOfReports) {
        this.name = name;
        this.baseSalary = baseSalary;
        this.noOfReports = noOfReports;
        this.bonus = 0;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setBonus(Rating rating) {
        double R = noOfReports / 5.0;
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
        return this.name.compareTo(o.getName());
    }
}
