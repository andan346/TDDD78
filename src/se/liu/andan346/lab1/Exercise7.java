package se.liu.andan346.lab1;

import javax.swing.*;

public class Exercise7
{
    public static void main(String[] args) {
	final int min = 10;
	final int max = 20;

	String input = JOptionPane.showInputDialog("'for' or 'while'");

	switch (input.toLowerCase()) {
	    case "for":
		System.out.println(sumFor(min, max));
		break;
	    case "while":
		System.out.println(sumWhile(min, max));
		break;
	    default:
		System.out.println("Did not recognize \"" + input + "\". Please enter 'for' or 'while'.");
	}
    }

    public static int sumFor(int min, int max) {
	int sum = 0;
	for (int i = min; i <= max; i++) {
	    sum += i;
	}
	return sum;
    }

    public static int sumWhile(int min, int max) {
	int sum = 0;
	int i = min;
	while (i <= max) {
	    sum += i;
	    i++;
	}
	return sum;
    }
}
