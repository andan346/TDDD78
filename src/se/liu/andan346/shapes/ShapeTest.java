package se.liu.andan346.shapes;

import java.util.ArrayList;
import java.util.List;

public class ShapeTest
{
    public static void main(String[] args) {
	final ArrayList<Shape> shapes = new ArrayList<>();

	shapes.addAll(List.of(new Shape[] {
		new Circle(1, 1, 3),
		new Circle(2, 3, 2),
		new Circle(4, 5, 1),
		new Circle(5, 2, 3),
		new Rectangle(7, 6, 2, 3),
		new Rectangle(2, 0, 4, 1)
	}));

	for (Shape shape : shapes) {
	    shape.draw();
	    //System.out.printf("(%01d,%01d)%n", shape.getX(), shape.getY());
	}
    }
}
