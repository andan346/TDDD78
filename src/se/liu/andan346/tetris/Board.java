package se.liu.andan346.tetris;

import se.liu.andan346.tetris.falling.FallHandler;
import se.liu.andan346.tetris.falling.HeavyFallHandler;
import se.liu.andan346.tetris.poly.FallingPoly;
import se.liu.andan346.tetris.poly.Poly;
import se.liu.andan346.tetris.poly.TetrominoMaker;
import se.liu.andan346.tetris.util.BoardListener;
import se.liu.andan346.tetris.util.Direction;
import se.liu.andan346.tetris.util.SquareType;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.List;
import java.util.function.Consumer;

public class Board
{
    // Board representation
    private int width;
    private int height;
    private SquareType[][] squares;
    private FallingPoly falling = null;

    // Helper components
    private TetrominoMaker tetrominoFactory = new TetrominoMaker();
    private FallHandler fallHandler = new HeavyFallHandler(this);
    private List<BoardListener> listeners = new ArrayList<>();
    private final static Random RND = new Random();

    // Flags & score
    public boolean isGameOver = false;
    public boolean isGamePaused = false;
    private int score = 0;

    // Constructor
    public Board(final int width, final int height) {
	this.width = width;
	this.height = height;
	this.squares = new SquareType[height][width];
	reset();
	// Fill board with empty squares
	//for (SquareType[] row : squares) Arrays.fill(row, SquareType.EMPTY);
    }


    //#####################//
    //  GETTERS & SETTERS  //
    //#####################//

    public int getWidth() {
	return width;
    }

    public int getHeight() {
	return height;
    }

    public FallingPoly getFalling() {
	return falling;
    }

