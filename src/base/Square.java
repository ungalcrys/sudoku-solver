package base;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Square {
    private static final List<Integer> ALL_DIGITS = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

    // flag used to debug constructor and single candidate detection
    public static final boolean DEBUG_SINGLE_CANDIDATE = false;

    private Integer value;
    private LinkedList<Integer> variants;

    public final Point point;

    // used by grid constructor
    public Square(int row, int col, int value) {
        point = new Point(row, col);
        if (DEBUG_SINGLE_CANDIDATE)
            System.out.println("\n=== new square " + point);

        if (value == 0)
            computeVariants();
        else {
            if (DEBUG_SINGLE_CANDIDATE)
                System.out.println(point + " set value " + value);
            setValue(value, true);
        }

    }

    // used by clean house
    public Square(int row, int col, LinkedList<Integer> variants) {
        point = new Point(row, col);
        this.variants = (LinkedList<Integer>) variants.clone();
    }

    private void setValue(Integer value, boolean isCallFromInit) {
        if (!isCallFromInit)
            System.out.println(point + " set value " + value);
        this.value = value;
        variants = null;
        Grid.getInstance().cleanVariantsAround(this);
    }

    public void setValue(Integer value) {
        setValue(value, false);
    }

    public Integer getValue() {
        return value;
    }

    public LinkedList<Integer> getVariants() {
        return variants;
    }

    public void removeVariantsBut(List<Integer> variants) {
        Iterator<Integer> iterator = this.variants.iterator();
        while (iterator.hasNext()) {
            Integer nextVariant = iterator.next();
            if (!variants.contains(nextVariant)) {
                iterator.remove();
                System.out.println(point + " variants remove " + nextVariant);
            }
        }
    }

    public void removeVariant(Integer value) {
        if (variants == null) {
            return;
        }

        if (variants.remove(value)) {
            if (DEBUG_SINGLE_CANDIDATE)
                System.out.println(point + " remove variant " + value);
            if (variants.size() == 1) {
                setValue(variants.get(0));
            }
        }
    }

    private void computeVariants() {
        variants = createVariants();
        Grid.getInstance().filterVariantsByPrevalues(this);

        if (DEBUG_SINGLE_CANDIDATE) {
            System.out.print("got ");
            for (int i = 0; i < variants.size(); i += 1)
                System.out.print(variants.get(i) + " ");
            System.out.println();
        }
    }

    public boolean hasSameVariantsAs(Square square) {
        LinkedList<Integer> variants2 = square.getVariants();
        if (variants.size() != variants2.size())
            return false;
        for (Integer variant : variants) {
            if (!variants2.contains(variant))
                return false;
        }
        return true;
    }

    private static LinkedList<Integer> createVariants() {
        LinkedList<Integer> list = new LinkedList<Integer>();
        list.addAll(ALL_DIGITS);
        return list;
    }

}
