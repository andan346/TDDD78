package se.liu.andan346.tetris.falling;

import se.liu.andan346.tetris.Board;
import se.liu.andan346.tetris.poly.FallingPoly;
import se.liu.andan346.tetris.util.SquareType;

public class FallthroughFallHandler implements FallHandler
{
    private Board board;
    private DefaultFallHandler defaultHandler;

    public FallthroughFallHandler(Board board) {
	this.board = board;
	this.defaultHandler = new DefaultFallHandler(board);
    }

    @Override public boolean hasCollision() {
	FallingPoly falling = board.getFalling();

	return falling.getSolidSquares().stream()
		.filter(p -> p.y > 0)
		.anyMatch(p -> board.getSquareAt(p) == SquareType.OUTSIDE);
    }

    @Override public void moveFalling(int dx, int dy) {
	defaultHandler.moveFalling(dx, dy);
    }

    @Override public FallHandler getDefault() {
	return defaultHandler;
    }
}
