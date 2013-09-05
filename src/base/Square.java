package base;

import java.util.LinkedList;

public class Square {
    private Integer value;
    private LinkedList<Integer> variants;

    private int row;
    private int col;

    // used by grid constructor
    public Square(Grid grid, int row, int col, int value) {
        System.out.println("Square row:" + row + " col:" + col + " value:" + value);
        this.row = row;
        this.col = col;

        if (value == 0) {
            computePreVariants(grid);
        } else {
            setValue(value, grid);
        }
    }

    // used by clean house
    public Square(int row, int col, LinkedList<Integer> variants) {
        this.row = row;
        this.col = col;
        this.variants = (LinkedList<Integer>) variants.clone();
    }

    public void setValue(Integer value, Grid grid) {
        System.out.println("==set value " + value);
        this.value = value;
        variants = null;
        cleanAroundSquare(grid);
    }

    public Integer getValue() {
        return value;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public LinkedList<Integer> getVariants() {
        return variants;
    }

    public void cleanAroundSquare(Grid grid) {
        grid.removeRowVariants(value, row, col);
        grid.removeColVariants(value, row, col);
        grid.removeHouseVariants(value, row, col);
    }

    public void removeVariant(Grid grid, Integer value) {
        if (variants == null) {
            // no variants here
            // System.out.println("null variants");
            return;
        }

        System.out.println("row:" + row + " col:" + col + " variants remove " + value);
        variants.remove(value);
        if (variants.size() == 1) {
            setValue(variants.get(0), grid);
        }
    }

    private void computePreVariants(Grid grid) {
        System.out.println("computePreVariants");
        variants = Utils.createVariants();
        grid.getPreRowVariants(row, col, variants);
        grid.getPreColumnVariants(row, col, variants);
        grid.getPreHouseVariants(row, col, variants);

        System.out.print("got ");
        for (int i = 0; i < variants.size(); i += 1)
            System.out.print(variants.get(i) + " ");
        System.out.println();
    }
}
