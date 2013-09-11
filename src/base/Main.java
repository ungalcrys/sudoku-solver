package base;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
//        { 0, 0, 0,  0, 0, 0,  0, 0, 0 },
//        { 0, 0, 0,  0, 0, 0,  0, 0, 0 }, 
//        { 0, 0, 0,  0, 0, 0,  0, 0, 0 },

//        { 0, 0, 0,  0, 0, 0,  0, 0, 0 }, 
//        { 0, 0, 0,  0, 0, 0,  0, 0, 0 },
//        { 0, 0, 0,  0, 0, 0,  0, 0, 0 },

//        { 0, 0, 0,  0, 0, 0,  0, 0, 0 }, 
//        { 0, 0, 0,  0, 0, 0,  0, 0, 0 },
//        { 0, 0, 0,  0, 0, 0,  0, 0, 0 }, };
        int[][] sudoku = new int[][] { 
              { 0, 0, 0,  0, 0, 4,  0, 0, 1 },
              { 0, 0, 6,  0, 3, 8,  0, 7, 5 }, 
              { 0, 0, 2,  0, 0, 0,  4, 0, 0 },

              { 0, 2, 0,  9, 0, 7,  0, 0, 8 }, 
              { 7, 8, 0,  0, 0, 0,  0, 0, 0 },
              { 0, 0, 0,  0, 0, 2,  7, 0, 0 },

              { 8, 0, 0,  0, 0, 6,  0, 1, 0 }, 
              { 2, 0, 0,  3, 0, 1,  0, 5, 0 },
              { 0, 0, 0,  0, 2, 9,  0, 0, 0 }, };
        Grid grid = new Grid(sudoku);

//        System.out.println("\n=================================cleanHouses\n");
//        grid.cleanHouses();
//
//        System.out.println("\n=================================LockedCandidates1\n");
//        grid.solveLockedCandidates1();
//
//        // TODO do we really need that below?
//        System.out.println("\n=================================cleanHouses\n");
//        grid.cleanHouses();
//
//        System.out.println("\n=================================solveNakedPairs\n");
//        grid.solveNakedPairs();
//        
//        System.out.println("\n=================================solveHiddenPairs\n");
//        grid.solveHiddenPairs();

        System.out.println("\n=================================print\n");
        System.out.println(grid.toString());

        grid.useXWing();
        grid.cleanHouses();
        
        System.out.println("\n=================================print\n");
        System.out.println(grid.toString());
    }
}
