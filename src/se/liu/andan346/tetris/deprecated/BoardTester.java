package se.liu.andan346.tetris.deprecated;

import se.liu.andan346.tetris.Board;
import se.liu.andan346.tetris.poly.TetrominoMaker;

@Deprecated
public class BoardTester
{
    private static final BoardToTextConverter CONVERTER = new BoardToTextConverter();

    public static void main(String[] args) {
	Board board = new Board(7, 9);
	//board.generateRandom();
	TetrominoMaker factory = new TetrominoMaker();
	board.setFalling(factory.getPoly(1), 3, 0);
	board.setFalling(factory.getPoly(5), 5, 5);
	System.out.println(CONVERTER.convertToText(board));
    }
}
