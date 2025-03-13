package Midterm;

public class SDE implements Engineer {

    private final String name;
    private final double baseSalary;
    private double bonus;
    private final int linesOfCode;
    private final int noOfDesignDocs;

    public SDE(String name, double baseSalary, int linesOfCode, int noOfDesignDocs) {
        this.name = name;
        this.baseSalary = baseSalary;
        this.linesOfCode = linesOfCode;
        this.bonus = 0;
        this.noOfDesignDocs = noOfDesignDocs;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setBonus(Rating rating) {
        double R = this.linesOfCode / 80.0 + this.noOfDesignDocs / 5.0;
        if(rating == Rating.EXCEED_EXPECTATION) {
            R *= 1.2;
        } else if (rating == Rating.SUPERB) {
            R *= 1.7;
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
        return this.name.compareTo(o.getName());
    }
}
