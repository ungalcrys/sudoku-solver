package command;

import java.util.ArrayList;

import base.Grid;
import base.Point;

public abstract class HouseCommand {
    protected ArrayList<Point>[] map;
    protected Grid grid;

    protected int startRow;
    protected int endRow;
    protected int startCol;
    protected int endCol;

    public HouseCommand(Grid grid, int hr, int hc) {
        this.grid = grid;
        // from 1 to 9
        map = new ArrayList[10];

        startRow = hr * 3;
        endRow = (hr + 1) * 3;
        startCol = hc * 3;
        endCol = (hc + 1) * 3;
    }

    public void execute() {
        for (int row = startRow; row < endRow; row += 1) {
            for (int col = startCol; col < endCol; col += 1) {
                addToRawMap(row, col);
            }
        }
        clean();
        // solve();
    }

    public abstract void addToRawMap(int row, int col);

    public abstract void clean();

    public abstract void solve();
}
