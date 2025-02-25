package se.liu.andan346.tetris;

import java.awt.*;

public class BoardToTextConverter
{
    public String convertToText(Board board) {
	// Get the current falling poly (if any) and its position
	Poly fallingPoly = board.getFallingPoly();
	Point fallingPos = board.getFallingPos();

	StringBuilder sb = new StringBuilder();
	/* Iterate over the boards squares */
	for (int y = 0; y < board.getHeight(); y++) {
	    for (int x = 0; x < board.getWidth(); x++) {
		// Retrieve square att current position on the board
		SquareType boardSquare = board.getAt(x, y);

		// Determine the square type from the falling piece (if it overlaps this position)
		SquareType fallingSquare = SquareType.EMPTY;
		if (fallingPoly != null)
		    fallingSquare = fallingPoly.getSquareAt(x - fallingPos.x, y - fallingPos.y);

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
