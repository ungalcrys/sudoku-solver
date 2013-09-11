package command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import base.Grid;
import base.Point;
import base.Square;

public class HiddenPair extends HouseCommand {

    public HiddenPair(Grid grid, int hr, int hc) {
        super(grid, hr, hc);
        System.out.println("==");
    }

    @Override
    public void addToRawMap(int row, int col) {
        Square square = grid.getSquare(row, col);
        LinkedList<Integer> variants = square.getVariants();
        if (variants != null) {
            Point point = square.getPoint();
            for (Integer variant : variants) {
                ArrayList<Point> list = map[variant];
                if (list == null) {
                    list = new ArrayList<Point>();
                    list.add(point);
                    map[variant] = list;
                } else
                    list.add(point);
            }
        }
    }

    @Override
    public void clean() {
        for (int i = 1; i < 10; i += 1) {
            ArrayList<Point> points = map[i];
            if (points != null && points.size() == 2) {
                System.out.println("found " + i + " at " + points);
                for (int j = i + 1; j < 10; j += 1) {
                    if (map[j] != null && areSameLists(points, map[j])) {
                        System.out.println("found " + j + " at " + map[j]);
                        System.out.println("> " + i + " and " + j + " at " + points);
                        map[j] = null;
                        for (Point point : points) {
                            grid.getSquare(point.getRow(), point.getCol()).removeVariantsBut(
                                    Arrays.asList(i, j));
                        }
                        break;
                    }
                }
            }
        }
    }

    private boolean areSameLists(ArrayList<Point> list1, ArrayList<Point> list2) {
        int size = list1.size();
        if (size != list2.size())
            return false;

        for (int i = 0; i < size; i += 1)
            if (list1.get(i) != list2.get(i))
                return false;

        return true;
    }

    @Override
    public void solve() {
        // Iterator<Entry<Integer, List<Point>>> iterator = map.entrySet().iterator();
        // ArrayList<Integer> variants = new ArrayList<Integer>(2);
        // while (iterator.hasNext()) {
        //
        // }
    }
}
