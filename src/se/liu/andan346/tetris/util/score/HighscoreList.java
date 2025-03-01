package se.liu.andan346.tetris.util.score;

import se.liu.andan346.tetris.Board;
import se.liu.andan346.tetris.util.BoardListener;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HighscoreList implements BoardListener
{
    private List<Highscore> highScores = new ArrayList<>();

    public void addHighScore(Highscore highScore) {
	highScores.add(highScore);
	highScores.sort(new ScoreComparator());
    }

    public List<Highscore> getHighScores() {
	return highScores;
    }

    @Override public void boardChanged(final Board board) {
	if (board.isGameOver)
	    askUser(board.getScore());
    }

    @Override public String toString() {
	return highScores.stream().map(Highscore::toString).collect(Collectors.joining("\n"));
    }

    public void showHighScores() {
	addHighScore(new Highscore("hi", 100));
	addHighScore(new Highscore("testguy", 300));
	JOptionPane.showMessageDialog(null, toString());
    }

    private void askUser(int score) {
	String input = JOptionPane.showInputDialog("Skriv in namn för att hamna på highscore-listan!");
	addHighScore(new Highscore(input, score));
	showHighScores();
    }
}
