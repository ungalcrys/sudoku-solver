package base;

import java.security.InvalidParameterException;

public class Rect {
    private int x1;
    private int y1;
    private int x2;
    private int y2;

    public Rect(int y1, int x1, int y2, int x2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        if (!(y1 < y2 && x1 < x2))
            throw new InvalidParameterException(
                    "Rule should have y1 < y2 and x1 < x2. Current has " + this);
    }

    @Override
    public String toString() {
        return new StringBuffer().append(Utils.getChar(y1)).append(x1 + 1).append('-')
                .append(Utils.getChar(y2)).append(x2 + 1).toString();
    }

    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }

    public int getY1() {
        return y1;
    }

    public int getY2() {
        return y2;
    }
}
