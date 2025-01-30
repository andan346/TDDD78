package se.liu.andan346.lab3;

import se.liu.andan346.lab1.Person;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Queue
{
    private List<Person> elements = new ArrayList<>();

    public int size() {
	return elements.size();
    }

    public boolean isEmpty() {
	return elements.isEmpty();
    }

    public boolean contains(final Object o) {
	return elements.contains(o);
    }

    public Iterator<Person> iterator() {
	return elements.iterator();
    }

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
	Person e = elements.get(0);
	elements.removeFirst();
	return e;
    }
}
