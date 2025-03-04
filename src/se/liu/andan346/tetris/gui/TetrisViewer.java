package se.liu.andan346.tetris.gui;

import se.liu.andan346.tetris.Board;
import se.liu.andan346.tetris.GameLoop;

import javax.swing.*;
import java.awt.*;

public class TetrisViewer
{
    private Board board;
    public TetrisComponent tetrisComponent = null;
    private JFrame frame = null;

    public TetrisViewer(Board board) {
	this.board = board;
    }

    public void initFrame() {
	this.frame = new JFrame("Tetris");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	frame.setLayout(new BorderLayout());

	TetrisMenuBar menuBar = new TetrisMenuBar(board);
	menuBar.addButtonListener(GameLoop.instance);
	board.addBoardListener(menuBar);
	frame.setJMenuBar(menuBar);

	tetrisComponent = new TetrisComponent(board);
	frame.add(tetrisComponent);

	frame.pack(); // Apply changes
	frame.setLocationRelativeTo(null); // Center frame
	frame.setVisible(true);
    }

    public void showSplash() {
	Dimension size = new Dimension(board.getWidth() * TetrisComponent.SQUARE_SIZE,
				       board.getHeight() * TetrisComponent.SQUARE_SIZE);
	TetrisSplashComponent splash = new TetrisSplashComponent(size, frame);
	splash.show();
    }
}
