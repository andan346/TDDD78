package se.liu.andan346.tetris.input;

import se.liu.andan346.tetris.Board;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RotateAction extends AbstractAction
{
    private Board board;

    public RotateAction(final Board board) {
	this.board = board;
    }

    @Override public void actionPerformed(final ActionEvent e) {
	board.rotateFallingPoly();
    }
}
