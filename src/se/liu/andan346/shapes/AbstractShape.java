package se.liu.andan346.shapes;

import java.awt.*;
import java.util.Objects;

public abstract class AbstractShape implements Shape
{
    protected int x;
    protected int y;
    protected Color color;

    protected AbstractShape(final int x, final int y, final Color color) {
	this.x = x;
	this.y = y;
	this.color = color;
    }

    @Override public int getX() {
	return x;
    }

    @Override public int getY() {
	return y;
    }

    @Override public Color getColor() {
	return color;
    }

    @Override public void draw(Graphics g) {
	g.setColor(getColor());
    }

    @Override public boolean equals(final Object o) {
	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	final AbstractShape that = (AbstractShape) o;
	return getX() == that.getX() && getY() == that.getY() && Objects.equals(getColor(), that.getColor());
    }

    @Override public int hashCode() {
	return Objects.hash(getX(), getY(), getColor());
    }
}
