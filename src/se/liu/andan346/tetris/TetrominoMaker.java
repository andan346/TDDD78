package se.liu.andan346.tetris;

public class TetrominoMaker
{
    public int getNumberOfTypes() {
	return SquareType.values().length;
    }

    public Poly getPoly(int n) {
	if (!(0 <= n && n <= getNumberOfTypes()))
	    throw new IllegalArgumentException("Invalid index: " + n);

	SquareType type = SquareType.values()[n];
	String[] pattern = {" "};

	switch (type) {
	    case SquareType.I -> pattern = new String[]{
		    " # ",
		    " # ",
		    " # ",
		    " # ",
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
		    " # ",
		    " # ",
		    "## ",
	    };
	    case SquareType.L -> pattern = new String[]{
		    " # ",
		    " # ",
		    " ##",
	    };
	}
	return Poly.fromPattern(pattern, type);
    }
}
