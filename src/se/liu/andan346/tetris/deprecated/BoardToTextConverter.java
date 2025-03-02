package se.liu.andan346.tetris.deprecated;

import se.liu.andan346.tetris.Board;
import se.liu.andan346.tetris.poly.FallingPoly;
import se.liu.andan346.tetris.util.SquareType;

@Deprecated
public class BoardToTextConverter
{
    public String convertToText(Board board) {
	// Get the current falling poly (if any) and its position
	FallingPoly falling = board.getFalling();

	StringBuilder sb = new StringBuilder();
	/* Iterate over the boards squares */
	for (int y = 0; y < board.getHeight(); y++) {
	    for (int x = 0; x < board.getWidth(); x++) {
		// Retrieve square att current position on the board
		SquareType boardSquare = board.getSquareAt(x, y);

		// Determine the square type from the falling piece (if it overlaps this position)
		SquareType fallingSquare = SquareType.EMPTY;
		if (falling != null)
		    fallingSquare = falling.getSquareAt(x - falling.getPos().x, y - falling.getPos().y);

		// Use the falling piece's square unless it's empty; otherwise, use the board's square
		SquareType resultingSquare = (fallingSquare == SquareType.EMPTY) ? boardSquare : fallingSquare;

		// Append the symbol representation of the square to the StringBuilder
		sb.append(resultingSquare.asSymbol());
		//sb.append(squareFinal.formatted());
	    }
	    // Add a new line after each row
	    sb.append(y < board.getHeight() - 1 ? "\n" : "");
	}
	return sb.toString();
    }
}
