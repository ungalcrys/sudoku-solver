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
    private static final int SQUARE_SIDE = 9;
    private static final int HOUSE_SIDE = 3;

    private static final Square[][] squares = new Square[9][9];

    public static Grid instance;

    /**
     * Constructor for the grid that fills the squares with given values and will also solve the
     * single position (single candidates) squares.
     * 
     * @param initial
     *            matrix to be solved
     */
    public Grid(int[][] initial) {
        instance = this;
        for (int row = 0; row < SQUARE_SIDE; row += 1) {
            for (int col = 0; col < SQUARE_SIDE; col += 1) {
                squares[row][col] = new Square(row, col, initial[row][col]);
            }
        }
    }

    public static Grid getInstance() {
        return instance;
    }

    public Square getSquare(int row, int col) {
        return squares[row][col];
    }

    private int getHouseStart(int index) {
        return index / HOUSE_SIDE * HOUSE_SIDE;
    }

    // TODO use command at cleanVariantsAround(Square) and
    // filterByPrevalues(Square)
    public void cleanVariantsAround(Square square) {
        Integer value = square.getValue();

        // System.out.println("clean row variants");
        for (int c = 0; c < SQUARE_SIDE; c += 1) {
            if (squares[square.point.row][c] == null)
                break;
            squares[square.point.row][c].removeVariant(value);
        }

        // System.out.println("clean col variants");
        for (int r = 0; r < SQUARE_SIDE; r += 1) {
            if (squares[r][square.point.col] == null)
                break;
            squares[r][square.point.col].removeVariant(value);
        }

        // System.out.println("clean house variants");
        int startRow = getHouseStart(square.point.row);
        int endRow = startRow + HOUSE_SIDE;
        int startCol = getHouseStart(square.point.col);
        int endCol = startCol + HOUSE_SIDE;
        outerloop: for (int r = startRow; r < endRow; r += 1) {
            for (int c = startCol; c < endCol; c += 1) {
                if (squares[r][c] == null)
                    break outerloop;
                squares[r][c].removeVariant(value);
            }
        }
    }

    private void removeValueFromVariants(int y, int x, LinkedList<Integer> variants) {
        Integer value = squares[y][x].getValue();
        if ((value != null) && variants.remove(value) && Square.DEBUG_SINGLE_CANDIDATE)
            System.out.println("remove from variants " + value);

    }

    public void filterVariantsByPrevalues(Square square) {
        LinkedList<Integer> variants = square.getVariants();

        for (int x = 0; x < square.point.col; x += 1) {
            removeValueFromVariants(square.point.row, x, variants);
        }

        for (int r = 0; r < square.point.row; r += 1) {
            removeValueFromVariants(r, square.point.col, variants);
        }

        int startRow = getHouseStart(square.point.row);
        int startCol = getHouseStart(square.point.col);
        int endCol = startCol + HOUSE_SIDE;

        // check the rows before the current row
        for (int y = startRow; y < square.point.row; y += 1) {
            for (int x = startCol; x < endCol; x += 1) {
                removeValueFromVariants(y, x, variants);
            }
        }

        // check the current row
        for (int c = startCol; c < square.point.col; c += 1) {
            removeValueFromVariants(square.point.row, c, variants);
        }
    }

    public void solveHiddenSingles() {
        boolean hasChanged = true;
        while (hasChanged) {
            hasChanged = false;
            for (int hr = 0; hr < HOUSE_SIDE; hr += 1) {
                for (int hc = 0; hc < HOUSE_SIDE; hc += 1) {
                    hasChanged = hasChanged || solveHiddenSingle(hr, hc);
                }
            }
        }
    }

    // version 1: computing unique digits from existing variants
    // TODO version 2: computing unique digits from 1 to 9, checking in variants
    private boolean solveHiddenSingle(int hr, int hc) {
        boolean isHouseChanged = false;
        // LinkedList<Square> unsolvedSquares = new LinkedList<Square>();
        ArrayList<Integer> checkedVariants = new ArrayList<Integer>();

        int startRow = hr * HOUSE_SIDE;
        int endRow = startRow + HOUSE_SIDE;
        int startCol = hc * HOUSE_SIDE;
        int endCol = startCol + HOUSE_SIDE;
        // boolean found = false;
        for (int r = startRow; r < endRow; r += 1) {
            for (int c = startCol; c < endCol; c += 1) {
                LinkedList<Integer> variants = getSquare(r, c).getVariants();
                if (variants == null)
                    continue;

                for (Integer variant : variants) {
                    if (variants.contains(variant))
                        continue;

                    // check if variant exists in next unsolved squares
                    int c2 = 0;
                    for (c2 = c + 1; c2 < endCol; c2 += 1) {
                        LinkedList<Integer> variants2 = getSquare(r, c2).getVariants();
                        if (variants2 != null && variants2.contains(variant)) {
                            break;
                        }
                    }
                    if (c2 < endCol) {
                        // break;
                    }

                }
                // if (variants == null)
                // continue;
                // unsolvedSquares.add(new Square(row, col, variants));
            }
        }

        // int novalSquaresCount = unsolvedSquares.size();
        // for (int i = 0; i < novalSquaresCount; i += 1) {
        // Iterator<Integer> iterator =
        // unsolvedSquares.get(i).getVariants().iterator();
        // while (iterator.hasNext()) {
        // boolean mustRemove = false;
        // Integer next = iterator.next();
        // for (int j = 0; j < novalSquaresCount; j += 1)
        // if (i != j) {
        // if (unsolvedSquares.get(j).getVariants().remove(next))
        // mustRemove = true;
        // else {
        // // TODO
        // }
        // }
        // if (mustRemove)
        // iterator.remove();
        // }
        // }
        //
        // for (Square square : unsolvedSquares) {
        // LinkedList<Integer> variants = square.getVariants();
        // if (variants.size() > 0) {
        // Integer integer = variants.get(0);
        // System.out.println();
        // getSquare(square.point.row, square.point.col).setValue(integer);
        // isHouseChanged = true;
        // }
        // }
        return isHouseChanged;
    }

    public void solveLockedCandidates1() {
        for (int hr = 0; hr < HOUSE_SIDE; hr += 1) {
            for (int hc = 0; hc < HOUSE_SIDE; hc += 1) {
                System.out.println("==house " + hr + ":" + hc);
                clearLine(hr, hc);
            }
        }

    }

    private void clearLine(int hr, int hc) {
        HashMap<Integer, ArrayList<Point>> map = new HashMap<Integer, ArrayList<Point>>();

        int startRow = hr * HOUSE_SIDE;
        int endRow = (hr + 1) * HOUSE_SIDE;
        int startCol = hc * HOUSE_SIDE;
        int endCol = (hc + 1) * HOUSE_SIDE;

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
                        x = point.row;
                        y = point.col;
                    } else {
                        if (x == point.row) {
                            y = point.col;
                            for (int col = 0; col < SQUARE_SIDE; col++) {
                                if (col / HOUSE_SIDE != y / HOUSE_SIDE) {
                                    Integer key = next.getKey();

                                    LinkedList<Integer> variants = getSquare(x, col).getVariants();
                                    if (variants != null && variants.remove(key))
                                        System.out.println("H remove from variants " + x + ":"
                                                + col + " value:" + key);
                                }
                            }
                        } else if (y == point.col) {
                            x = point.row;
                            for (int row = 0; row < SQUARE_SIDE; row++) {
                                if (row / HOUSE_SIDE != x / HOUSE_SIDE) {
                                    Integer key = next.getKey();
                                    Square square = getSquare(row, y);
                                    LinkedList<Integer> variants = square.getVariants();
                                    if (variants != null && variants.remove(key)) {
                                        System.out.println("V remove from variants " + row + ":"
                                                + y + " value:" + key);
                                        if (variants.size() == 1)
                                            square.setValue(variants.get(0));
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
        for (int i = 0; i < SQUARE_SIDE; i += 1) {

            // create a 2 variant squares list
            LinkedList<Square> list = new LinkedList<Square>();
            for (int j = 0; j < SQUARE_SIDE; j += 1) {
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
                int squareRow = pairedSquare.point.row;
                int squareCol = pairedSquare.point.col;
                int skipJ = -1;
                if (isHorizontal)
                    skipJ = squareCol;
                else
                    skipJ = squareRow;

                for (int j = 0; j < SQUARE_SIDE; j += 1) {
                    if (j == skipJ) {
                        System.out.println("skip j:" + j);
                        if (squareIndex + 1 < pair.size()) {
                            pairedSquare = pair.get(1);
                            if (isHorizontal)
                                skipJ = pairedSquare.point.col;
                            else
                                skipJ = pairedSquare.point.row;
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
                            square.removeVariant(variant);
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
                    System.out.println("got naked pair " + firstSquare.point + "-" + square.point
                            + " " + firstSquare.getVariants());
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
        for (int hr = 0; hr < HOUSE_SIDE; hr += 1) {
            for (int hc = 0; hc < HOUSE_SIDE; hc += 1) {
                new HiddenPair(this, hr, hc).execute();
                ;
            }
        }
    }

    private static final boolean DEBUG_XWING = false;

    public void useXWing() {
        System.out.println("=================================useXWing");
        for (int i = 1; i < 10; i += 1) {
            useXWing(i);
        }
    }

    public void useXWing(int val) {
        // TODO can use a arrayList with only n (number of found variants) as
        // elements
        int[] locationMap = new int[SQUARE_SIDE];
        printDigit(val);

        LinkedList<Rect> rectList = new LinkedList<Rect>();
        for (int y = 0; y < 8; y += 1) {

            // initialize location map
            for (int i = 0; i < SQUARE_SIDE; i += 1) {
                locationMap[i] = 0;
            }

            for (int x = 0; x < SQUARE_SIDE; x += 1) {
                LinkedList<Integer> variants = getSquare(y, x).getVariants();
                if (variants != null && variants.contains(val)) {
                    locationMap[x] = val;
                }
            }
            ArrayList<Integer> list = new ArrayList<Integer>(SQUARE_SIDE);
            for (int y2 = y + 1; y2 < SQUARE_SIDE; y2 += 1) {
                list.clear();
                for (int x = 0; x < SQUARE_SIDE; x += 1) {
                    LinkedList<Integer> variants = getSquare(y2, x).getVariants();
                    if (locationMap[x] == val && variants != null && variants.contains(val)) {
                        list.add(x);
                        if (list.size() > 2)
                            break;
                    }
                }
                if (list.size() == 2) {
                    Rect rect = new Rect(y, list.get(0), y2, list.get(1));
                    System.out.println("found rectangle " + rect);
                    rectList.add(rect);
                }
            }
        }

        ArrayList<Rect> rectsToBeRemoved = new ArrayList<Rect>(SQUARE_SIDE);
        int maxI = rectList.size() - 1;
        int maxI2 = rectList.size();
        for (int i = 0; i < maxI; i += 1) {
            for (int i2 = i + 1; i2 < maxI2; i2 += 1) {
                Rect rect1 = rectList.get(i);
                Rect rect2 = rectList.get(i2);
                if (rect1.getX1() == rect2.getX1()) {
                    if (!rectsToBeRemoved.contains(rect1))
                        rectsToBeRemoved.add(rect1);
                    rectsToBeRemoved.add(rect2);
                    break;
                }
            }
        }

        for (Rect rect : rectsToBeRemoved) {
            System.out.println("-invalid " + rect);
            rectList.remove(rect);
        }

        Iterator<Rect> iterator = rectList.iterator();
        while (iterator.hasNext()) {
            Rect rect = iterator.next();
            int y1 = rect.getY1();
            int y2 = rect.getY2();
            for (int y = 0; y < SQUARE_SIDE; y += 1) {
                if (y == y1 || y == y2)
                    continue;

                LinkedList<Integer> variants1 = getSquare(y, rect.getX1()).getVariants();
                LinkedList<Integer> variants2 = getSquare(y, rect.getX2()).getVariants();
                if (variants1 != null && variants1.contains(val) && variants2 != null
                        && variants2.contains(val)) {
                    iterator.remove();
                    System.out.println("=invalid " + rect);
                    break;
                }
            }
        }

        for (Rect rect : rectList) {
            System.out.println("=clean around rect " + rect);
            for (int xy = 0; xy < SQUARE_SIDE; xy += 1) {
                if (xy != rect.getX1() && xy != rect.getX2()) {
                    getSquare(rect.getY1(), xy).removeVariant(val);
                    getSquare(rect.getY2(), xy).removeVariant(val);
                }
                if (xy != rect.getY1() && xy != rect.getY2()) {
                    getSquare(xy, rect.getX1()).removeVariant(val);
                    getSquare(xy, rect.getX2()).removeVariant(val);
                }
            }
        }
        printDigit(val);
    }

    private static final boolean DEBUG_DIGIT = true;

    public void printDigit(int digit) {
        for (int y = 0; y < SQUARE_SIDE; y += 1) {
            if (DEBUG_DIGIT && y % HOUSE_SIDE == 0)
                System.out.println();
            for (int x = 0; x < SQUARE_SIDE; x += 1) {
                if (DEBUG_DIGIT && x % HOUSE_SIDE == 0)
                    System.out.print(" ");
                LinkedList<Integer> variants = getSquare(y, x).getVariants();
                if (variants != null && variants.contains(digit)) {
                    if (DEBUG_DIGIT)
                        System.out.print(digit + " ");
                } else {
                    if (DEBUG_DIGIT)
                        System.out.print(". ");
                }
            }
            if (DEBUG_DIGIT)
                System.out.println();
        }
        if (DEBUG_DIGIT)
            System.out.println("\n");
    }

    public void print() {
        for (int y = 0; y < SQUARE_SIDE; y += 1) {
            if (DEBUG_DIGIT && y % HOUSE_SIDE == 0)
                System.out.println();
            for (int x = 0; x < SQUARE_SIDE; x += 1) {
                if (DEBUG_DIGIT && x % HOUSE_SIDE == 0)
                    System.out.print(" ");
                Integer value = getSquare(y, x).getValue();
                if (value != null) {
                    if (DEBUG_DIGIT)
                        System.out.print(value + " ");
                } else {
                    if (DEBUG_DIGIT)
                        System.out.print(". ");
                }
            }
            if (DEBUG_DIGIT)
                System.out.println();
        }
        if (DEBUG_DIGIT)
            System.out.println("\n");
    }

    @Override
    public String toString() {
        StringBuffer lineBuffer = new StringBuffer();
        for (int r = 0; r < SQUARE_SIDE; r += 1) {
            for (int c = 0; c < SQUARE_SIDE; c += 1) {
                Square square = getSquare(r, c);
                Integer value = square.getValue();
                if (value != null)
                    lineBuffer.append(value);
                else {
                    LinkedList<Integer> variants = square.getVariants();
                    for (int i = 0; i < variants.size(); i += 1)
                        lineBuffer.append(variants.get(i));
                }
                lineBuffer.append(' ');
            }
            lineBuffer.append("\n");
        }
        return lineBuffer.toString();
    }
}
