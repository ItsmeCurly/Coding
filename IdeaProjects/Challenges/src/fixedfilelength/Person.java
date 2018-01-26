package fixedfilelength;

import java.text.NumberFormat;

public class Person implements Comparable<Person>{
    private int salary;
    private String name;

    public Person() {
        this(0, null);
    }

    public Person(int salary) {
        this(salary, null);
    }

    public Person(String name) {
        this(0, name);
    }

    public Person(int salary, String name) {
        this.salary = salary;
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public String getName() {
        return name;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Person o) {
        if(this.salary == o.getSalary()) return 0;
        else if(this.salary > o.getSalary()) return 1;
        else return -1;
    }

    @Override
    public String toString() {
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        return "Person{" +
                "salary=" + fmt.format(salary) +
                ", name='" + name + '\'' +
                '}';
    }
}
