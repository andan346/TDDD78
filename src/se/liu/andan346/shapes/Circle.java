package se.liu.andan346.shapes;

import java.awt.*;

public class Circle implements Shape
{
    private int x;
    private int y;
    private int radius;
    private Color color;

    public Circle(final int x, final int y, final int radius, final Color color) {
	if (radius < 0) throw new IllegalArgumentException("Negativ radie!");

	this.x = x;
	this.y = y;
	this.radius = radius;
	this.color = color;
    }

    public Circle(final int x, final int y, final int radius) {
	this.x = x;
	this.y = y;
	this.radius = radius;
	this.color = DEFAULT_COLOR;
    }

    @Override public int getX() {
	return x;
    }

    @Override public int getY() {
	return y;
    }

    public int getRadius() {
	return radius;
    }

    @Override public Color getColor() {
	return color;
    }

    @Override public void draw() {
	System.out.println("Ritar: " + this);
    }

    @Override public String toString() {
	return "Circle{" + "x=" + x + ", y=" + y + ", radius=" + radius + ", color=" + color + '}';
    }
}
