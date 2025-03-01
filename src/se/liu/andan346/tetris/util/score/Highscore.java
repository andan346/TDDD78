package se.liu.andan346.tetris.util.score;

public class Highscore
{
    private String name;
    private int score;

    public Highscore(final String name, final int score) {
	this.name = name;
	this.score = score;
    }

    public String getName() {
	return name;
    }

    public int getScore() {
	return score;
    }

    @Override public String toString() {
	return String.format("%s: %d", getName(), getScore());
    }
}
