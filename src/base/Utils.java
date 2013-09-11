package base;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Utils {
    private static final List<Integer> ALL_DIGITS = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

    public static LinkedList<Integer> createVariants() {
        LinkedList<Integer> list = new LinkedList<Integer>();
        list.addAll(ALL_DIGITS);
        return list;
    }

    public static char getChar(int y) {
        return (char) (65 + y);
    }
}
