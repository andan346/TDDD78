package se.liu.andan346.tetris.falling;

import se.liu.andan346.tetris.Board;
import se.liu.andan346.tetris.poly.FallingPoly;
import se.liu.andan346.tetris.util.SquareType;

import java.awt.*;
import java.util.List;

public class DefaultFallHandler implements FallHandler
{
    private Board board;

    public DefaultFallHandler(Board board) {
	this.board = board;
    }

    @Override public boolean hasCollision() {
	FallingPoly falling = board.getFalling();
	List<Point> prevFallingSquares = falling.getPrevState().getSolidSquares();

	return falling.getSolidSquares().stream()
		.filter(p -> !prevFallingSquares.contains(p) && p.y >= 0)
		.anyMatch(p -> board.getSquareAt(p) != SquareType.EMPTY);
    }

    public void handleCollision(int dy) {
	FallingPoly falling = board.getFalling();

	// revert to previous state,
	board.setFalling(falling.getPrevState());

	// freeze falling poly in place.
	if (dy > 0) {
	    board.freezeFalling();
	    board.tick(); // advance one tick to make gameplay smoother
	}
    }

    @Override public void moveFalling(int dx, int dy) {
	FallingPoly falling = board.getFalling();
	if (falling == null) return;

	// Move falling to new position
	Point newPos = new Point(falling.getPos().x + dx, falling.getPos().y + dy);
	falling.setPos(newPos);

	// In case of collision...
	if (this.hasCollision()) {
	    handleCollision(dy);
	}

	// In case of no collision; move the squares on the board.
	else board.updateFallingSquares();
    }

    @Override public FallHandler getDefault() {
	return this;
    }
}
