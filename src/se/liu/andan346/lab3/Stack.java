package se.liu.andan346.lab3;

import se.liu.andan346.lab1.Person;

public class Stack extends ListManipulator
{
    public void push(Person e) {
	elements.add(e);
    }

    public Person pop() {
	Person e = elements.getLast();
	elements.removeLast();
	return e;
    }
}
