package se.liu.andan346.tetris;

import se.liu.andan346.tetris.poly.FallingPoly;
import se.liu.andan346.tetris.poly.Poly;
import se.liu.andan346.tetris.poly.TetrominoMaker;
import se.liu.andan346.tetris.util.BoardListener;
import se.liu.andan346.tetris.util.Direction;
import se.liu.andan346.tetris.util.SquareType;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.List;
import java.util.function.Consumer;

public class Board
{
    private final static Random RND = new Random();

    private int width;
    private int height;
    private SquareType[][] squares;
    private FallingPoly falling = null;

    private TetrominoMaker tetrominoFactory = new TetrominoMaker();
    private FallHandler fallHandler = new DefaultFallHandler();
    private List<BoardListener> listeners = new ArrayList<>();

    public boolean isGameOver = false;
    public boolean isGamePaused = false;
    private int score = 0;

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

    public FallingPoly getFalling() {
	return falling;
    }

    public FallHandler getFallHandler() {
	return fallHandler;
    }

    public int getScore() {
	return score;
    }

    public SquareType getSquareAt(int x, int y) {
	if (isWithinBoard(x, y))
	    return squares[y][x];
	else
	    return SquareType.OUTSIDE;
    }

    public SquareType getSquareAt(final Point pos) {
	return getSquareAt(pos.x, pos.y);
    }

    private boolean setSquareAt(int x, int y, SquareType type) {
	if (isWithinBoard(x, y)) {
	    squares[y][x] = type;
	    return true;
	}
	else return false;
    }

    private boolean setSquareAt(final Point pos, SquareType type) {
	return setSquareAt(pos.x, pos.y, type);
    }

    private boolean isWithinBoard(int x, int y) {
	return (0 <= x && x < getWidth()) && (0 <= y && y < getHeight());
    }

    private void iterate(Consumer<Point> consumer) {
	for (int y = 0; y < getHeight(); y++) {
	    for (int x = 0; x < getWidth(); x++) {
		consumer.accept(new Point(x, y));
	    }
	}
    }


    public void addBoardListener(BoardListener bl) {
	this.listeners.add(bl);
    }

    private void notifyListeners() {
	for (BoardListener listener : listeners) {
	    listener.boardChanged(this);
	}
    }


    public void tick() {
	// Set new random falling poly if there currently is none
	if (getFalling() == null) {
	    // Clear rows
	    List<Integer> rowsToClear = getRowsToClear();
	    if (!rowsToClear.isEmpty()) {
		awardScore(rowsToClear.size());
		clearRows(rowsToClear);
	    }

	    spawnRandomFalling();
	// Else, move falling one position down
	} else {
	    //moveFalling(Direction.DOWN);
	}
    }


    private void freezeFalling() {
	falling = null;
    }

    private void awardScore(final int amtOfRows) {
	// amtofRows, score
	Map<Integer, Integer> scoreMap = Map.of(
		1, 100,
		2, 300,
		3, 500,
		4, 800
	);

	score += scoreMap.getOrDefault(Math.min(amtOfRows, scoreMap.size()), 0);
	System.out.println(score);
	notifyListeners();
    }

    private void clearRows(List<Integer> rowsToClear) {
	// Clear rows
	for (int row : rowsToClear) {
	    for (int col = 0; col < getWidth(); col++) {
		setSquareAt(col, row, SquareType.EMPTY);
	    }
	}

	// Collapse rows
	int destRow = getHeight() - 1;
	for (int row = getHeight() - 1; row >= 0 ; row--) {
	    if (!rowsToClear.contains(row)) {
		for (int col = 0; col < getWidth(); col++) {
		    setSquareAt(col, destRow, getSquareAt(col, row));
		}
		destRow--;
	    }
	}
    }

    private List<Integer> getRowsToClear() {
	List<Integer> rowsToClear = new ArrayList<>();

	// For each row, assume it should clear
	for (int y = 0; y < getHeight(); y++) {
	    boolean shouldClear = true;

	    // If not all squares in the row are solid; don't clear
	    for (int x = 0; x < getWidth(); x++) {
		if (!getSquareAt(x, y).isSolid())
		    shouldClear = false;
	    }

	    // Add the row to the list
	    if (shouldClear) rowsToClear.add(y);
	}

	return rowsToClear;
    }

