package se.liu.andan346.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameLoop
{
    private Point boardSize = new Point(10, 20);
    private Board board = null;
    private int stepDelay = 1000;

    public GameLoop(String[] args) {
	if (args.length >= 2)
	    boardSize = new Point(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
	if (args.length >= 3)
	    stepDelay = Integer.parseInt(args[2]);
    }

    public void init() {
	board = new Board(boardSize.x, boardSize.y);
	Timer timer = new Timer(stepDelay, performStep);
	timer.setCoalesce(true);
	timer.start();
	TetrisViewer viewer = new TetrisViewer(board);
	viewer.show();
    }

    final Action performStep = new AbstractAction() {
	@Override public void actionPerformed(final ActionEvent e) {

	    board.generateRandom();
	}
    };
}
