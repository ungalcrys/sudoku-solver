import java.util.LinkedList;

public class Grid {

    private Square[] squares = new Square[81];

    public Grid(int[] initial) {
        for (int i = 0; i < 81; i++) {
            squares[i] = new Square(i, initial[i]);
        }
    }

    class Square {
        LinkedList<Integer> variants = new LinkedList<Integer>();
        Integer value = null;
        int index;

        public Square(int index, int value) {
            this.index = index;

            if (value > 0) {
                this.value = value;
                checkAroundSquare();
            } else if (value == 0) {
                computeVariants();
            }
        }

        private void checkAroundSquare() {
            removeVariantsOnRow();
            removeVariantsOnColumn();
            removeVariantsOnBigSquare();
        }

        private void removeVariant(Integer value) {
            variants.removeFirstOccurrence(value);
            if (variants.size() == 1) {
                this.value = variants.get(0);
                variants = null;
            }
        }

        /**
         * Removes current value from all squares variants in the row.
         */
        public void removeVariantsOnRow() {
            int rowIndex = index / 9;
            for (int i = rowIndex * 9; i < (rowIndex + 1) * 9; i += 1) {
                if (i != index && squares[i] != null)
                    squares[i].removeVariant(value);
            }
        }

        /**
         * Removes current value from all squares variants in the column.
         */
        public void removeVariantsOnColumn() {
            int firstRowOnColumn = index % 9;
            for (int i = firstRowOnColumn; i < firstRowOnColumn + 72 + 1; i += 9) {
                if (i != index && squares[i] != null)
                    squares[i].removeVariant(value);
            }
        }

        /**
         * Removes current value from all squares variants in the big square.
         */
        public void removeVariantsOnBigSquare() {
            int lineIndex = index / 9 / 3;
            for (int i = lineIndex * 3; i < (lineIndex + 1) * 3; i += 1) {
                int colIndex = i % 9 / 3;
                for (int j = colIndex * 3; j < (colIndex + 1) * 3; j += 9) {
                    
                }
            }
        }

        private void computeVariants() {
            // TODO Auto-generated method stub
        }
    }

}
