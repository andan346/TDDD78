package se.liu.andan346.lab1;

import java.time.LocalDate;
import java.time.Period;

public class Person
{
    private String name;
    private LocalDate birthDay;

    public Person(String name, LocalDate birthDay) {
	this.name = name;
	this.birthDay = birthDay;
    }

    public int getAge() {
	return Period.between(this.birthDay, LocalDate.now()).getYears();
    }

    @Override public String toString() {
	return name + " " + getAge();
    }

    public static void main(String[] args) {
	Person myself = new Person("Andreas", LocalDate.of(2004, 6, 22));
	System.out.println(myself);

	Person leifGW = new Person("Leif GW", LocalDate.of(1945, 3, 12));
	System.out.println(leifGW);

	Person tomten = new Person("Tomten", LocalDate.of(1931, 12, 25));
	System.out.println(tomten);
    }

}
