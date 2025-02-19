package se.liu.andan346.tetris;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Board
{
    private final static Random RND = new Random();

    private int width;
    private int height;
    private SquareType[][] squares;

    private Poly fallingPoly = null;
    private Point fallingPos = new Point();

    private ArrayList<BoardListener> listeners = new ArrayList<>();

    private TetrominoMaker tetrominoFactory = new TetrominoMaker();

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
	if ((0 <= x && x < getWidth()) && (0 <= y && y < getHeight()))
	    return squares[y][x];
	else
	    return SquareType.OUTSIDE;
    }

    public Poly getFalling() {
	return fallingPoly;
    }

    public Point getFallingPos() {
	return fallingPos;
    }

    public void setFallingPos(Point p) {
	fallingPos = p;
    }

    public void addBoardListener(BoardListener bl) {
	this.listeners.add(bl);
    }

    private void notifyListeners() {
	for (BoardListener listener : listeners) {
	    listener.boardChanged();
	}
    }

    public void tick() {
	System.out.println("tick");

	// Set new random falling poly if there currently is none
	if (getFalling() == null) {
	    Poly poly = tetrominoFactory.getRandom();
	    setFalling(poly, this.getWidth()/2 - poly.getWidth()/2, 0);
	// Else, move falling one position down
	} else {
	    moveFalling(0, 1);
	}
    }

    public void moveFalling(Direction dir) {
	switch (dir) {
	    case LEFT -> moveFalling(-1, 0);
	    case RIGHT -> moveFalling(1, 0);
	}
    }

    private void moveFalling(final int x, final int y) {
	Point newPos = new Point(fallingPos.x + x, fallingPos.y + y);
	//if (!canMoveTo(newPos.x, newPos.y)) return;

	clearFallingSquares();
	setFallingPos(newPos);
	clampFalling();
	fillFallingSquares();
    }

    private boolean canMoveTo(final int x, final int y) {
	Poly poly = fallingPoly;
	Point[] corners = new Point[]{
		new Point(x, y),
		new Point(x + fallingPoly.getWidth(), y),
		new Point(x + fallingPoly.getWidth(), y + fallingPoly.getHeight()),
		new Point(x, y + fallingPoly.getHeight())
	};

	for (Point corner : corners) {
	    System.out.println(getAt(corner.x, corner.y));

	    // Implement "if corner on fallingpoly"
	    if (false)
		continue;

	    if (!getAt(corner.x, corner.y).equals(SquareType.EMPTY))
		return false;
	}
	System.out.println("---");

	return true;
    }

    public void setFalling(Poly poly, int x, int y) {
	// Initiate new falling poly and pos
	fallingPoly = poly;
	fallingPos = new Point(x, y);

	// Clamp poly to board and fill its squares
	clampFalling();
	fillFallingSquares();
    }

    private void clearFallingSquares() {
	Poly poly = fallingPoly;
	int x = fallingPos.x;
	int y = fallingPos.y;

	/* Iterate over the Poly's squares */
	for (int i = 0; i < poly.getHeight(); i++) {
	    for (int j = 0; j < poly.getWidth(); j++) {
		// Fill the squares at the specified coordinates
		squares[i+y][j+x] = SquareType.EMPTY;
	    }
	}
	// Notify update
	notifyListeners();
    }

    private void fillFallingSquares() {
	Poly poly = fallingPoly;
	int x = fallingPos.x;
	int y = fallingPos.y;

	/* Iterate over the Poly's squares */
	for (int i = 0; i < poly.getHeight(); i++) {
	    for (int j = 0; j < poly.getWidth(); j++) {
		// Fill the squares at the specified coordinates
		squares[i+y][j+x] = poly.getSquareAt(j, i);
	    }
	}
	// Notify update
	notifyListeners();
    }

    private void clampFalling() {
	this.fallingPos = new Point(
	    Math.clamp(fallingPos.x, 0, this.getWidth() - fallingPoly.getWidth()),
	    Math.clamp(fallingPos.y, 0, this.getHeight() - fallingPoly.getHeight())
	);
    }

    public void generateRandom() {
	/* Iterate over the board */
	for (int i = 0; i < getSquares().length; i++) {
	    for (int j = 0; j < getSquares()[i].length; j++) {
		// Set current square to random SquareType
		int rnd = 1 + RND.nextInt(SquareType.values().length - 1);
		squares[i][j] = SquareType.values()[rnd];
	    }
	}
	notifyListeners();
    }
}
