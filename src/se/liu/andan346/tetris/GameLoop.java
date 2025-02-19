package se.liu.andan346.tetris;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class GameLoop
{
    private Board board;
    private TetrisViewer viewer = null;
    private BoardToTextConverter textConverter = new BoardToTextConverter();
    private int stepDelay;

    public GameLoop(Board board, int stepDelay) {
	this.board = board;
	this.stepDelay = stepDelay;
    }

    public void init() {
	viewer = new TetrisViewer(board);

	Timer timer = new Timer(stepDelay, performStep);
	timer.setCoalesce(true);
	timer.start();

	viewer.show();
    }

    public final Action performStep = new AbstractAction() {
	@Override public void actionPerformed(final ActionEvent e) {
	    //board.generateRandom();
	    board.addBoardListener(viewer.tetrisComponent);
	    board.tick();
	    //viewer.tetrisComponent.repaint();
	    //viewer.updateText(textConverter.convertToText(board));
	}
    };
}
