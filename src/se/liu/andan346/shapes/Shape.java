package se.liu.andan346.shapes;

import java.awt.*;

public interface Shape
{
    int getX();

    int getY();

    Color getColor();

    void draw(final Graphics g);
}
