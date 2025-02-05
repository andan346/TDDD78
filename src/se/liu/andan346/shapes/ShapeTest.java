package se.liu.andan346.shapes;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ShapeTest
{
    public static void main(String[] args) {

	final ArrayList<Shape> shapes = new ArrayList<>(List.of(new Shape[]{
		new Circle(1, 1, 3, Color.BLACK),
		new Circle(2, 3, 2, Color.GREEN),
		new Circle(4, 5, 1, Color.MAGENTA),
		new Circle(5, 2, 3, Color.DARK_GRAY),
		new Rectangle(7, 6, 2, 3, Color.CYAN),
		new Rectangle(2, 0, 4, 1, Color.YELLOW),
		new Text(4, 8, 8, Color.BLACK, "Test"),
		new Text(8, 2, 3, Color.GREEN, "tjenixen!")
	}));

	for (Shape shape : shapes) return;//shape.draw();
    }
}
