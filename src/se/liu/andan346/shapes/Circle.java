package se.liu.andan346.shapes;

import java.awt.*;

public class Circle extends AbstractShape
{
    private int radius;

    public Circle(final int x, final int y, final int radius, final Color color) {
	super(x, y, color);
	if (radius < 0) throw new IllegalArgumentException("Negativ radie!");
	this.radius = radius;
    }

    public int getRadius() {
	return radius;
    }

    @Override public void draw(final Graphics g) {
	//System.out.println("Ritar: " + this);
	super.draw(g);
	g.drawOval(getX(), getY(), 2*getRadius(), 2*getRadius());
    }

    @Override public String toString() {
	return "Circle{" + "x=" + getX() + ", y=" + getY() + ", radius=" + getRadius() + ", color=" + getColor() + '}';
    }
}
