package se.liu.andan346.tetris;

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

	tetrisComponent = new TetrisComponent(board);
	frame.add(tetrisComponent);

	frame.pack();
	frame.setVisible(true);
    }

    public static void main(String[] args) {
	new TetrisViewer(new Board(9, 18)).show();
    }
}
