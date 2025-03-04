package se.liu.andan346.tetris.gui;

import se.liu.andan346.tetris.Board;
import se.liu.andan346.tetris.input.InputHandler;
import se.liu.andan346.tetris.util.BoardListener;
import se.liu.andan346.tetris.util.SquareType;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Objects;

public class TetrisComponent extends JComponent implements BoardListener
{
    private Board board;
    public final static int SQUARE_SIZE = 30;
    public InputHandler inputHandler;

    private String prevFallHandler;
    private double stringOpacity = 1;
    private int fontSize = 24;
    private boolean shouldDrawString = false;

    public TetrisComponent(Board board) {
	this.board = board;
	this.prevFallHandler = board.getFallHandler().toString();

	this.inputHandler = new InputHandler(this);
    }

    public Board getBoard() {
	return board;
    }

    private Point getStartPos() {
	// Get window size
	Dimension size = getSize();
	// Calculate coordinates for centering the board within the component
	int x = (size.width - board.getWidth() * SQUARE_SIZE) / 2;
	int y = (size.height - board.getHeight() * SQUARE_SIZE) / 2;

	return new Point(x, y);
    }

    @Override
    public Dimension getPreferredSize() {
	return new Dimension(
		board.getWidth() * SQUARE_SIZE,
		board.getHeight() * SQUARE_SIZE
	);
    }

    @Override
    protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;

	// Iterate over the board
	for (int y = 0; y < board.getHeight(); y++) {
	    for (int x = 0; x < board.getWidth(); x++) {
		// Get square type
		SquareType type = board.getSquareAt(x, y);

		// Set pixel coordinates for drawing
		Point startPos = getStartPos();
		int drawX = startPos.x + x * SQUARE_SIZE;
		int drawY = startPos.y + y * SQUARE_SIZE;

		// If square empty, skip the rest of the iteration
		if (type.equals(SquareType.EMPTY)) continue;

		// Draw current square
		g2d.setColor(type.getColor());
		drawSquare(drawX, drawY, g2d);
	    }
	}

	// Draw border
	Point borderPos = getStartPos();
	Dimension borderDim = getPreferredSize();
	g2d.setColor(Color.BLACK);
	g2d.drawRect(borderPos.x - 1, borderPos.y - 1, borderDim.width + 1, borderDim.height + 1);

	// Draw powerup string
	if (shouldDrawString) {
	    Composite ogComposite = g2d.getComposite();

	    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) stringOpacity));

	    g2d.setFont(new Font("Arial", Font.BOLD, fontSize));
	    FontMetrics fm = g2d.getFontMetrics();
	    Point pos = new Point((getWidth() - fm.stringWidth(prevFallHandler)) / 2,
				  (getHeight() - fm.getHeight()) / 2 + fm.getAscent());
	    g2d.drawString(prevFallHandler, pos.x, pos.y);

	    g2d.setComposite(ogComposite);
	}
    }

    private void drawSquare(int x, int y, Graphics2D g2d) {
	// Draw base shape
	g2d.fillRect(x, y, SQUARE_SIZE, SQUARE_SIZE);

	// Draw shading
	drawShading(x, y, g2d);
    }

    private void drawShading(int x, int y, Graphics2D g2d) {
	// Define the width of the shading
	int width = (int) (0.15 * SQUARE_SIZE);

	// Create shape
	Shape shape = new Polygon(
		new int[]{x, x + SQUARE_SIZE, (x + SQUARE_SIZE) - width, x + width},
		new int[]{y, y, y + width, y + width},
		4
	);

	// Definitions needed hereafter
	Color color = g2d.getColor();
	AffineTransform transform = new AffineTransform();

	// For each side of the square...
	for (int i = 0; i < 4; i++) {
	    // Change the color of the shading
	    switch (i) {
		case 0 -> g2d.setColor(color.brighter());
		case 1,2 -> g2d.setColor(color.darker());
		case 3 -> g2d.setColor(color.darker().darker());
	    }

	    // Rotate the shape
	    transform.rotate(
		    Math.toRadians(90 * i),
		    x + SQUARE_SIZE / 2.0,
		    y + SQUARE_SIZE / 2.0
	    );
	    Shape rotated = transform.createTransformedShape(shape);

	    // Draw
	    g2d.fill(rotated);
	}
    }

    private void announcePowerup() {
	new Thread(() -> {
	    stringOpacity = 1;
	    fontSize = 1;
	    shouldDrawString = true;

	    long startTime = System.currentTimeMillis();
	    long duration = 1000;
	    while (true) {
		long elapsed = System.currentTimeMillis() - startTime;
		if (elapsed > duration) break;

		double val = (double) elapsed / duration;
		stringOpacity = 1 - val;
		fontSize = (int) (val * 100);

		repaint();

		try { Thread.sleep(1000 / 30); } // 30 fps
		catch (InterruptedException e) { e.printStackTrace();}
	    }

	    shouldDrawString = false;
	    stringOpacity = 0;
	}).start();
    }

    @Override public void boardChanged(Board board) {
	repaint();

	String currFallHandler = board.getFallHandler().toString();
	if (!Objects.equals(currFallHandler, prevFallHandler)) {
	    System.out.println("New powerup: " + currFallHandler);
	    prevFallHandler = currFallHandler;
	    if (!Objects.equals(currFallHandler, "Default"))
	    	announcePowerup();
	}
    }

    @Override public void onGameOver(final Board board) {
	repaint();
    }
}
