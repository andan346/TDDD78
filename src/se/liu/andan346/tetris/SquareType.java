package se.liu.andan346.tetris;

import java.util.Random;

public enum SquareType
{
    EMPTY("\u001B[38;5;235m⬚"),
    I("\u001B[38;5;50m▣"),
    O("\u001B[38;5;220m▦"),
    T("\u001B[38;5;127m▩"),
    S("\u001B[38;5;40m▨"),
    Z("\u001B[38;5;196m▧"),
    J("\u001B[38;5;204m▥"), // ██
    L("\u001B[38;5;202m▤");

    private final String symbol;

    SquareType(String symbol) {
	this.symbol = symbol;
    }

    public String formatted() {
	return this.symbol;
    }

    public static void main() {
	Random rnd = new Random();
	for (int i = 0; i < 25; i++) {
	    int rndIndex = rnd.nextInt(SquareType.values().length);
	    SquareType rndSquareType = SquareType.values()[rndIndex];
	    System.out.println(rndSquareType);
	}
    }
}
