package se.liu.andan346.tetris.falling;

public interface FallHandler
{
    public boolean hasCollision();

    public void moveFalling(int dx, int dy);

    public FallHandler getDefault();
}
