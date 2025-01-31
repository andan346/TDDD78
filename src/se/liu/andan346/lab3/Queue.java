package se.liu.andan346.lab3;

import se.liu.andan346.lab1.Person;

public class Queue extends ListManipulator
{

    public boolean add(final Person person) {
	return elements.add(person);
    }

    public boolean remove(final Object o) {
	return elements.remove(o);
    }

    public void clear() {
	elements.clear();
    }

    public void enqueue(Person e) {
	elements.add(e);
    }

    public Person dequeue() {
	Person e = elements.getFirst();
	elements.removeFirst();
	return e;
    }
}
