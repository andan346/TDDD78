package se.liu.andan346.lab1;

import javax.swing.JOptionPane;

public class Exercise4
{
    public static void main(String[] args) {
	String input = JOptionPane.showInputDialog("Please input a value");
	int tabell = Integer.parseInt(input);

	for (int i = 1; i <= 12; i++) {
	    System.out.println(i + " * "+ tabell + " = " + i*tabell);
	}
    }
}
