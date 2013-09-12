package base;

public class Point {

    public final int row;
    public final int col;

    public Point(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object obj) {
        Point point = (Point) obj;
        return row == point.row && col == point.col;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer().append(Utils.getChar(row)).append(col + 1);
        return stringBuffer.toString();
    }
}
