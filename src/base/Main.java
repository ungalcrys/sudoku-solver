package base;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        int[][] sudoku = new int[][] { 
                { 0, 0, 0,  0, 0, 0,  6, 8, 0 },
                { 0, 0, 0,  0, 7, 3,  0, 0, 9 },
                { 3, 0, 9,  0, 0, 0,  0, 4, 5 },

                { 4, 9, 0,  0, 0, 0,  0, 0, 0 },
                { 8, 0, 3,  0, 5, 0,  9, 0, 2 },
                { 0, 0, 0,  0, 0, 0,  0, 3, 6 },

                { 9, 6, 0,  0, 0, 0,  3, 0, 8 },
                { 7, 0, 0,  6, 8, 0,  0, 0, 0 },
                { 0, 2, 8,  0, 0, 0,  0, 0, 0 },
                };
        Grid grid = new Grid(sudoku);
        grid.cleanHouses();

        System.out.println("\n=================\n");
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
