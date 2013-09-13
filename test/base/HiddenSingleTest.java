package base;

import junit.framework.TestCase;

import org.junit.Test;

public class HiddenSingleTest extends TestCase {

    // @formatter:off
    private int[][] createEmptyGrid() {
        int[][] emptyGrid = new int[][] { 
            { 0, 0, 0,  0, 0, 0,  0, 0, 0 },
            { 0, 0, 0,  0, 0, 0,  0, 0, 0 }, 
            { 0, 0, 0,  0, 0, 0,  0, 0, 0 },
            
            { 0, 0, 0,  0, 0, 0,  0, 0, 0 }, 
            { 0, 0, 0,  0, 0, 0,  0, 0, 0 },
            { 0, 0, 0,  0, 0, 0,  0, 0, 0 },
            
            { 0, 0, 0,  0, 0, 0,  0, 0, 0 }, 
            { 0, 0, 0,  0, 0, 0,  0, 0, 0 },
            { 0, 0, 0,  0, 0, 0,  0, 0, 0 } };
        return emptyGrid;
    }
    // @formatter:on

    @Test
    public void testHouse00() throws Exception {
        Grid grid = new Grid(createEmptyGrid());
        int[] variants00 = new int[] { 2, 7, 8, 9 };
        int[] variants02 = new int[] { 7, 8, 9 };
        int[] variants10 = new int[] { 2, 5, 7, 9 };
        int[] variants11 = new int[] { 2, 4 };
        int[] variants12 = new int[] { 4, 5, 7, 9 };
        int[] variants20 = new int[] { 1, 5, 8 };
        int[] variants22 = new int[] { 1, 4, 5, 8 };
        grid.getSquare(0, 0).setOnly(variants00);
        grid.getSquare(0, 1).setOnly(3);
        grid.getSquare(0, 2).setOnly(7, 8, 9);
        grid.getSquare(1, 0).setOnly(2, 5, 7, 9);
        grid.getSquare(1, 1).setOnly(2, 4);
        grid.getSquare(1, 2).setOnly(4, 5, 7, 9);
        grid.getSquare(2, 0).setOnly(1, 5, 8);
        grid.getSquare(2, 1).setOnly(6);
        grid.getSquare(2, 2).setOnly(1, 4, 5, 8);
        grid.solveHiddenSingle(0, 0);

        assertTrue(grid.areEqual(0, 0, variants00));
        assertTrue(grid.areEqual(0, 1, 3));
        assertTrue(grid.areEqual(0, 2, variants02));

        assertTrue(grid.areEqual(1, 0, variants10));
        assertTrue(grid.areEqual(1, 1, variants11));
        assertTrue(grid.areEqual(1, 2, variants12));

        assertTrue(grid.areEqual(2, 0, variants20));
        assertTrue(grid.areEqual(2, 1, 6));
        assertTrue(grid.areEqual(2, 2, variants22));
    }

    @Test
    public void testHouse01() throws Exception {
        Grid grid = new Grid(createEmptyGrid());
        int[] variants04 = new int[] { 6, 7, 9 };
        int[] variants05 = new int[] { 6, 7, 9 };
        int[] variants14 = new int[] { 3, 4, 7, 9 };
        int[] variants23 = new int[] { 3, 4 };
        int[] variants24 = new int[] { 3, 4 };
        grid.getSquare(0, 3).setOnly(5);
        grid.getSquare(0, 4).setOnly(variants04);
        grid.getSquare(0, 5).setOnly(variants05);
        grid.getSquare(1, 3).setOnly(1);
        grid.getSquare(1, 4).setOnly(variants14);
        grid.getSquare(1, 5).setOnly(8);
        grid.getSquare(2, 3).setOnly(variants23);
        grid.getSquare(2, 4).setOnly(variants24);
        grid.getSquare(2, 5).setOnly(2);
        grid.solveHiddenSingle(0, 1);

        assertTrue(grid.areEqual(0, 3, 5));
        assertTrue(grid.areEqual(0, 4, variants04));
        assertTrue(grid.areEqual(0, 5, variants05));

        assertTrue(grid.areEqual(1, 3, 1));
        assertTrue(grid.areEqual(1, 4, variants14));
        assertTrue(grid.areEqual(1, 5, 8));

        assertTrue(grid.areEqual(2, 3, variants23));
        assertTrue(grid.areEqual(2, 4, variants24));
        assertTrue(grid.areEqual(2, 5, 2));
    }

    @Test
    public void testHouse02() throws Exception {
        Grid grid = new Grid(createEmptyGrid());
        int[] variants08 = new int[] { 2, 8 };
        int[] variants17 = new int[] { 2, 3, 5 };
        int[] variants18 = new int[] { 2, 3, 5 };
        int[] variants27 = new int[] { 3, 5, 8 };

        grid.getSquare(0, 6).setOnly(1);
        grid.getSquare(0, 7).setOnly(4);
        grid.getSquare(0, 8).setOnly(variants08);
        grid.getSquare(1, 6).setOnly(6);
        grid.getSquare(1, 7).setOnly(variants17);
        grid.getSquare(1, 8).setOnly(variants18);
        grid.getSquare(2, 6).setOnly(7);
        grid.getSquare(2, 7).setOnly(variants27);
        grid.getSquare(2, 8).setOnly(9);
        grid.solveHiddenSingle(0, 2);

        assertTrue(grid.areEqual(0, 6, 1));
        assertTrue(grid.areEqual(0, 7, 4));
        assertTrue(grid.areEqual(0, 8, variants08));

        assertTrue(grid.areEqual(1, 6, 6));
        assertTrue(grid.areEqual(1, 7, variants17));
        assertTrue(grid.areEqual(1, 8, variants18));

        assertTrue(grid.areEqual(2, 6, 7));
        assertTrue(grid.areEqual(2, 7, variants27));
        assertTrue(grid.areEqual(2, 8, 9));
    }

