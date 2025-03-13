package Midterm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DepartmentImpl implements Department {

    private static DepartmentImpl departmentImpl;

    private final List<OrderedList<Engineer>> teams;
    private static final int MAX_TEAMS = 4;
    private static final int MAX_SIZE = 3;

    private DepartmentImpl() {
        teams = new ArrayList<>();
        for (int i = 0; i < MAX_TEAMS; i++) {
            teams.add(new OrderedListImpl<>());
        }
    }

    public static DepartmentImpl getDepartmentImpl() {
        if (departmentImpl == null) {
            departmentImpl = new DepartmentImpl();
        }
        return departmentImpl;
    }

    @Override
    public boolean hire(Engineer e, int teamId) {
        if(teamId < 0 || teamId > MAX_TEAMS) {
            throw new IndexOutOfBoundsException("Wrong team id");
        }
        OrderedList<Engineer> team = teams.get(teamId);
        if(team.size() >= MAX_SIZE) {
            return false;
        }
        team.add(e);
        return true;
    }

    @Override
    public void giveOutBonus() {
        for(OrderedList<Engineer> team : teams) {
            for(int i = 0; i < team.size(); i++) {
                Engineer e = team.get(i);
                e.setBonus(Rating.EXCEED_EXPECTATION);
            }
        }
    }

    @Override
    public void layoff(double bonusThreshold) {
        for(OrderedList<Engineer> team : teams) {
            OrderedList<Engineer> teamAfterLayoff = team.subList(e -> e.getBonus() >= bonusThreshold);
            teams.set(teams.indexOf(team), teamAfterLayoff);
        }
    }

    @Override
    public Iterator<Engineer> iterator() {
        return new Iterator<>() {

            private int currentTeam = 0;
            private int engineerInTheTeam = 0;

            @Override
            public boolean hasNext() {

                while (currentTeam < teams.size() && engineerInTheTeam >= teams.get(currentTeam).size()) {
                    currentTeam++;
                    engineerInTheTeam = 0;
                }

                return currentTeam < teams.size();
            }

            @Override
            public Engineer next() {

                Engineer engineer = teams.get(currentTeam).get(engineerInTheTeam);
                engineerInTheTeam++;
                return engineer;
            }
        };
    }
}
