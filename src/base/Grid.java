package base;

import java.util.Iterator;
import java.util.LinkedList;

public class Grid {
    private Square[][] squares = new Square[9][9];

    public Grid(int[][] initial) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                squares[row][col] = new Square(this, row, col, initial[row][col]);
                System.out.println();
            }
        }
    }

    public Square getSquare(int row, int col) {
        return squares[row][col];
    }

    /**
     * Removes all value occurrences from variants on specified row.
     * 
     * @param value
     *            to be removed from variants arrays
     * @param row
     *            where squares must be iterated
     * @param col
     *            limit of the iteration
     */
    public void removeRowVariants(Integer value, int row, int col) {
        System.out.println("==removePreRowVariants");
        for (int c = 0; c < 9; c += 1) {
            if (squares[row][c] != null)
                squares[row][c].removeVariant(value);
        }
    }

    /**
     * Removes all value occurrences from variants on specified column.
     * 
     * @param value
     *            to be removed from variants arrays
     * @param row
     *            limit of the iteration
     * @param col
     *            where squares must be iterated
     */
    public void removeColVariants(Integer value, int row, int col) {
        System.out.println("==removePreColVariants");
        for (int r = 0; r < 9; r += 1) {
            if (squares[r][col] != null)
                squares[r][col].removeVariant(value);
        }
    }

    public void removeHouseVariants(Integer value, int row, int col) {
        System.out.println("==removePreHouseVariants");
        for (int r = row / 3 * 3; r < ((row / 3 + 1) * 3); r += 1) {
            for (int c = col / 3 * 3; c < (col / 3 + 1) * 3; c += 1) {
                System.out.println("row:" + r + " col:" + c);
                if (squares[r][c] != null)
                    squares[r][c].removeVariant(value);
            }
        }
    }

    // on row
    public void getPreRowVariants(int row, int col, LinkedList<Integer> initialList) {
        for (int c = 0; c < col; c += 1) {
            Integer value = squares[row][c].getValue();
            if (value != null) {
                System.out.println("row remove " + value);
                initialList.remove(value);
            }
        }
    }

    // on column
    public void getPreColumnVariants(int row, int col, LinkedList<Integer> initialList) {
        for (int r = 0; r < row; r += 1) {
            Integer value = squares[r][col].getValue();
            System.out.println("col remove " + value);
            initialList.remove(value);
        }
    }

    // on house
    public void getPreHouseVariants(int row, int col, LinkedList<Integer> initialList) {
        for (int r = row / 3 * 3; r < row; r += 1) {
            for (int c = col / 3 * 3; c < (col / 3 + 1) * 3; c += 1) {
                Integer value = squares[r][c].getValue();
                System.out.println("home remove1 " + value);
                initialList.remove(value);
            }
        }
        for (int c = col / 3 * 3; c < col; c += 1) {
            Integer value = squares[row][c].getValue();
            System.out.println("home remove2 " + value);
            initialList.remove(value);
        }
    }

    public void cleanHouses() {
        for (int hr = 0; hr < 3; hr += 1) {
            for (int hc = 0; hc < 3; hc += 1) {
                cleanHouse(hr, hc);
            }
        }
    }

    // version 1: computing unique digits from existing variants
    // TODO version 2: computing unique digits from 1 to 9, checking in variants
    private void cleanHouse(int hr, int hc) {
        LinkedList<LinkedList<Integer>> vars = new LinkedList<LinkedList<Integer>>();
        for (int row = hr * 3; row < (hr + 1) * 3; row += 1) {
            for (int col = hc * 3; col < (hc + 1) * 3; col += 1) {
                Square square = getSquare(row, col);
                LinkedList<Integer> variants = square.getVariants();
                if (variants == null)
                    continue;

                vars.add((LinkedList<Integer>) variants.clone());

                // if (digits.size() == 0) {
                // for (Integer variant : square.getVariants())
                // digits.add(new SimpleSquare(variant, row, col));
                // // digits.addAll(square.getVariants());
                // continue;
                // }
                // square.getVariants().re
                // for(Integer variant:square.getVariants())
                // if ()

            }
        }

        for (int i = 0; i < vars.size(); i += 1) {
            Iterator<Integer> iterator = vars.get(i).iterator();
            while (iterator.hasNext()) {
                boolean mustRemove = false;
                Integer next = iterator.next();
                for (int j = 0; j < vars.size(); j += 1)
                    if (i != j) {
                        if (vars.get(j).remove(next))
                            mustRemove = true;
                    }
                if (mustRemove)
                    iterator.remove();
            }
        }

        for (LinkedList<Integer> var : vars) {
            for (Integer digit : var)
                System.out.println("=====digit " + digit);
        }
    }

}
