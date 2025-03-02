package se.liu.andan346.tetris.util;

import se.liu.andan346.tetris.Board;

public interface BoardListener
{
    public void boardChanged(Board board);

    public void onGameOver(Board board);
}
