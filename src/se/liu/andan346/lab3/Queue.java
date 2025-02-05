package se.liu.andan346.lab3;

import se.liu.andan346.lab1.Person;

public class Queue extends ListManipulator
{
    public void enqueue(Person e) {
	elements.add(e);
    }

    public Person dequeue() {
	Person e = elements.getFirst();
	elements.removeFirst();
	return e;
    }
}
