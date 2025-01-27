package se.liu.andan346.tetris;

public class TetrisViewerTester
{
    public static void main(String[] args) {
	Board board = new Board(9, 18);
	board.generateRandom();
	board.setFalling(new TetrominoMaker().getPoly(3), 1, 0);
	TetrisViewer viewer = new TetrisViewer(board);
	viewer.show();
    }
}
