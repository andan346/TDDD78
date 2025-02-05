package se.liu.andan346.shapes;

import java.awt.*;

public class Text extends AbstractShape
{
    private int size;
    private String text;

    public Text(final int x, final int y, final int size, final Color color, final String text) {
	super(x, y, color);
	if (size < 0) throw new IllegalArgumentException("Negativ storlek!");
	this.size = size;
	this.text = text;
    }

    public int getSize() {
	return size;
    }

    public String getText() {
	return text;
    }

    @Override public void draw(final Graphics g) {
	//System.out.println("Ritar: " + this);
	super.draw(g);
	g.setFont(new Font("serif", Font.PLAIN, getSize()));
	g.drawString(getText(), getX(), getY());
    }

    @Override public String toString() {
	return "Text{" + "x=" + getX() + ", y=" + getY() + ", color=" + getColor() + ", size=" + getSize() + ", text='" + getText() + '\'' + '}';
    }
}
