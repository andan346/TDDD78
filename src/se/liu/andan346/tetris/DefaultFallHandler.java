package se.liu.andan346.tetris;

import se.liu.andan346.tetris.poly.FallingPoly;
import se.liu.andan346.tetris.util.SquareType;

import java.awt.*;
import java.util.List;

public class DefaultFallHandler implements FallHandler
{
    @Override public boolean hasCollision(Board board) {
	FallingPoly falling = board.getFalling();
	List<Point> prevFallingSquares = falling.getPrevState().getSolidSquares();

	return falling.getSolidSquares().stream()
		.filter(p -> !prevFallingSquares.contains(p) && p.y >= 0)
		.anyMatch(p -> board.getSquareAt(p) != SquareType.EMPTY);

	/*
	for (Point p : falling.getSolidSquares()) {
	    if (prevFallingSquares.contains(p) || p.y < 0)
		continue;
	    else if (board.getSquareAt(p) != SquareType.EMPTY) {
		return true;
	    }
	}
	return false;

	 */
    }
}
