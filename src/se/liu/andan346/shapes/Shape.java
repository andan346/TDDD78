package se.liu.andan346.shapes;

import java.awt.*;

public interface Shape
{
    static final Color DEFAULT_COLOR = Color.WHITE;

    int getX();

    int getY();

    Color getColor();

    void draw();
}
