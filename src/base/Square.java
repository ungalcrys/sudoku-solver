package base;

import java.util.LinkedList;

public class Square {
    private LinkedList<Integer> variants;
    private Integer value;

    private int row;
    private int col;
    private Grid grid;

    public Square(Grid grid, int row, int col, int value) {
        System.out.println("Square row:" + row + " col:" + col + " value:" + value);
        this.grid = grid;
        this.row = row;
        this.col = col;

        if (value == 0) {
            computePreVariants();
        } else {
            this.value = value;
            cleanAroundSquare();
        }
    }

    public Integer getValue() {
        return value;
    }

    public LinkedList<Integer> getVariants() {
        return variants;
    }

    private void cleanAroundSquare() {
        grid.removeRowVariants(value, row, col);
        grid.removeColVariants(value, row, col);
        grid.removeHouseVariants(value, row, col);
    }

    public void removeVariant(Integer value) {
        if (variants == null) {
            // no variants here
            System.out.println("null variants");
            return;
        }

        System.out.println("row:" + row + " col:" + col + " variants remove " + value);
        variants.remove(value);
        if (variants.size() == 1) {
            this.value = variants.get(0);
            variants = null;
            cleanAroundSquare();
        }
    }

    private void computePreVariants() {
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
