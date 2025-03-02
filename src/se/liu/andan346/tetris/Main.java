package se.liu.andan346.tetris;


import se.liu.andan346.tetris.gui.TetrisViewer;
import se.liu.andan346.tetris.util.score.HighscoreList;

public class Main
{
    public static void main(String[] args) {
	// Defaults
	Board board = new Board(10, 20);
	int initStepDelay = 750;
	int minStepDelay = 250;
	int timeBetweenSpeedIncrease = 30000;
	int speedIncreaseAmount = 50;

	// Handle arguments
	switch (args.length) {
	    case 6:
		minStepDelay = Integer.parseInt(args[3]);
		timeBetweenSpeedIncrease = Integer.parseInt(args[4]);
		speedIncreaseAmount = Integer.parseInt(args[5]);
	    case 3:
		initStepDelay = Integer.parseInt(args[2]);
	    case 2:
		board = new Board(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
	    	break;
	}

	HighscoreList highScoreList = new HighscoreList();

	GameLoop mainLoop = new GameLoop(board, initStepDelay, minStepDelay, timeBetweenSpeedIncrease, speedIncreaseAmount);

	TetrisViewer viewer = mainLoop.getViewer();
	viewer.initFrame();
	viewer.showSplash();

	mainLoop.init();
    }
}
