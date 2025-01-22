package se.liu.andan346.lab1;

import javax.swing.JOptionPane;

public class Exercise8
{
    public static void main(String[] args) {
	boolean shouldQuit = false;
	do {
	    if (askUser("Quit?") && askUser("Really?"))
		shouldQuit = true;
	} while (!shouldQuit);
    }

    public static boolean askUser(String question) {
	return JOptionPane.showConfirmDialog(null, question, "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
}
