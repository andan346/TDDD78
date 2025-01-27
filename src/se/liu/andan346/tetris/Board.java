package se.liu.andan346.tetris;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class Board
{
    private SquareType[][] squares;
    private int width;
    private int height;
    private final static Random RND = new Random();
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

    @Override public String toString() {
	return Arrays.stream(getSquares())
		.map(row -> Arrays.stream(row)
			.map(SquareType::toString)
			.collect(Collectors.joining(" ")))
		.collect(Collectors.joining("\n"));
    }

    public void setFalling(Poly poly, int x, int y) {
	for (int i = 0; i < poly.getHeight(); i++) {
	    for (int j = 0; j < poly.getWidth(); j++) {
		try { squares[i+y][j+x-poly.getWidth()/2] = poly.getSquareAt(j, i); }
		catch (ArrayIndexOutOfBoundsException _) {}
	    }
	}
    }

    public void generateRandom() {
	for (int i = 0; i < getSquares().length; i++) {
	    for (int j = 0; j < getSquares()[i].length; j++) {
		int rnd = RND.nextInt(SquareType.values().length);
		squares[i][j] = SquareType.values()[rnd];
	    }
	}
    }

    public static void main(String[] args) {
	Board board = new Board(5, 10);
	System.out.println(board);
	board.generateRandom();
	System.out.println(new BoardToTextConverter().convertToText(board));
    }
}
