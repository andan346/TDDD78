package se.liu.andan346.tetris.poly;

import se.liu.andan346.tetris.util.SquareType;

import java.util.Random;

public class TetrominoMaker
{
    private final static Random RND = new Random();

    public int getNumberOfTypes() {
	return SquareType.values().length;
    }

    public Poly getPoly(int n) {
	// Check for valid index
	if (0 > n || n > getNumberOfTypes())
	    throw new IllegalArgumentException("Invalid index: " + n);

	SquareType type = SquareType.values()[n];
	// Check for valid type
	if (type == SquareType.EMPTY || type == SquareType.OUTSIDE)
	    throw new IllegalArgumentException("Invalid type: " + type);

	// Return valid type
	return getPoly(type);
    }

    public Poly getPoly(SquareType type) {
	// Initialize empty pattern
	String[] pattern = {" "};

	// Sets the pattern to one of the predefined types if existing
	switch (type) {
	    case SquareType.I -> pattern = new String[]{
		    "#",
		    "#",
		    "#",
		    "#",
	    };
	    case SquareType.O -> pattern = new String[]{
		    "##",
		    "##",
	    };
	    case SquareType.T -> pattern = new String[]{
		    "###",
		    " # ",
	    };
	    case SquareType.S -> pattern = new String[]{
		    " ##",
		    " # ",
		    "## ",
	    };
	    case SquareType.Z -> pattern = new String[]{
		    "## ",
		    " # ",
		    " ##",
	    };
	    case SquareType.J -> pattern = new String[]{
		    " #",
		    " #",
		    "##",
	    };
	    case SquareType.L -> pattern = new String[]{
		    "# ",
		    "# ",
		    "##",
	    };
	}
	// Return new Poly from pattern
	// (Defaults to a 1x1 Poly with its only square being SquareType.EMPTY)
	return Poly.fromPattern(pattern, type);
    }

    public Poly getRandom() {
	int lowerIndexBound = 2;
	int higherIndexBound = getNumberOfTypes();
	return getPoly(lowerIndexBound + RND.nextInt(higherIndexBound - lowerIndexBound));
    }
}
