package se.liu.andan346.tetris;

public class Poly
{
    private SquareType[][] shape;

    public Poly(final SquareType[][] shape) {
        this.shape = shape;
    }

    public int getHeight() {
        return  shape.length;
    }

    public int getWidth() {
        return shape[0].length;
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

        return new Poly(shape);
    }

}
