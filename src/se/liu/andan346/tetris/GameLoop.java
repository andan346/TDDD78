package se.liu.andan346.tetris;

import se.liu.andan346.tetris.deprecated.BoardToTextConverter;
import se.liu.andan346.tetris.gui.TetrisViewer;
import se.liu.andan346.tetris.util.score.HighscoreList;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class GameLoop
{
    private Board board;
    private TetrisViewer viewer;
    private BoardToTextConverter textConverter = new BoardToTextConverter();
    private HighscoreList highScoreList;

    private Timer timer;
    private int stepDelay;
    private int minStepDelay;
    private int timeBetweenSpeedIncrease;
    private int speedIncreaseAmount;
    private long lastDelayDecreaseTime;

    public GameLoop(Board board, int initStepDelay, int minStepDelay, int timeBetweenSpeedIncrease, int speedIncreaseAmount) {
	this.board = board;
	this.viewer = new TetrisViewer(board);
	this.highScoreList = new HighscoreList();

	this.stepDelay = initStepDelay;
	this.minStepDelay = minStepDelay;
	this.timeBetweenSpeedIncrease = timeBetweenSpeedIncrease;
	this.speedIncreaseAmount = speedIncreaseAmount;
    }

    public void init() {
	lastDelayDecreaseTime = System.currentTimeMillis();
	System.out.println(stepDelay);
	timer = new Timer(stepDelay, this::performStep);
	timer.setCoalesce(true);
	timer.start();

	board.addBoardListener(highScoreList);
    }

    private void performStep(ActionEvent actionEvent) {
	//board.generateRandom();
	board.addBoardListener(viewer.tetrisComponent);
	board.tick();
	//viewer.tetrisComponent.repaint();
	//viewer.updateText(textConverter.convertToText(board));

	long elapsed = System.currentTimeMillis() - lastDelayDecreaseTime;
	System.out.println(elapsed + " : " + stepDelay);
	if (elapsed > timeBetweenSpeedIncrease) {
	    lastDelayDecreaseTime = System.currentTimeMillis();
	    stepDelay = Math.max(minStepDelay, stepDelay - speedIncreaseAmount);
	    timer.setDelay(stepDelay);
	}

    }

    public TetrisViewer getViewer() {
	return viewer;
    }
}
