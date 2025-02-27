package se.liu.andan346.tetris;


public class Main
{
    public static void main(String[] args) {
	// Defaults
	Board board = new Board(10, 20);
	int stepDelay = 750;

	// Handle arguments
	switch (args.length) {
	    case 3:
		stepDelay = Integer.parseInt(args[2]);
	    case 2:
		board = new Board(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
	    	break;
	}

	GameLoop mainLoop = new GameLoop(board, stepDelay);
	mainLoop.init();
    }
}
