package se.liu.andan346.tetris.gui;

import se.liu.andan346.tetris.Board;

import javax.swing.*;
import java.awt.*;

public class TetrisViewer
{
    private Board board;
    public TetrisComponent tetrisComponent = null;

    public TetrisViewer(Board board) {
	this.board = board;
    }

    public void show() {
	JFrame frame = new JFrame("Tetris");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	frame.setLayout(new BorderLayout());

	frame.setJMenuBar(new TetrisMenuBar());

	tetrisComponent = new TetrisComponent(board);
	frame.add(tetrisComponent);

	frame.pack(); // Apply changes
	frame.setLocationRelativeTo(null); // Center frame
	frame.setVisible(true);
    }
}
