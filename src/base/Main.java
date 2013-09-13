package base;

public class Main {
    public static void main(String[] args) {
        // @formatter:off
        int[][] sudoku = new int[][] { 
          { 0, 3, 0,  5, 0, 0,  1, 4, 0 },
          { 0, 0, 0,  1, 0, 8,  0, 0, 0 }, 
          { 0, 6, 0,  0, 0, 2,  0, 0, 9 },

          { 3, 5, 0,  0, 0, 0,  0, 0, 0 }, 
          { 0, 9, 0,  6, 0, 4,  8, 0, 0 },
          { 6, 0, 2,  0, 0, 0,  5, 0, 0 },

          { 0, 0, 0,  0, 1, 0,  9, 7, 0 }, 
          { 0, 0, 0,  0, 0, 0,  2, 0, 0 },
          { 4, 7, 0,  0, 0, 0,  0, 6, 0 } };
        Grid grid = new Grid(sudoku);
     // @formatter:on

        // System.out.println("\n" + grid);

        grid.solveHiddenSingles();

        // System.out.println("\n=================================LockedCandidates1\n");
        // grid.solveLockedCandidates1();
        //
        // // TODO do we really need that below?
        // System.out.println("\n=================================cleanHouses\n");
        // grid.cleanHouses();
        //
        // System.out.println("\n=================================solveNakedPairs\n");
        // grid.solveNakedPairs();

        // System.out.println("\n=================================solveHiddenPairs\n");
        // grid.solveHiddenPairs();

        // System.out.println("\n=================================print\n");
        // System.out.println(grid.toString());

        // grid.useXWing(7);
        // grid.cleanHouses();

        // System.out.println("\n=================================print");
         grid.print();

        // System.out.println(grid);
    }
}
