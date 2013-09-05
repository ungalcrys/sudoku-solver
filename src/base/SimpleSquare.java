package base;

public class SimpleSquare {
    private final int value;
    private final int row;
    private final int col;

    public SimpleSquare(int value, int row, int col) {
        this.value = value;
        this.row = row;
        this.col = col;
    }

    public int getValue() {
        return value;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