    @Test
    public void testHouse10() throws Exception {
        Grid grid = new Grid(createEmptyGrid());
        int[] variants32 = new int[] { 1, 7, 8 };
        int[] variants40 = new int[] { 1, 7 };
        int[] variants42 = new int[] { 1, 7 };
        int[] variants51 = new int[] { 1, 4, 8 };
        grid.getSquare(3, 0).setOnly(3);
        grid.getSquare(3, 1).setOnly(5);
        grid.getSquare(3, 2).setOnly(variants32);
        grid.getSquare(4, 0).setOnly(variants40);
        grid.getSquare(4, 1).setOnly(9);
        grid.getSquare(4, 2).setOnly(variants42);
        grid.getSquare(5, 0).setOnly(6);
        grid.getSquare(5, 1).setOnly(variants51);
        grid.getSquare(5, 2).setOnly(2);
        System.out.println(grid.getVariants(3, 2));
        grid.solveHiddenSingle(1, 0);

        assertTrue(grid.areEqual(3, 0, 3));
        assertTrue(grid.areEqual(3, 1, 5));
        assertTrue(grid.areEqual(3, 2, variants32));

        assertTrue(grid.areEqual(4, 0, variants40));
        assertTrue(grid.areEqual(4, 1, 9));
        assertTrue(grid.areEqual(4, 2, variants42));

        assertTrue(grid.areEqual(5, 0, 6));
        // here lies the hidden single
        assertTrue(grid.areEqual(5, 1, 4));
        assertTrue(grid.areEqual(5, 2, 2));
    }

    @Test
    public void testHouse11() throws Exception {
        Grid grid = new Grid(createEmptyGrid());
        int[] variants33 = new int[] { 2, 7, 8, 9 };
        int[] variants34 = new int[] { 2, 7, 8, 9 };
        int[] variants35 = new int[] { 1, 7, 9 };
        int[] variants44 = new int[] { 2, 3, 5, 7 };
        int[] variants53 = new int[] { 3, 7, 8, 9 };
        int[] variants54 = new int[] { 3, 7, 8, 9 };
        int[] variants55 = new int[] { 1, 3, 7, 9 };
        grid.getSquare(3, 3).setOnly(variants33);
        grid.getSquare(3, 4).setOnly(variants34);
        grid.getSquare(3, 5).setOnly(variants35);
        grid.getSquare(4, 3).setOnly(6);
        grid.getSquare(4, 4).setOnly(variants44);
        grid.getSquare(4, 5).setOnly(4);
        grid.getSquare(5, 3).setOnly(variants53);
        grid.getSquare(5, 4).setOnly(variants54);
        grid.getSquare(5, 5).setOnly(variants55);
        grid.solveHiddenSingle(1, 1);

        assertTrue(grid.areEqual(3, 3, variants33));
        assertTrue(grid.areEqual(3, 4, variants34));
        assertTrue(grid.areEqual(3, 5, variants35));

        assertTrue(grid.areEqual(4, 3, 6));
        // here lies the hidden single
        assertTrue(grid.areEqual(4, 4, 5));
        assertTrue(grid.areEqual(4, 5, 4));

        assertTrue(grid.areEqual(5, 3, variants53));
        assertTrue(grid.areEqual(5, 4, variants54));
        assertTrue(grid.areEqual(5, 5, variants55));
    }

    @Test
    public void testHouse12() throws Exception {
        Grid grid = new Grid(createEmptyGrid());
        int[] variants37 = new int[] { 1, 2, 9 };
        int[] variants38 = new int[] { 1, 2, 6, 7 };
        int[] variants47 = new int[] { 1, 2, 3 };
        int[] variants48 = new int[] { 1, 2, 3, 7 };
        int[] variants57 = new int[] { 1, 3, 9 };
        int[] variants58 = new int[] { 1, 3, 7 };

        grid.getSquare(3, 6).setOnly(4);
        grid.getSquare(3, 7).setOnly(variants37);
        grid.getSquare(3, 8).setOnly(variants38);
        grid.getSquare(4, 6).setOnly(8);
        grid.getSquare(4, 7).setOnly(variants47);
        grid.getSquare(4, 8).setOnly(variants48);
        grid.getSquare(5, 6).setOnly(5);
        grid.getSquare(5, 7).setOnly(variants57);
        grid.getSquare(5, 8).setOnly(variants58);
        grid.solveHiddenSingle(1, 2);

        assertTrue(grid.areEqual(3, 6, 4));
        assertTrue(grid.areEqual(3, 7, variants37));
        // here lies the hidden single
        assertTrue(grid.areEqual(3, 8, 6));

        assertTrue(grid.areEqual(4, 6, 8));
        assertTrue(grid.areEqual(4, 7, variants47));
        assertTrue(grid.areEqual(4, 8, variants48));

        assertTrue(grid.areEqual(5, 6, 5));
        assertTrue(grid.areEqual(5, 7, variants57));
        assertTrue(grid.areEqual(5, 8, variants58));
    }

}
