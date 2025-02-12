package se.liu.andan346.tetris;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class Board
{
    private final static Random RND = new Random();

    private int width;
    private int height;
    private SquareType[][] squares;

    private Poly falling = null;
    private Point fallingPos = new Point();

    public Board(final int width, final int height) {
	this.width = width;
	this.height = height;
	this.squares = new SquareType[height][width];
	for (SquareType[] row : squares) Arrays.fill(row, SquareType.EMPTY);
    }

    public int getWidth() {
	return width;
    }

    public int getHeight() {
	return height;
    }

    public SquareType[][] getSquares() {
	return squares;
    }

    public SquareType getAt(int x, int y) {
	return squares[y][x];
    }

    public Poly getFalling() {
	return falling;
    }

    public Point getFallingPos() {
	return fallingPos;
    }

    public void setFalling(Poly poly, int x, int y) {
	/* Iterate over the Poly's squares */
	for (int i = 0; i < poly.getHeight(); i++) {
	    for (int j = 0; j < poly.getWidth(); j++) {
		// Fill the squares at the specified coordinates (centered on the Poly's own x axis)
		try { squares[i+y][j+x-poly.getWidth()/2] = poly.getSquareAt(j, i); }
		// We don't need to worry about squares outside the board so we just ignore any index errors
		catch (ArrayIndexOutOfBoundsException e) {
		    System.out.println("Issue that will be fixed in a future commit. For lab 1-3 this is not an issue.");
		}
	    }
	}
    }

    public void generateRandom() {
	/* Iterate over the board */
	for (int i = 0; i < getSquares().length; i++) {
	    for (int j = 0; j < getSquares()[i].length; j++) {
		// Set current square to random SquareType
		int rnd = RND.nextInt(SquareType.values().length);
		squares[i][j] = SquareType.values()[rnd];
	    }
	}
    }
}
