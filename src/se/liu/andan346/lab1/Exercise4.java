package se.liu.andan346.lab1;

import javax.swing.JOptionPane;

public class Exercise4
{
    public static void main(String[] args) {
	// Initialize variables
	int tabell = 0;
	boolean validInput;

	do {
	    // Get input
	    String input = JOptionPane.showInputDialog("Please input a value");

	    validInput = true;  // Assume input valid
	    // Try parse input as integer
	    try {
		tabell = Integer.parseInt(input);
	    }
	    // Set input non-valid if an exception is found
	    catch (NumberFormatException e) {
		validInput = false;
	    }
	}
	while (!validInput);  // Repeat until input is valid

	// Print 1*input,2*input,...,12*input
	for (int i = 1; i <= 12; i++)
		System.out.println(i*tabell);
    }
}
