package se.liu.andan346.tetris.deprecated;

import se.liu.andan346.tetris.Board;
import se.liu.andan346.tetris.gui.TetrisViewer;
import se.liu.andan346.tetris.poly.TetrominoMaker;
import se.liu.andan346.tetris.util.SquareType;

@Deprecated
public class TetrisViewerTester
{
    public static void main(String[] args) {
	Board board = new Board(9, 18);
	//board.generateRandom();
	TetrominoMaker factory = new TetrominoMaker();
	board.setFalling(factory.getPoly(SquareType.Z), 7, 0);
	TetrisViewer viewer = new TetrisViewer(board);
	viewer.initFrame();
    }
}
