package se.liu.andan346.tetris.poly;

import se.liu.andan346.tetris.util.SquareType;

import java.awt.*;

@Deprecated
public class FallingPoly extends Poly
{
    private Point position;
    private SquareType type;

    public FallingPoly(final Point position, final SquareType[][] shape, final SquareType type) {
	super(shape, type);
	this.position = position;
	this.type = type;
    }

    public Point getPosition() {
	return position;
    }

    public void setPosition(final Point position) {
	this.position = position;
    }
}
