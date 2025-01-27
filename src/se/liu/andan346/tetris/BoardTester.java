package se.liu.andan346.tetris;

import java.util.Random;

public class BoardTester
{
    private static final BoardToTextConverter textConverter = new BoardToTextConverter();

    public static void main(String[] args) {
	Board board = new Board(6, 9);
	for (int i = 0; i < 10; i++) {
	    board.generateRandom();
	    System.out.println(textConverter.convertToText(board) + "\n");
	}
    }
}
