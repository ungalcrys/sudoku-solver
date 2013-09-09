package command;

import java.util.HashMap;
import java.util.List;

import base.Grid;
import base.Point;

public abstract class HouseCommand {
    protected HashMap<Integer, List<Point>> map;
    protected Grid grid;

    protected int startRow;
    protected int endRow;
    protected int startCol;
    protected int endCol;

    public HouseCommand(Grid grid, int hr, int hc) {
        this.grid = grid;
        map = new HashMap<Integer, List<Point>>();

        startRow = hr * 3;
        endRow = (hr + 1) * 3;
        startCol = hc * 3;
        endCol = (hc + 1) * 3;
    }

    public void solve() {
        for (int row = startRow; row < endRow; row += 1) {
            for (int col = startCol; col < endCol; col += 1) {
                createRawMap(row, col);
            }
        }
        clean();
    }

    public abstract void createRawMap(int row, int col);

    public abstract void clean();
}
