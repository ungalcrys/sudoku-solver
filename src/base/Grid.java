package base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import command.HiddenPair;

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
        System.out.println("removePreRowVariants");
        for (int c = 0; c < 9; c += 1) {
            if (squares[row][c] != null)
                squares[row][c].removeVariant(this, value);
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
        System.out.println("removePreColVariants");
        for (int r = 0; r < 9; r += 1) {
            if (squares[r][col] != null)
                squares[r][col].removeVariant(this, value);
        }
    }

    public void removeHouseVariants(Integer value, int row, int col) {
        System.out.println("removePreHouseVariants");
        for (int r = row / 3 * 3; r < ((row / 3 + 1) * 3); r += 1) {
            for (int c = col / 3 * 3; c < (col / 3 + 1) * 3; c += 1) {
                System.out.println("row:" + r + " col:" + c);
                if (squares[r][c] != null)
                    squares[r][c].removeVariant(this, value);
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
        boolean hasChanged = true;
        while (hasChanged) {
            hasChanged = false;
            for (int hr = 0; hr < 3; hr += 1) {
                for (int hc = 0; hc < 3; hc += 1) {
                    hasChanged = hasChanged || cleanHouse(hr, hc);
                }
            }
        }
    }

    // version 1: computing unique digits from existing variants
    // TODO version 2: computing unique digits from 1 to 9, checking in variants
    private boolean cleanHouse(int hr, int hc) {
        boolean isHouseChanged = false;
        LinkedList<Square> novalSquares = new LinkedList<Square>();

        int startRow = hr * 3;
        int endRow = (hr + 1) * 3;
        int startCol = hc * 3;
        int endCol = (hc + 1) * 3;
        for (int row = startRow; row < endRow; row += 1) {
            for (int col = startCol; col < endCol; col += 1) {
                LinkedList<Integer> variants = getSquare(row, col).getVariants();
                if (variants == null)
                    continue;
                novalSquares.add(new Square(row, col, variants));
            }
        }

        int novalSquaresCount = novalSquares.size();
        for (int i = 0; i < novalSquaresCount; i += 1) {
            Iterator<Integer> iterator = novalSquares.get(i).getVariants().iterator();
            while (iterator.hasNext()) {
                boolean mustRemove = false;
                Integer next = iterator.next();
                for (int j = 0; j < novalSquaresCount; j += 1)
                    if (i != j) {
                        if (novalSquares.get(j).getVariants().remove(next))
                            mustRemove = true;
                        else {
                            // TODO
                        }
                    }
                if (mustRemove)
                    iterator.remove();
            }
        }

        for (Square square : novalSquares) {
            LinkedList<Integer> variants = square.getVariants();
            if (variants.size() > 0) {
                Integer integer = variants.get(0);
                System.out.println();
                getSquare(square.getRow(), square.getCol()).setValue(integer, this);
                isHouseChanged = true;
            }
        }
        return isHouseChanged;
    }

    public void solveLockedCandidates1() {
        for (int hr = 0; hr < 3; hr += 1) {
            for (int hc = 0; hc < 3; hc += 1) {
                System.out.println("==house " + hr + ":" + hc);
                clearLine(hr, hc);
            }
        }

    }

    private void clearLine(int hr, int hc) {
        HashMap<Integer, ArrayList<Point>> map = new HashMap<Integer, ArrayList<Point>>();

        int startRow = hr * 3;
        int endRow = (hr + 1) * 3;
        int startCol = hc * 3;
        int endCol = (hc + 1) * 3;

        for (int row = startRow; row < endRow; row += 1) {
            for (int col = startCol; col < endCol; col += 1) {
                Square square = getSquare(row, col);
                LinkedList<Integer> variants = square.getVariants();
                if (variants == null)
                    continue;

                for (Integer variant : variants) {
                    ArrayList<Point> list = map.get(variant);
                    // System.out.println("put value:" + variant + " " + point);
                    if (list == null) {
                        ArrayList<Point> arrayList = new ArrayList<Point>();
                        arrayList.add(new Point(row, col));
                        map.put(variant, arrayList);
                        System.out.println("add1 " + variant + " now:" + arrayList);
                    } else {
                        list.add(new Point(row, col));
                        // map.put(variant, list);
                        System.out.println("add2 " + variant + " now:" + list);
                    }
                }
            }
        }

        // System.out.println("map size " + map.size());
        Iterator<Entry<Integer, ArrayList<Point>>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<Integer, ArrayList<Point>> next = iterator.next();
            List<Point> value = next.getValue();
            if (value.size() == 2) {
                System.out.println("iterator key:" + next.getKey() + " value  " + value);
                int x = 0;
                int y = 0;
                for (Point point : value) {
                    if (x == 0) {
                        x = point.getRow();
                        y = point.getCol();
                    } else {
                        if (x == point.getRow()) {
                            y = point.getCol();
                            for (int col = 0; col < 9; col++) {
                                if (col / 3 != y / 3) {
                                    Integer key = next.getKey();

                                    LinkedList<Integer> variants = getSquare(x, col).getVariants();
                                    if (variants != null && variants.remove(key))
                                        System.out.println("H remove from variants " + x + ":"
                                                + col + " value:" + key);
                                }
                            }
                        } else if (y == point.getCol()) {
                            x = point.getRow();
                            for (int row = 0; row < 9; row++) {
                                if (row / 3 != x / 3) {
                                    Integer key = next.getKey();
                                    Square square = getSquare(row, y);
                                    LinkedList<Integer> variants = square.getVariants();
                                    if (variants != null && variants.remove(key)) {
                                        System.out.println("V remove from variants " + row + ":"
                                                + y + " value:" + key);
                                        if (variants.size() == 1)
                                            square.setValue(variants.get(0), this);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        //

    }

    public void solveNakedPairs() {
        solveNakedPairs(true);
        solveNakedPairs(false);
    }

    private void solveNakedPairs(boolean isHorizontal) {
        for (int i = 0; i < 9; i += 1) {

            // create a 2 variant squares list
            LinkedList<Square> list = new LinkedList<Square>();
            for (int j = 0; j < 9; j += 1) {
                Square square = null;
                if (isHorizontal)
                    square = getSquare(i, j);
                else
                    square = getSquare(j, i);
                LinkedList<Integer> variants = square.getVariants();
                if (variants != null && variants.size() == 2) {
                    list.add(square);
                }
            }

            // create naked pairs list
            ArrayList<List<Square>> nakedPairs = getNakedPairs(list);

            // clean around naked pairs
            for (List<Square> pair : nakedPairs) {
                int squareIndex = 0;
                Square pairedSquare = pair.get(squareIndex);
                int squareRow = pairedSquare.getRow();
                int squareCol = pairedSquare.getCol();
                int skipJ = -1;
                if (isHorizontal)
                    skipJ = squareCol;
                else
                    skipJ = squareRow;

                for (int j = 0; j < 9; j += 1) {
                    if (j == skipJ) {
                        System.out.println("skip j:" + j);
                        if (squareIndex + 1 < pair.size()) {
                            pairedSquare = pair.get(1);
                            if (isHorizontal)
                                skipJ = pairedSquare.getCol();
                            else
                                skipJ = pairedSquare.getRow();
                        }
                    } else {
                        Square square = null;
                        if (isHorizontal) {
                            square = getSquare(i, j);
                            System.out.println("horizontal ");
                        } else {
                            square = getSquare(j, i);
                            System.out.println("vertical ");
                        }
                        for (Integer variant : pairedSquare.getVariants()) {
                            System.out.println("  remove variant:" + variant + " for i:" + i
                                    + " - j:" + j);
                            square.removeVariant(this, variant);
                        }
                    }
                }
            }
        }
    }

    private ArrayList<List<Square>> getNakedPairs(LinkedList<Square> list) {
        int size = 0;
        ArrayList<List<Square>> nakedPairs = new ArrayList<List<Square>>();
        while ((size = list.size()) > 1) {
            // size = list.size();
            Square firstSquare = list.get(0);
            int i = 0;
            for (i = 1; i < size; i += 1) {
                Square square = list.get(i);
                if (firstSquare.hasSameVariantsAs(square)) {
                    System.out.println("got naked pair " + firstSquare.getLocationAsString() + "-"
                            + square.getLocationAsString() + " " + firstSquare.getVariants());
                    list.remove(firstSquare);
                    list.remove(square);
                    List<Square> pair = Arrays.asList(firstSquare, square);
                    nakedPairs.add(pair);
                    break;
                }
            }

            // no naked pair not found
            if (i == size)
                list.remove(0);
        }
        return nakedPairs;
    }

    public void solveHiddenPairs() {
        for (int hr = 0; hr < 3; hr += 1) {
            for (int hc = 0; hc < 3; hc += 1) {
                new HiddenPair(this, hr, hc).solve();;
            }
        }
    }
}
