package se.liu.andan346.shapes;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DiagramComponent extends JComponent
{
    private List<Shape> shapes;

    public DiagramComponent(final List<Shape> shapes) {
	this.shapes = shapes;
    }

    public void addShape(Shape s) {
	shapes.add(s);
    }

    @Override protected void paintComponent(final Graphics g) {
	super.paintComponent(g);
	for (Shape shape : shapes) {
	    shape.draw(g);
	}
    }
}
