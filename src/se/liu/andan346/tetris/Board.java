package se.liu.andan346.tetris;

import se.liu.andan346.tetris.poly.Poly;
import se.liu.andan346.tetris.poly.TetrominoMaker;
import se.liu.andan346.tetris.util.BoardListener;
import se.liu.andan346.tetris.util.Direction;
import se.liu.andan346.tetris.util.SquareType;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.List;

public class Board
{
    private final static Random RND = new Random();

    private int width;
    private int height;
    private SquareType[][] squares;

    private Poly fallingPoly = null;
    private Point fallingPos = new Point();

    private List<BoardListener> listeners = new ArrayList<>();

    private TetrominoMaker tetrominoFactory = new TetrominoMaker();

    private boolean isGameOver = false;

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

    public boolean isWithinBoard(int x, int y) {
	return (0 <= x && x < getWidth()) && (0 <= y && y < getHeight());
    }

    public SquareType getAt(int x, int y) {
	if (isWithinBoard(x, y))
	    return squares[y][x];
	else
	    return SquareType.OUTSIDE;
    }

    private boolean setAt(int x, int y, SquareType type) {
	if (isWithinBoard(x, y)) {
	    squares[y][x] = type;
	    return true;
	}
	else return false;
    }

    public Poly getFallingPoly() {
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
	// Game over check
	if (isGameOver) {
	    System.out.println("Game Over!");
	    return;
	}

	// Set new random falling poly if there currently is none
	if (getFallingPoly() == null) {
	    spawnRandomFalling();
	// Else, move falling one position down
	} else {
	    moveFalling(Direction.DOWN);
	}
    }

    private List<Point> getFallingOccupiedSquares() {
	List<Point> squarePositions = new ArrayList<>();
	for (int i = 0; i < fallingPoly.getHeight(); i++) {
	    for (int j = 0; j < fallingPoly.getWidth(); j++) {
		if (fallingPoly.getSquareAt(j, i) != SquareType.EMPTY)
		    squarePositions.add(new Point(fallingPos.x + j, fallingPos.y + i));
	    }
	}
	return squarePositions;
    }

    public void moveFalling(Direction dir) {
	switch (dir) {
	    case LEFT -> moveFallingPoly(-1, 0);
	    case RIGHT -> moveFallingPoly(1, 0);
	    case DOWN -> moveFallingPoly(0, 1);
	}
    }

    private void moveFallingPoly(final int dx, final int dy) {
	if (fallingPoly == null) return;

	Point oldPos = new Point(fallingPos);
	Point newPos = new Point(oldPos.x + dx, oldPos.y + dy);
	List<Point> squarePositions = getFallingOccupiedSquares();

	fallingPos = newPos;
	if (hasCollision(squarePositions)) {
	    fallingPos = oldPos;
	    if (fallingPos.y == 0) gameOver();
	    if (dy > 0) fallingPoly = null;
	}
	else moveFallingSquares(squarePositions, dx, dy);
    }

    private boolean hasCollision(List<Point> oldSquarePositions) {
	for (Point squarePos : getFallingOccupiedSquares()) {
	    if (oldSquarePositions.contains(squarePos))
		continue;
	    else if (this.getAt(squarePos.x, squarePos.y) != SquareType.EMPTY)
		return true;
	}
	return false;
    }

    private void moveFallingSquares(List<Point> oldSquarePositions, int dx, int dy) {
	for (Point squarePos : oldSquarePositions) {
	    this.setAt(squarePos.x, squarePos.y, SquareType.EMPTY);
	}
	for (Point squarePos : oldSquarePositions) {
	    Point newPos = new Point(squarePos);
	    newPos.translate(dx, dy);
	    this.setAt(newPos.x, newPos.y, fallingPoly.getType());
	}
	notifyListeners();
    }

    private void spawnRandomFalling() {
	Poly poly = tetrominoFactory.getRandom();
	setFalling(poly, this.getWidth()/2 - poly.getWidth()/2, 0);
    }

    public void setFalling(Poly poly, int x, int y) {
	// Initiate new falling poly and pos
	fallingPoly = poly;
	fallingPos = new Point(x, y);

	// Clamp poly to board and fill its squares
	clampFalling();
	fillFallingSquares();
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
	for (int i = 0; i < getHeight(); i++) {
	    for (int j = 0; j < getWidth(); j++) {
		// Set current square to random SquareType
		int rnd = 1 + RND.nextInt(SquareType.values().length - 1);
		squares[i][j] = SquareType.values()[rnd];
	    }
	}
	notifyListeners();
    }

    private void gameOver() {
	isGameOver = true;
    }

    public void rotateFalling() {
	System.out.println("rotate");
    }
}
