package base;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        // int[][] sudoku = new int[][] {
        // { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        // { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        // { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        //
        // { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        // { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        // { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        //
        // { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        // { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        // { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, };
        int[][] sudoku = new int[][] { { 0, 0, 0, 0, 2, 0, 0, 0, 0 },
                { 0, 8, 0, 9, 7, 0, 0, 0, 2 }, { 0, 0, 0, 0, 0, 1, 0, 3, 8 },

                { 0, 5, 0, 0, 0, 0, 8, 0, 4 }, { 0, 1, 0, 0, 4, 0, 0, 9, 0 },
                { 6, 0, 3, 0, 0, 0, 0, 1, 0 },

                { 2, 6, 0, 1, 0, 0, 0, 0, 0 }, { 4, 0, 0, 0, 3, 2, 0, 6, 0 },
                { 0, 0, 0, 0, 8, 0, 0, 0, 0 }, };
        Grid grid = new Grid(sudoku);

        System.out.println("\n=================================cleanHouses\n");
        grid.cleanHouses();

        System.out.println("\n=================================LockedCandidates1\n");
        grid.cleanLockedCandidates1();

        // TODO do we really need that below?
        System.out.println("\n=================================cleanHouses\n");
        grid.cleanHouses();

        System.out.println("\n=================================LockedNakedPairs\n");
        grid.cleanNakedPairs();

        System.out.println("\n=================================print\n");
        for (int r = 0; r < 9; r += 1) {
            StringBuffer lineBuffer = new StringBuffer();
            for (int c = 0; c < 9; c += 1) {
                Square square = grid.getSquare(r, c);
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
            System.out.println(lineBuffer);
        }
    }
}
