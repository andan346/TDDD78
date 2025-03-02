package se.liu.andan346.tetris.input;

import se.liu.andan346.tetris.Board;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PauseAction extends AbstractAction
{
    private Board board;
    private JComponent component;

    public PauseAction(final Board board, final JComponent component) {
	this.board = board;
	this.component = component;
    }

    @Override public void actionPerformed(final ActionEvent e) {
	board.togglePause();
    }
}
