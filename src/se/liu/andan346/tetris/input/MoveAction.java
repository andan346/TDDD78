package se.liu.andan346.tetris.input;

import se.liu.andan346.tetris.Board;
import se.liu.andan346.tetris.util.Direction;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MoveAction extends AbstractAction
{
    private Board board;
    private Direction dir;

    public MoveAction(final Board board, final Direction dir) {
	this.board = board;
	this.dir = dir;
    }

    @Override public void actionPerformed(final ActionEvent e) {
	board.moveFalling(dir);
    }
}
