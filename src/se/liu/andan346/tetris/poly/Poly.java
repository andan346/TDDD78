package se.liu.andan346.tetris.poly;

import se.liu.andan346.tetris.util.SquareType;

public class Poly
{
    private SquareType[][] shape;
    private SquareType type;

    public Poly(final SquareType[][] shape, final SquareType type) {
        this.shape = shape;
        this.type = type;
    }

    public int getHeight() {
        return shape.length;
    }

    public int getWidth() {
        return shape[0].length;
    }

    public SquareType getType() {
        return this.type;
    }

    public SquareType getSquareAt(int x, int y) {
        SquareType square;
        try {
            square = shape[y][x];
        }
        catch (IndexOutOfBoundsException e) {
            square = SquareType.EMPTY;
        }
        return square;
    }

    public void rotate(int times) {
        SquareType[][] newShape = new SquareType[getWidth()][getHeight()];

        for (int t = 0; t < times; t++) {
            for (int i = 0; i < getHeight(); i++) {
                for (int j = 0; j < getWidth(); j++) {
                    newShape[j][getHeight() - 1 - i] = shape[i][j];
                }
            }
        }

        shape = newShape;
    }

    public static Poly fromPattern(String[] pattern, SquareType type) {
        int rows = pattern.length;
        int cols = pattern[0].length();
        SquareType[][] shape = new SquareType[rows][cols];

        for (int i = 0; i < rows; i++) {
            String row = pattern[i];
            for (int j = 0; j < cols; j++) {
                char ch = row.charAt(j);
                shape[i][j] = (ch == '#') ? type : SquareType.EMPTY;
            }
        }

        return new Poly(shape, type);
    }
}
