package se.liu.andan346.tetris.util;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public enum SquareType
{
    OUTSIDE(' ', "000000"),
    EMPTY('⬚', "cccccc"),
    I('▣', "6af2f2"),
    O('▦', "f0f130"),
    T('▩', "a353f1"),
    S('▨', "76f014"),
    Z('▧', "ec462f"),
    J('▥', "1e46f1"),
    L('▤', "f0a030");

    private final char symbol;
    private final String hexColor;

    SquareType(char symbol, String hexColor) {
	this.symbol = symbol;
	this.hexColor = hexColor;
    }

    public static List<SquareType> getValid() {
	List<SquareType> nonValid = List.of(OUTSIDE);
	return Arrays.stream(values())
		.filter(e -> !nonValid.contains(e))
		.collect(Collectors.toList());
    }

    public boolean isSolid() {
	List<SquareType> nonSolid = List.of(EMPTY, OUTSIDE);
	return !nonSolid.contains(this);
    }

    public String asSymbol() {
	return String.valueOf(symbol);
    }

    public Color getColor() {
	return Color.decode("#" + hexColor);
    }

    public static void main(String[] args) {
	Random rnd = new Random();
	for (int i = 0; i < 25; i++) {
	    int rndIndex = 1 + rnd.nextInt(SquareType.values().length - 1);
	    SquareType rndSquareType = SquareType.values()[rndIndex];
	    System.out.println(rndSquareType);
	}
    }
}
