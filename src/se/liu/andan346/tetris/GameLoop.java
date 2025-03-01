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
    private int stepDelay;
    private HighscoreList highScoreList;

    public GameLoop(Board board, int stepDelay) {
	this.board = board;
	this.stepDelay = stepDelay;
	this.viewer = new TetrisViewer(board);
	this.highScoreList = new HighscoreList();
    }

    public void init() {
	Timer timer = new Timer(stepDelay, this::performStep);
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
    }

    public TetrisViewer getViewer() {
	return viewer;
    }
}
