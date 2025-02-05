package se.liu.andan346.lab3;

import se.liu.andan346.lab1.Person;

import java.time.LocalDate;
import java.util.ArrayList;

public class Tester
{
    public static void main(String[] args) {
	Person[] people = new Person[]{
		new Person("Person 1", LocalDate.of(1990, 2, 10)),
		new Person("Person 2", LocalDate.of(2001, 4, 30)),
		new Person("Person 3", LocalDate.of(2004, 6, 22)),
		new Person("Person 4", LocalDate.of(2003, 3, 3)),
		new Person("Person 5", LocalDate.now())
	};

	// Stack
	Stack stack = new Stack();
	for (Person person : people) stack.push(person);

	System.out.println("Stack:");
	while (!stack.isEmpty()) System.out.println(stack.pop());

	// Queue
	Queue queue = new Queue();
	for (Person person : people) queue.enqueue(person);

	System.out.println("Queue:");
	while (!queue.isEmpty()) System.out.println(queue.dequeue());
    }
}
