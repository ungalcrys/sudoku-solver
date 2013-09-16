package command;

import java.util.ArrayList;
import java.util.LinkedList;

import base.Grid;
import base.Square;

public class HiddenSingleSolver extends HouseSolver {

    public HiddenSingleSolver() {
        if (DEBUG_HIDDEN_SINGLE) {
            System.out.println("\n==solveHiddenSingles");
        }
    }

    @Override
    boolean isHouseQualified(int r, int c, ArrayList<Integer> checkedVariants) {
        Square square = Grid.getInstance().getSquare(r, c);
        LinkedList<Integer> variants = square.getVariants();
        if (variants == null)
            return false;

        if (DEBUG_HIDDEN_SINGLE)
            System.out.println("\n=check " + square.point);
        for (Integer variant : variants) {
            // skip if this digit has been checked before
            if (checkedVariants.contains(variant))
                continue;

            if (DEBUG_HIDDEN_SINGLE)
                System.out.println("variant " + variant);

            int startCol = getStartCol();
            int endCol = getEndCol();
            int endRow = getEndRow();

            // check if variant exists in next unsolved squares
            // check rest of the first line
            int c2 = 0;
            for (c2 = c + 1; c2 < endCol; c2 += 1) {
                if (isVariantIn(r, c2, variant, checkedVariants))
                    break;
            }

            // variant found
            if (c2 < endCol)
                continue;

            // check the next lines
            outerloop: for (int r2 = r + 1; r2 < endRow; r2 += 1) {
                for (c2 = startCol; c2 < endCol; c2 += 1) {
                    if (isVariantIn(r2, c2, variant, checkedVariants))
                        break outerloop;
                }
            }

            // variant found => unique variant (hidden single) was found
            if (c2 >= endCol) {
                square.setValue(variant);
                return true;
            }
        }
        return false;
    }

}
