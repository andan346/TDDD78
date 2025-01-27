package se.liu.andan346.lab1;

import javax.swing.JOptionPane;

public class Exercise9
{
    public static void main(String[] args) {
	String input = JOptionPane.showInputDialog("Please input a value");
	double inputDouble = Double.parseDouble(input);
	System.out.println("Roten ur " + input + " är " + findRoot(inputDouble));
    }

    public static double findRoot(double x) {
	double guess = x;
	for (int i = 0; i < 10; i++)
	    guess -= (guess*guess-x) / (2*guess);
	return guess;
    }
}