    public void setFalling(FallingPoly falling) {
	this.falling = falling;
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

    public boolean setSquareAt(int x, int y, SquareType type) {
	if (isWithinBoard(x, y)) {
	    squares[y][x] = type;
	    return true;
	}
	else return false;
    }

    public boolean setSquareAt(final Point pos, SquareType type) {
	return setSquareAt(pos.x, pos.y, type);
    }


    //####################//
    //  HELPER FUNCTIONS  //
    //####################//

    private boolean isWithinBoard(int x, int y) {
	return (0 <= x && x < getWidth()) && (0 <= y && y < getHeight());
    }

    public boolean isSupported(Point pos) {
	Point p = new Point(pos);
	while (p.y < getHeight()) {
	    if (getSquareAt(p) == SquareType.EMPTY)
		return false;
	    else
		p.translate(0, 1);
	}
	return true;
    }

    public Map<Point, SquareType> getConnectedColumn(Point pos) {
	Map<Point, SquareType> connectedCol = new HashMap<>();

	Point p = new Point(pos);
	while (p.y < getHeight()) {
	    if (getSquareAt(p) == SquareType.EMPTY || getSquareAt(p) == SquareType.OUTSIDE)
		break;
	    else {
		connectedCol.put(new Point(p), getSquareAt(p));
		p.translate(0, 1);
	    }
	}

	return connectedCol;
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

    public void notifyListeners() {
	for (BoardListener listener : listeners) {
	    listener.boardChanged(this);
	}
    }


    //#############//
    //  GAME TICK  //
    //#############//

    public void tick() {
	// If no falling poly currently exists...
	if (getFalling() == null) {

	    // Clear potential rows
	    List<Integer> rowsToClear = getRowsToClear();
	    if (!rowsToClear.isEmpty()) {
		awardScore(rowsToClear.size());
		clearRows(rowsToClear);
	    }

	    // Spawn new falling
	    spawnRandomFalling();

	// Else, move falling downwards
	} else moveFalling(Direction.DOWN);
    }


    //########################//
    //  FALLING POLY CONTROL  //
    //########################//

    public void moveFalling(Direction dir) {
	switch (dir) {
	    case LEFT -> fallHandler.moveFalling(-1, 0);
	    case RIGHT -> fallHandler.moveFalling(1, 0);
	    case DOWN -> fallHandler.moveFalling(0, 1);
	}
    }

    public void moveFalling(final int dx, final int dy) {
	if (falling == null) return;

	// Move falling to new position
	Point newPos = new Point(falling.getPos().x + dx, falling.getPos().y + dy);
	falling.setPos(newPos);

	// In case of collision...
	if (fallHandler.hasCollision()) {

	    // revert to previous state,
	    falling = falling.getPrevState();

	    // freeze falling poly in place.
	    if (dy > 0) {
		freezeFalling();
		tick(); // advance one tick to make gameplay smoother
	    }
	}

	// In case of no collision; move the squares on the board.
	else updateFallingSquares();
    }

    public void rotateFalling() {
	if (falling == null) return;

	// Rotate falling
	falling.rotate();

	// In case of a collision...
	if (fallHandler.hasCollision())

	    // revert to previous state,
	    falling = falling.getPrevState();

	    // In case of no collision; move the squares on the board.
	else updateFallingSquares();
    }

    public void freezeFalling() {
	falling = null;
    }

    public void updateFallingSquares() {
	FallingPoly currState = falling;
	FallingPoly prevState = falling.getPrevState();

	if (currState.getPos().y >= 0)
	    currState.updateBackdrop(this);

	prevState.iterSolidSquares(p -> this.setSquareAt(prevState.squareToBoard(p), currState.getBackdropSquare(prevState.squareToBoard(p))));

	currState.iterSolidSquares(p -> this.setSquareAt(currState.squareToBoard(p), currState.getType()));

	notifyListeners();
    }


    //#################//
    //  POLY SPAWNING  //
    //#################//

    private void spawnRandomFalling() {
	Poly poly = tetrominoFactory.getRandom();
	// Spawn top center of the board
	spawnFalling(new FallingPoly(poly, new Point(this.getWidth()/2 - poly.getWidth()/2, 0)));
    }

    private void spawnFalling(FallingPoly falling) {
	this.falling = falling;

	// Set falling pos above its spawn location
	falling.adjustForEmptySquares();
	falling.translate(0, -falling.getSolidHeight());

	// Until it's in its original position...
	for (int i = 0; i < falling.getSolidHeight(); i++) {
	    // Move down
	    Point posDown = new Point(falling.getPos().x, falling.getPos().y + 1);
	    falling.setPos(posDown);
	    // Check collision
	    if (getFallHandler().getDefault().hasCollision()) {
		if (!isGameOver) gameOver(); // Call game over if collision on spawn
	    }
	    else
		// Move the squares on the board
		updateFallingSquares();
	}
    }


    //########################//
    //  ROW CLEARING & SCORE  //
    //########################//

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

    private void awardScore(final int amtOfRows) {
	// amtofRows, score
	Map<Integer, Integer> scoreMap = Map.of(
		1, 100,
		2, 300,
		3, 500,
		4, 800
	);

	score += scoreMap.getOrDefault(Math.min(amtOfRows, scoreMap.size()), 0);
	notifyListeners();
    }


    //#################//
    //  MISCELLANEOUS  //
    //#################//

    public void generateRandom() {
	this.iterate(pos -> {
	    List<SquareType> squareTypes = SquareType.getValid();
	    setSquareAt(pos, squareTypes.get(RND.nextInt(squareTypes.size())));
	});
	notifyListeners();
    }

    private void gameOver() {
	isGameOver = true;
	freezeFalling();

	for (BoardListener listener : listeners) {
	    listener.onGameOver(this);
	}
    }

    public void togglePause() {
	isGamePaused = !isGamePaused;
	notifyListeners();
    }


    public void reset() {
	freezeFalling();
	for (SquareType[] row : squares) Arrays.fill(row, SquareType.EMPTY);
    }


    //##############//
    //  DEPRECATED  //
    //##############//

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
}
