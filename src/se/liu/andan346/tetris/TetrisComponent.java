package se.liu.andan346.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class TetrisComponent extends JComponent
{
    private Board board;
    private final static int SQUARE_SIZE = 30;

    public TetrisComponent(Board board) {
	this.board = board;
    }

    private Point getStartPos() {
	Dimension size = getSize();
	int newWidth = (size.width - board.getWidth() * SQUARE_SIZE) / 2;
	int newHeight = (size.height - board.getHeight() * SQUARE_SIZE) / 2;
	return new Point(newWidth, newHeight);
    }

    @Override
    public Dimension getPreferredSize() {
	return new Dimension(board.getWidth() * SQUARE_SIZE, board.getHeight() * SQUARE_SIZE);
    }

    @Override
    protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;

	for (int y = 0; y < board.getHeight(); y++) {
	    for (int x = 0; x < board.getWidth(); x++) {
		SquareType type = board.getAt(x, y);
		if (type.equals(SquareType.EMPTY)) continue;
		g2d.setColor(type.getColor());
		drawSquare(x, y, g2d);
	    }
	}
    }

    private void drawSquare(int x, int y, Graphics2D g2d) {
	/* Offset coordinates */
	x = getStartPos().x + x * SQUARE_SIZE;
	y = getStartPos().y + y * SQUARE_SIZE;

	/* Draw base shape */
	g2d.fillRect(x, y, SQUARE_SIZE, SQUARE_SIZE);

	/* Draw shading */
	// Shape
	int width = (int) (0.15 * SQUARE_SIZE);
	Polygon shade = new Polygon(
		new int[]{x, x + SQUARE_SIZE, (x + SQUARE_SIZE) - width, x + width},
		new int[]{y, y, y + width, y + width},
		4
	);

	// Color and rotation
	Color color = g2d.getColor();
	AffineTransform transform = new AffineTransform();
	for (int i = 0; i < 4; i++) {
	    // Rotation
	    transform.rotate(
		    Math.toRadians(90 * i),
		    x + SQUARE_SIZE / 2.0,
		    y + SQUARE_SIZE / 2.0
	    );
	    Shape shadeRotated = transform.createTransformedShape(shade);

	    // Color
	    switch (i) {
		case 0 -> g2d.setColor(color.brighter());
		case 1,2 -> g2d.setColor(color.darker());
		case 3 -> g2d.setColor(color.darker().darker());
	    }

	    // Draw
	    g2d.fill(shadeRotated);
	}
    }
}
