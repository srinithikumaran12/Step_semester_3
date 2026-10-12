package datastructures.class_problems;


public class WarehouseGridSummary {

    // Java has no tuples, so the (total_items, (row, col)) result gets a small class
    static class Summary {
        private final long totalItems;
        private final int maxRow;
        private final int maxCol;

        Summary(long totalItems, int maxRow, int maxCol) {
            this.totalItems = totalItems;
            this.maxRow = maxRow;
            this.maxCol = maxCol;
        }

        long getTotalItems() {
            return totalItems;
        }

        int getMaxRow() {
            return maxRow;
        }

        int getMaxCol() {
            return maxCol;
        }

        @Override
        public String toString() {
            return "(" + totalItems + ", (" + maxRow + ", " + maxCol + "))";
        }
    }

    // For an empty grid there is no bin, so the coordinate is reported as (-1, -1)
    static Summary warehouseSummary(int[][] grid) {
        long total = 0;
        int maxValue = -1; // bins are non-negative, so the first bin always becomes the first maximum
        int maxRow = -1;
        int maxCol = -1;

        if (grid != null) {
            for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid[row].length; col++) {
                    int items = grid[row][col];
                    total += items;

                    if (items > maxValue) { // strict ">" keeps the first maximum on ties
                        maxValue = items;
                        maxRow = row;
                        maxCol = col;
                    }
                }
            }
        }

        return new Summary(total, maxRow, maxCol);
    }

    public static void main(String[] args) {
        int[][] grid = {
                {4, 9, 2},
                {7, 1, 6},
                {3, 12, 5}
        };

        System.out.println(warehouseSummary(grid)); // (49, (2, 1))
    }
}