package se.liu.andan346.tetris;

import java.awt.*;
import java.util.Arrays;
import java.util.stream.Collectors;

public class BoardToTextConverter
{
    private Poly fallingPoly;
    private Point fallingPos;

    public String convertToText(Board board) {
	this.fallingPoly = board.getFalling();
	this.fallingPos = board.getFallingPos();

	StringBuilder sb = new StringBuilder();
	for (int y = 0; y < board.getHeight(); y++) {
	    for (int x = 0; x < board.getWidth(); x++) {
		SquareType squareSet = board.getAt(x, y);
		SquareType squareFalling = getFallingSquareAt(x, y);

	    }
	}

	return Arrays.stream(board.getSquares())
		.map(row -> Arrays.stream(row)
			.map(SquareType::formatted)
			.collect(Collectors.joining(" ")))
		.collect(Collectors.joining("\n"));
    }

    private SquareType getFallingSquareAt(int x, int y) {
	fallingPoly.getSquareAt()
    }

    private boolean pointWithinPoly(int x, int y, Poly poly, Point polyPos) {
	int x1 = polyPos.x; 		int y1 = polyPos.y;
	int x2 = x1 + poly.getWidth();	int y2 = y1 + poly.getHeight();
	return (x1 <= x && x <= x2) && (y1 <= y && y <= y2);
    }

}
