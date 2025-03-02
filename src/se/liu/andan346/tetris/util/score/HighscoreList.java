package se.liu.andan346.tetris.util.score;

import com.google.gson.reflect.TypeToken;
import se.liu.andan346.tetris.Board;
import se.liu.andan346.tetris.util.BoardListener;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;

public class HighscoreList implements BoardListener
{
    private List<Highscore> highscores = null;
    private Gson gson = new Gson().newBuilder().setPrettyPrinting().create();
    private static final String FILE_PATH = "highscores.json";
    private static final String TMP_FILE_PATH = FILE_PATH + ".tmp";

    public HighscoreList() {
	while (true) {
	    try {
		this.highscores = readHighscoresFromFile();
		break;
	    } catch (IOException e) {
		if (askTryAgain("Det gick inte att läsa in tidigare highscore.") != JOptionPane.YES_OPTION)
		    break;
	    }
	}
    }

    public void addHighscore(Highscore highScore) throws IOException {
	highscores.add(highScore);
	highscores.sort(new ScoreComparator());
	saveHighscoresToFile();
    }

    public List<Highscore> getHighscores() {
	return highscores;
    }

    public void showHighscores() {
	JOptionPane.showMessageDialog(null, toString());
    }

    private void askUser(int score) {
	String input = JOptionPane.showInputDialog("Skriv in namn för att hamna på highscore-listan!");
	while (true) {
	    try {
		if (input != null)
		    addHighscore(new Highscore(input, score));
		break;
	    } catch (IOException e) {
		if (askTryAgain("Det gick inte att spara ditt highscore.") != JOptionPane.YES_OPTION)
		    break;
	    }
	}
	showHighscores();
    }

    private List<Highscore> readHighscoresFromFile() throws IOException {
	try (FileReader reader = new FileReader(FILE_PATH)) {
	    Type listType = new TypeToken<List<Highscore>>(){}.getType();
	    List<Highscore> list = gson.fromJson(reader, listType);
	    return list != null ? list : new ArrayList<>();
	} catch (FileNotFoundException e) {
	    return new ArrayList<>();
	}
    }

    private void saveHighscoresToFile() throws IOException {
	File tmpFile = new File(TMP_FILE_PATH);
	try (FileWriter writer = new FileWriter(tmpFile)) {
	    gson.toJson(highscores, writer);
	}

	File ogFile = new File(FILE_PATH);

	if (ogFile.exists() && !ogFile.delete())
	    throw new IOException("Kunde inte ta bort den gamla highscore-filen.");

	if (!tmpFile.renameTo(ogFile))
	    throw new IOException("Kunde inte byta namn på tmp-filen");
    }

    @Override public String toString() {
	return getHighscores().stream().map(Highscore::toString).collect(Collectors.joining("\n"));
    }

    private int askTryAgain(String message) {
	return JOptionPane.showConfirmDialog(
		null,
		message + "\nVill du försöka igen?",
		"Ett problem uppstod!",
		JOptionPane.YES_NO_OPTION
	);
    }

    @Override public void boardChanged(final Board board) {
	// Do nothing
    }

    @Override public void onGameOver(final Board board) {
	askUser(board.getScore());
    }
}
