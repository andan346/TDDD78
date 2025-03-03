package se.liu.andan346.tetris.poly;

import se.liu.andan346.tetris.Board;
import se.liu.andan346.tetris.util.SquareType;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class FallingPoly extends Poly
{
    private Point pos;
    private FallingPoly prevState = null;
    private Map<Point, SquareType> backdrop = new HashMap<>();

    public FallingPoly(final Poly poly, final Point pos) {
	super(poly.getShape(), poly.getType());
	this.pos = pos;
	//adjustForEmptySquares();
    }

    public Point getPos() {
	return pos;
    }

    public void setPos(final Point pos) {
	saveState();
	this.pos = pos;
    }

    public void translate(int dx, int dy) {
	saveState();
	this.pos.translate(dx, dy);
    }

    public void translate(int dx, int dy, boolean saveState) {
	if (saveState)
	    translate(dx, dy);
	else {
	    Point pos = getPos();
	    pos.translate(dx, dy);
	    this.pos = pos;
	}
    }

    public Point squareToBoard(Point squarePos) {
	return new Point(squarePos.x + pos.x, squarePos.y + pos.y);
    }

    public void rotate() {
	saveState();
	rotate(1);
    }

    public FallingPoly getPrevState() {
	return prevState;
    }

    private void saveState() {
	prevState = new FallingPoly(this, this.pos);
    }

    private void iterSquares(Consumer<Point> consumer) {
	for (int y = 0; y < getHeight(); y++) {
	    for (int x = 0; x < getWidth(); x++) {
		consumer.accept(new Point(x, y));
	    }
	}
    }

    private List<Point> getSquares() {
	List<Point> list = new ArrayList<>();
	iterSquares(list::add);
	return list;
    }

    public void iterSolidSquares(Consumer<Point> consumer) {
	iterSquares(p -> {
	    if (getSquareAt(p.x, p.y).isSolid())
		consumer.accept(p);
	});
    }

    public List<Point> getSolidSquares() {
	List<Point> list = new ArrayList<>();
	iterSolidSquares(p -> list.add(squareToBoard(p)));
	return list;
    }

    public void adjustForEmptySquares() {
	for (Point p : getSquares()) {
	    if (getSquareAt(p.x, p.y).isSolid()) {
		translate(0, -p.y);
		return;
	    }
	}
    }

    public void updateBackdrop(Board board) {
	List<Point> prevSquares = getPrevState().getSolidSquares();
	List<Point> currSquares = getSolidSquares();

	currSquares.stream()
	    .filter(p -> !prevSquares.contains(p))
	    .forEach(p -> backdrop.putIfAbsent(p, board.getSquareAt(p)));
    }

    public SquareType getBackdropSquare(final Point p) {
	return backdrop.getOrDefault(p, SquareType.EMPTY);
    }

    /*public int getSolidHeight() {
	int[] minMax = new int[]{0, 0};
	iterSolidSquares(p -> {
	    minMax[0] = Math.min(minMax[0], p.y);
	    minMax[1] = Math.max(minMax[1], p.y);
	});
	return minMax[1] - minMax[0];
    }*/
}
