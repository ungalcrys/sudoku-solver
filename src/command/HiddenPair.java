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
    }

    @Override
    public void createRawMap(int row, int col) {
        Square square = grid.getSquare(row, col);
        LinkedList<Integer> variants = square.getVariants();
        if (variants != null) {
            Point point = square.getPoint();
            for (Integer variant : variants) {
                List<Point> list = map.get(variant);
                if (list == null) {
                    list = new ArrayList<Point>();
                    list.add(point);
                    map.put(variant, list);
                } else
                    list.add(point);
            }
        }
    }

    @Override
    public void clean() {
        // for (int row = startRow; row < endRow; row += 1) {
        // for (int col = startCol; col < endCol; col += 1) {
        // Square square = grid.getSquare(row, col);
        // if (square.getRow() != row || square.getCol() != col) {
        // LinkedList<Integer> variants = square.getVariants();
        // if (variants != null) {
        // // for(int i=0; i<points.get(0))
        // // variants.remove(points.get)
        // }
        // }
        // }
        // }

        Iterator<Entry<Integer, List<Point>>> iterator = map.entrySet().iterator();
        ArrayList<Integer> variants = new ArrayList<Integer>(2);
        while (iterator.hasNext()) {
            Entry<Integer, List<Point>> next = iterator.next();
            List<Point> points = next.getValue();
            if (points.size() != 2) {
                iterator.remove();
            } else {
                Integer key = next.getKey();
                variants.add(key);
                System.out.println("found key " + key + " at " + points);
                if (variants.size() == 2) {
                    for (Point point : points) {
                        Square square = grid.getSquare(point.getRow(), point.getCol());
                        for (Integer variant : square.getVariants()) {
                            if (variant != variants.get(0) && variant == variants.get(1))
                                square.removeVariant(grid, variant);
                        }
                    }
                }
            }
        }

    }

}
