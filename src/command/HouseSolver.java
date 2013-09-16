package command;

import java.util.ArrayList;
import java.util.LinkedList;

import base.Grid;
import base.Square;

public abstract class HouseSolver {

    static final boolean DEBUG_HIDDEN_SINGLE = false;

    private int startRow;
    private int endRow;
    private int startCol;
    private int endCol;

    public void execute() {
        boolean isGridModified = true;
        while (isGridModified) {
            isGridModified = false;
            for (int hr = 0; hr < Grid.HOUSE_SIDE; hr += 1) {
                for (int hc = 0; hc < Grid.HOUSE_SIDE; hc += 1) {

                    ArrayList<Integer> checkedVariants = new ArrayList<Integer>(9);

                    startRow = hr * Grid.HOUSE_SIDE;
                    endRow = startRow + Grid.HOUSE_SIDE;
                    startCol = hc * Grid.HOUSE_SIDE;
                    endCol = startCol + Grid.HOUSE_SIDE;

                    houseIteration: for (int r = startRow; r < endRow; r += 1) {
                        for (int c = startCol; c < endCol; c += 1) {
                            if (isHouseQualified(r, c, checkedVariants)) {
                                isGridModified = true;
                                break houseIteration;
                            }
                        }
                    }// end house iteration

                }
            }// end grid iteration

        }
    }

    public int getStartRow() {
        return startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public int getStartCol() {
        return startCol;
    }

    public int getEndCol() {
        return endCol;
    }

    boolean isVariantIn(int row, int col, Integer variant, ArrayList<Integer> checkedVariants) {
        Square square = Grid.getInstance().getSquare(row, col);
        LinkedList<Integer> variants2 = square.getVariants();
        if (variants2 == null)
            return false;

        if (DEBUG_HIDDEN_SINGLE)
            System.out.println("check in " + square.point + " with " + variants2);
        if (variants2.contains(variant)) {
            if (DEBUG_HIDDEN_SINGLE)
                System.out.println("exists");
            checkedVariants.add(variant);
            return true;
        }
        return false;
    }

    abstract boolean isHouseQualified(int r, int c, ArrayList<Integer> checkedVariants);
}
