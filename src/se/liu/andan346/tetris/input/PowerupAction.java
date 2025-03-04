package se.liu.andan346.tetris.input;

import se.liu.andan346.tetris.Board;
import se.liu.andan346.tetris.falling.FallthroughFallHandler;
import se.liu.andan346.tetris.falling.HeavyFallHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class PowerupAction extends AbstractAction
{
    private Board board;

    public PowerupAction(final Board board) {
	this.board = board;
    }

    @Override public void actionPerformed(final ActionEvent e) {
	int rnd = new Random().nextInt(2);
	switch (rnd) {
	    case 0 -> board.setPowerup(new FallthroughFallHandler(board));
	    case 1 -> board.setPowerup(new HeavyFallHandler(board));
	}
    }
}
