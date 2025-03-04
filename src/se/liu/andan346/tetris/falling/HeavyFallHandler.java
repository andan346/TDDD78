package se.liu.andan346.tetris.falling;

import se.liu.andan346.tetris.Board;
import se.liu.andan346.tetris.poly.FallingPoly;
import se.liu.andan346.tetris.util.SquareType;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class HeavyFallHandler implements FallHandler
{
    private Board board;
    private DefaultFallHandler defaultHandler;

    public HeavyFallHandler(Board board) {
	this.board = board;
	this.defaultHandler = new DefaultFallHandler(board);
    }

    @Override public boolean hasCollision() {
	return defaultHandler.hasCollision();
    }

    private void onVerticalCollision() {
	FallingPoly falling = board.getFalling();
	board.setFalling(falling.getPrevState());
	board.freezeFalling();
	//board.tick();
    }

    @Override public void moveFalling(int dx, int dy) {
	FallingPoly falling = board.getFalling();
	if (falling == null) return;

	// Move falling to new position
	Point newPos = new Point(falling.getPos().x + dx, falling.getPos().y + dy);
	falling.setPos(newPos);

	// In case of collision...
	if (this.hasCollision()) {

	    // revert to prev state if moving horizontally
	    if (dx != 0) {
		defaultHandler.handleCollision(dy);
		return;
	    }

	    // get squares directly below prev state
	    List<Point> contactPoints = falling.getPrevState().getBoardSquaresBelow();


	    // Get columns below
	    // If any goes to the bottom, its supported
	    for (Point p0 : contactPoints) {
		if (board.isSupported(p0)) {
		    defaultHandler.handleCollision(dy);
		    return;
		}
	    }

	    // Get connected columns below
	    // Move each down
	    for (Point p0 : contactPoints) {
		Map<Point, SquareType> connectedCol = board.getConnectedColumn(p0);
		connectedCol.forEach((p, t) -> board.setSquareAt(p, SquareType.EMPTY));
		connectedCol.forEach((p, t) -> board.setSquareAt(p.x, p.y + 1, t));
	    }

	    // Update visuals
	    board.notifyListeners();
	    board.updateFallingSquares();
	}

	// In case of no collision; move the squares on the board.
	else board.updateFallingSquares();
    }

    @Override public FallHandler getDefault() {
	return defaultHandler;
    }

    @Override public String toString() {
	return "Heavy";
    }
}
