package datastructures.assigment_problems;


public class ClassTopperFinder {

    // Java has no tuples, so the (rowIndex, total) result gets a small class
    static class Result {
        private final int rowIndex;
        private final int total;

        Result(int rowIndex, int total) {
            this.rowIndex = rowIndex;
            this.total = total;
        }

        int getRowIndex() {
            return rowIndex;
        }

        int getTotal() {
            return total;
        }

        @Override
        public String toString() {
            return "(" + rowIndex + ", " + total + ")";
        }
    }

    // For an empty grid there is no student, so the row index is reported as -1
    static Result findTopper(int[][] marks) {
        int bestRow = -1;
        int bestTotal = -1; // marks are non-negative, so the first student always becomes the first best

        if (marks != null) {
            for (int row = 0; row < marks.length; row++) {
                int total = 0;
                for (int col = 0; col < marks[row].length; col++) {
                    total += marks[row][col];
                }

                if (total > bestTotal) { // strict ">" keeps the smallest row index on ties
                    bestTotal = total;
                    bestRow = row;
                }
            }
        }

        return new Result(bestRow, Math.max(bestTotal, 0));
    }

    public static void main(String[] args) {
        int[][] marks = {
                {78, 85, 90},
                {88, 92, 79},
                {65, 70, 95}
        };

        System.out.println(findTopper(marks)); // (1, 259)  - totals are 253, 259, 230
    }
}