package se.liu.andan346.tetris;

import java.awt.*;

public class FallingPoly extends Poly
{
    private Point position;

    public FallingPoly(final SquareType[][] shape, final Point position) {
	super(shape);
	this.position = position;
    }

    public Point getPosition() {
	return position;
    }

    public void setPosition(final Point position) {
	this.position = position;
    }
}