    List<Point> getFallingOccupiedSquares() {
	List<Point> squarePositions = new ArrayList<>();
	for (int i = 0; i < falling.getHeight(); i++) {
	    for (int j = 0; j < falling.getWidth(); j++) {
		if (falling.getSquareAt(j, i) != SquareType.EMPTY)
		    squarePositions.add(new Point(falling.getPos().x + j, falling.getPos().y + i));
	    }
	}
	return squarePositions;
    }

    public void moveFalling(Direction dir) {
	switch (dir) {
	    case LEFT -> moveFalling(-1, 0);
	    case RIGHT -> moveFalling(1, 0);
	    case DOWN -> moveFalling(0, 1);
	}
    }

    private void moveFalling(final int dx, final int dy) {
	if (falling == null) return;

	// Move falling to new position
	Point newPos = new Point(falling.getPos().x + dx, falling.getPos().y + dy);
	falling.setPos(newPos);

	// In case of collision...
	if (fallHandler.hasCollision(this)) {

	    // revert to previous state,
	    falling = falling.getPrevState();

	    // freeze and spawn new poly if moving down.
	    if (dy > 0) {
		freezeFalling();
		spawnRandomFalling();
	    }
	}

	// In case of no collision; move the squares on the board.
	else moveFallingSquares();
    }

    private void moveFallingSquares() {
	FallingPoly currState = falling;
	FallingPoly prevState = falling.getPrevState();

	prevState.iterSolidSquares(p -> {
	    this.setSquareAt(prevState.squareToBoard(p), SquareType.EMPTY);
	});

	currState.iterSolidSquares(p -> {
	    this.setSquareAt(currState.squareToBoard(p), currState.getType());
	});

	notifyListeners();
    }

    private void spawnRandomFalling() {
	Poly poly = tetrominoFactory.getRandom();
	spawnFalling(new FallingPoly(poly, new Point(this.getWidth()/2 - poly.getWidth()/2, 0)));
    }

    private void spawnFalling(FallingPoly falling) {
	this.falling = falling;
	falling.setPos(new Point(falling.getPos().x, falling.getPos().y - falling.getSolidHeight()));
	System.out.println(falling.getType() + ": " + falling.getSolidHeight());
	for (int i = 0; i < falling.getSolidHeight(); i++) {
	    falling.setPos(new Point(falling.getPos().x, falling.getPos().y + 1));
	    System.out.println(falling.getPos());
	    if (getFallHandler().hasCollision(this))
		gameOver();
	    else
		moveFallingSquares();
	}
    }

    @Deprecated public void setFalling(Poly poly, int x, int y) {
	// Initiate new falling poly
	falling = new FallingPoly(poly, new Point(x, y));

	// Clamp poly to board and fill its squares
	clampFalling();
	fillFallingSquares();
    }

    @Deprecated private void fillFallingSquares() {
	Poly poly = falling;
	int x = falling.getPos().x;
	int y = falling.getPos().y;

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

    @Deprecated private void clampFalling() {
	this.falling.setPos(new Point(
		Math.clamp(falling.getPos().x, 0, this.getWidth() - falling.getWidth()),
		Math.clamp(falling.getPos().y, 0, this.getHeight() - falling.getHeight())
	));
    }

    public void generateRandom() {
	this.iterate(pos -> {
	    List<SquareType> squareTypes = SquareType.getValid();
	    setSquareAt(pos, squareTypes.get(RND.nextInt(squareTypes.size())));
	});
	notifyListeners();
    }

    private void gameOver() {
	isGameOver = true;
	notifyListeners();
    }

    public void rotateFalling() {
	if (falling == null) return;

	falling.rotate();

	if (fallHandler.hasCollision(this))
	    falling = falling.getPrevState();
	else
	    moveFallingSquares();
    }

    public void togglePause() {
	isGamePaused = !isGamePaused;
	notifyListeners();
    }
}
