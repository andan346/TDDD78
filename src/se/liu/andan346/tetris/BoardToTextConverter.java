package se.liu.andan346.tetris;

import java.awt.*;
import java.util.Arrays;
import java.util.stream.Collectors;

public class BoardToTextConverter
{

    public String convertToText(Board board) {
	Poly fallingPoly = board.getFalling();
	Point fallingPos = board.getFallingPos();

	StringBuilder sb = new StringBuilder();
	for (int y = 0; y < board.getHeight(); y++) {
	    for (int x = 0; x < board.getWidth(); x++) {
		SquareType squareSet = board.getAt(x, y);
		SquareType squareFalling = SquareType.EMPTY;
		if (fallingPoly != null)
		    squareFalling = fallingPoly.getSquareAt(x - fallingPos.x, y - fallingPos.y);
		SquareType squareFinal = (squareFalling == SquareType.EMPTY) ? squareSet : squareFalling;
		//sb.append(squareFinal.formatted());
		sb.append(squareFinal.asSymbol());
	    }
	    sb.append("\n");
	}
	return sb.toString();
	/*
	return Arrays.stream(board.getSquares())
		.map(row -> Arrays.stream(row)
			.map(SquareType::formatted)
			.collect(Collectors.joining(" ")))
		.collect(Collectors.joining("\n"));
	 */
    }

    private boolean pointWithinPoly(int x, int y, Poly poly, Point polyPos) {
	int x1 = polyPos.x; 		int y1 = polyPos.y;
	int x2 = x1 + poly.getWidth();	int y2 = y1 + poly.getHeight();
	return (x1 <= x && x <= x2) && (y1 <= y && y <= y2);
    }

}
