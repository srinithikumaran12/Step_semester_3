package datastructures.assigment_problems;

import java.util.HashMap;
import java.util.Map;


public class MostPopularOrder {

    static class Result {
        private final String item;
        private final int count;

        Result(String item, int count) {
            this.item = item;
            this.count = count;
        }

        String getItem() {
            return item;
        }

        int getCount() {
            return count;
        }

        @Override
        public String toString() {
            String shownItem = (item == null) ? "null" : "\"" + item + "\"";
            return "(" + shownItem + ", " + count + ")";
        }
    }

    // Approach 1
    // For an empty list there is no item, so the result is (null, 0)
    static Result mostPopularBruteForce(String[] orders) {
        String bestItem = null;
        int bestCount = 0;

        if (orders != null) {
            for (int i = 0; i < orders.length; i++) {
                int count = 0;
                for (int j = 0; j < orders.length; j++) {
                    if (orders[i].equals(orders[j])) {
                        count++;
                    }
                }

                if (count > bestCount) { // strict ">" keeps the earlier item on ties
                    bestCount = count;
                    bestItem = orders[i];
                }
            }
        }

        return new Result(bestItem, bestCount);
    }

    // Approach 2
    static Result mostPopular(String[] orders) {
        if (orders == null || orders.length == 0) {
            return new Result(null, 0);
        }

        // Pass 1: count every item
        Map<String, Integer> counts = new HashMap<>();
        for (String order : orders) {
            counts.put(order, counts.getOrDefault(order, 0) + 1);
        }

        // Pass 2: walk the ORIGINAL order, so the first item to reach the highest count wins ties
        String bestItem = null;
        int bestCount = 0;
        for (String order : orders) {
            int count = counts.get(order);
            if (count > bestCount) {
                bestCount = count;
                bestItem = order;
            }
        }

        return new Result(bestItem, bestCount);
    }

    public static void main(String[] args) {
        String[] sample1 = {"dosa", "idli", "vada", "dosa", "idli", "dosa", "tea"};
        String[] sample2 = {"tea", "coffee", "coffee", "tea"};

        System.out.println(mostPopular(sample1));           // ("dosa", 3)
        System.out.println(mostPopular(sample2));           // ("tea", 2)

        System.out.println(mostPopularBruteForce(sample1)); // ("dosa", 3)
        System.out.println(mostPopularBruteForce(sample2)); // ("tea", 2)
    }
}