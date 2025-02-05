package se.liu.andan346.shapes;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CircleTest
{
    public static void main(String[] args) {

	final ArrayList<Circle> circles = new ArrayList<>(List.of(new Circle[]{
		new Circle(1, 1, 3, Color.BLACK),
		new Circle(2, 3, 2, Color.GREEN),
		new Circle(4, 5, 1, Color.MAGENTA),
		new Circle(5, 2, 3, Color.DARK_GRAY)
	}));

	for (Circle circle : circles)
	    System.out.printf("(%01d,%01d)%n", circle.getX(), circle.getY());
    }
}
