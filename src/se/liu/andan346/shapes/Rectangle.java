package se.liu.andan346.shapes;

import java.awt.*;

public class Rectangle extends AbstractShape
{
    private int width;
    private int height;

    public Rectangle(final int x, final int y, final int width, final int height, final Color color) {
	super(x, y, color);
	if (width < 0 || height < 0) throw new IllegalArgumentException("Negativa dimensioner!");
	this.width = width;
	this.height = height;
    }

    public int getWidth() {
	return width;
    }

    public int getHeight() {
	return height;
    }

    @Override public void draw(final Graphics g) {
	//System.out.println("Ritar: " + this);
	super.draw(g);
	g.drawRect(getX(), getY(), getWidth(), getHeight());
    }

    @Override public String toString() {
	return "Rectangle{" + "x=" + getX() + ", y=" + getY() + ", width=" + getWidth() + ", height=" + getHeight() + ", color=" + getColor() + '}';
    }
}
