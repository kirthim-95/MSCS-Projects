package Midterm;

import java.util.Iterator;

public class Test {
  public static void main(String[] args) {

    OrderedListImpl<Integer> orderedList = new OrderedListImpl<>();
    orderedList.add(4);
    System.out.println(orderedList.toString());
    orderedList.add(5);
    System.out.println(orderedList.toString());
    orderedList.add(2);
    System.out.println(orderedList.toString());
    System.out.println(orderedList.get(1));
    System.out.println(orderedList.size());
    System.out.println(orderedList.subList(e -> e > 2));

    Department d = DepartmentImpl.getDepartmentImpl();
    // senior SDE to team 0
    d.hire(new SeniorSDE(/*name*/"Tom", /*base salary*/100, /*num of reports*/10), 0);
    // junior SDE to team 0
    d.hire(new SDE(/*name*/"Jack", /*base salary*/100, /*lines of code*/200, /*num of design doc*/2), 0);
    // junior SDE to team 2
    d.hire(new JuniorSDE(/*name*/"Elon", /*base salary*/200, /*lines of code*/200000), 2);

    System.out.println("Initial state....");
    Iterator<Engineer> it = d.iterator();
    int idx = 0;
    // expected output:
    // 0 Jack $0.0
    // 1 Tom $0.0
    // 2 Elon $0.0
    while(it.hasNext()) {
      Engineer e = it.next();
      System.out.println(idx + " " + e.getName() + " $" + e.getBonus());
      idx++;
    }

    // give out bonus
    System.out.println("Giving out bonus....");
    d.giveOutBonus();

    it = d.iterator();
    idx = 0;
    // expected output:
    // 0 Jack $348.0
    // 1 Tom $200.0
    // 2 Elon $400000.0
    while(it.hasNext()) {
      Engineer e = it.next();
      System.out.println(idx + " " + e.getName() + " $" + e.getBonus());
      idx++;
    }

    System.out.println("Lay off..");
    d.layoff(300000.0);
    it = d.iterator();
    idx = 0;
    // expected output:
    // 0 Elon $400000.0
    while(it.hasNext()) {
      Engineer e = it.next();
      System.out.println(idx + " " + e.getName() + " $" + e.getBonus());
      idx++;
    }
  }
}
