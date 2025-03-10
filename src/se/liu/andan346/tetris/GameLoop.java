package se.liu.andan346.tetris;

import se.liu.andan346.tetris.deprecated.BoardToTextConverter;
import se.liu.andan346.tetris.gui.ButtonListener;
import se.liu.andan346.tetris.gui.TetrisViewer;
import se.liu.andan346.tetris.util.BoardListener;
import se.liu.andan346.tetris.util.score.HighscoreList;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class GameLoop implements BoardListener, ButtonListener
{
    public static GameLoop instance = null;

    private Board board;
    private TetrisViewer viewer;
    private BoardToTextConverter textConverter = new BoardToTextConverter();
    private HighscoreList highScoreList;

    private Timer timer = null;
    private int initStepDelay;
    private int stepDelay;
    private int minStepDelay;
    private int timeBetweenSpeedIncrease;
    private int speedIncreaseAmount;
    private long lastDelayDecreaseTime;

    public GameLoop(Board board, int initStepDelay, int minStepDelay, int timeBetweenSpeedIncrease, int speedIncreaseAmount) {
	if (instance == null)
	    instance = this;

	this.board = board;
	this.viewer = new TetrisViewer(board);
	this.highScoreList = new HighscoreList();

	this.initStepDelay = initStepDelay;
	this.minStepDelay = minStepDelay;
	this.timeBetweenSpeedIncrease = timeBetweenSpeedIncrease;
	this.speedIncreaseAmount = speedIncreaseAmount;
    }

    public void init() {
	stepDelay = initStepDelay;
	lastDelayDecreaseTime = System.currentTimeMillis();

	timer = new Timer(stepDelay, this::performStep);
	timer.setCoalesce(true);
	timer.start();

	board.addBoardListener(this);
	board.addBoardListener(highScoreList);
    }

    public TetrisViewer getViewer() {
	return viewer;
    }

    private void performStep(ActionEvent actionEvent) {
	board.addBoardListener(viewer.tetrisComponent);
	board.tick();
	//board.generateRandom();

	long elapsed = System.currentTimeMillis() - lastDelayDecreaseTime;
	if (elapsed > timeBetweenSpeedIncrease) {
	    lastDelayDecreaseTime = System.currentTimeMillis();
	    stepDelay = Math.max(minStepDelay, stepDelay - speedIncreaseAmount);
	    timer.setDelay(stepDelay);
	}
    }

    @Override public void boardChanged(final Board board) {
	if ((board.isGamePaused || board.isGameOver) && timer.isRunning())
	    timer.stop();
	else if (!timer.isRunning())
	    timer.start();
    }

    @Override public void onGameOver(final Board board) {
	timer.stop();
    }

    @Override public void onRestart() {
	timer.stop();
	board.reset();
	init();
    }
}
