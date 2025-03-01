package se.liu.andan346.tetris.util.score;

import java.util.Comparator;

public class ScoreComparator implements Comparator<Highscore>
{
    @Override public int compare(final Highscore o1, final Highscore o2) {
	return Integer.compare(o2.getScore(), o1.getScore());
    }
}
