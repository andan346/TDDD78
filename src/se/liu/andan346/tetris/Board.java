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

    private Poly falling = null;
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
	return squares[y][x];
    }

    public Poly getFalling() {
	return falling;
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
	// Set new random falling poly if there currently is none
	if (getFalling() == null) {
	    Poly poly = tetrominoFactory.getRandom();
	    setFalling(poly, this.getWidth()/2 - poly.getWidth()/2, 0);
	// Else, move falling one position down
	} else {
	    moveFalling(0, 1);
	}
    }

    private void moveFalling(final int x, final int y) {
	setFallingPos(new Point(fallingPos.x + x, fallingPos.y + y));
	notifyListeners();
    }

    public void setFalling(Poly poly, int x, int y) {
	// Clamp coordinates to keep them within the board
	x = Math.clamp(x, 0, this.getWidth() - poly.getWidth());
	y = Math.clamp(y, 0, this.getHeight() - poly.getHeight());
	/* Iterate over the Poly's squares */
	for (int i = 0; i < poly.getHeight(); i++) {
	    for (int j = 0; j < poly.getWidth(); j++) {
		// Fill the squares at the specified coordinates (centered on the Poly's own x axis)
		squares[i+y][j+x] = poly.getSquareAt(j, i);
	    }
	}
	falling = poly;
	notifyListeners();
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
	notifyListeners();
    }
}
