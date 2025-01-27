package se.liu.andan346.tetris;

import javax.swing.*;
import java.awt.*;

public class TetrisViewer
{
    private Board board;
    private JTextArea textArea = null;

    public TetrisViewer(Board board) {
	this.board = board;
    }

    public void show() {
	JFrame frame = new JFrame("Tetris");
	frame.setLayout(new BorderLayout());

	this.textArea = new JTextArea(board.getWidth(), board.getHeight());
	frame.add(textArea, BorderLayout.CENTER);

	textArea.setText(new BoardToTextConverter().convertToText(board));
	textArea.setFont(new Font("Monospaced", Font.PLAIN, 20));

	frame.pack();
	frame.setVisible(true);
    }

    public void updateText(String text) {
	textArea.setText(text);
    }

    public static void main(String[] args) {
	new TetrisViewer(new Board(9, 18)).show();
    }
}
