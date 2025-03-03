package se.liu.andan346.tetris;

import se.liu.andan346.tetris.poly.FallingPoly;
import se.liu.andan346.tetris.util.SquareType;

public class FallthroughFallHandler implements FallHandler
{
    @Override public boolean hasCollision(Board board) {
	FallingPoly falling = board.getFalling();

	return falling.getSolidSquares().stream()
		.filter(p -> p.y > 0)
		.anyMatch(p -> board.getSquareAt(p) == SquareType.OUTSIDE);
    }
}
