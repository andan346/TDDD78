package se.liu.andan346.tetris;

import java.util.Random;

public enum SquareType
{
    //\u001B[38;5;<code>m
    EMPTY('⬚', 255), // 255 ⬚
    I('▣', 50), // 50
    O('▦', 220), // 220
    T('▩', 127), // 127
    S('▨', 40), // 40
    Z('▧', 196), // 196
    J('▥', 204), // ██ 204
    L('▤', 202); // 202

    private final char symbol;
    private int color = 255;

    SquareType(char symbol, int color) {
	this.symbol = symbol;
	this.color = color;
    }

    public String formatted() {
	return String.format("\u001B[38;5;%dm%c", color, symbol);
    }

    public String asSymbol() {
	return String.valueOf(symbol);
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
