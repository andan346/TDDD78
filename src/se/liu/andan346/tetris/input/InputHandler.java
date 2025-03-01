package se.liu.andan346.tetris.input;

import se.liu.andan346.tetris.Board;
import se.liu.andan346.tetris.util.Direction;
import se.liu.andan346.tetris.gui.TetrisComponent;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class InputHandler
{
    private JComponent component;
    private Board board;

    public InputHandler(TetrisComponent component) {
	this.component = component;
	this.board = component.getBoard();

	addKeyBindings();
    }

    private void addKeyBindings() {
	bindKey(KeyEvent.VK_LEFT, "move_left", new MoveAction(board, Direction.LEFT));
	bindKey(KeyEvent.VK_RIGHT, "move_right", new MoveAction(board, Direction.RIGHT));
	bindKey(KeyEvent.VK_DOWN, "move_down", new MoveAction(board, Direction.DOWN));
	bindKey(KeyEvent.VK_UP, "rotate", new RotateAction(board));
    }

    private void bindKey(int keyCode, String actionName, AbstractAction action) {
	KeyStroke keyStroke = KeyStroke.getKeyStroke(keyCode, 0, false);
	component.getInputMap().put(keyStroke, actionName);
	component.getActionMap().put(actionName, action);
    }
}
