package se.liu.andan346.tetris;

import java.util.Random;

public enum SquareType
{
    EMPTY,
    I,
    O,
    T,
    S,
    Z,
    J,
    L;

    public static void main() {
	Random rnd = new Random();
	for (int i = 0; i < 25; i++) {
	    int rndIndex = rnd.nextInt(SquareType.values().length);
	    SquareType rndSquareType = SquareType.values()[rndIndex];
	    System.out.println(rndSquareType);
	}

    }

}
