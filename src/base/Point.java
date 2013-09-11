package base;

public class Point {

    private int row;
    private int col;

    public Point(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public boolean equals(Object obj) {
        Point point = (Point) obj;
        return row == point.getRow() && col == point.getCol();
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer().append(Utils.getChar(row)).append(col + 1);
        return stringBuffer.toString();
    }
}
