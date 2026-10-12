package datastructures.class_problems;

import java.util.HashSet;
import java.util.Set;


public class PairWithTargetSum {

    static boolean hasPairBruteForce(int[] nums, int target) {
        if (nums == null) {
            return false;
        }
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if ((long) nums[i] + nums[j] == target) { // long avoids int overflow
                    return true;
                }
            }
        }
        return false;
    }

    static boolean hasPairWithSum(int[] nums, int target) {
        if (nums == null) {
            return false;
        }

        Set<Integer> seen = new HashSet<>();
        for (int num : nums) {
            long complement = (long) target - num;

            // A complement outside the int range can never be in the set
            boolean inIntRange = complement >= Integer.MIN_VALUE && complement <= Integer.MAX_VALUE;
            if (inIntRange && seen.contains((int) complement)) {
                return true;
            }

            seen.add(num); // added AFTER the check, so a number never pairs with itself
        }
        return false;
    }

    public static void main(String[] args) {
        int[] example1 = {2, 7, 11, 15};
        int[] example2 = {3, 4, 6};

        System.out.println(hasPairWithSum(example1, 9));         // true  (2 + 7)
        System.out.println(hasPairWithSum(example2, 20));        // false

        System.out.println(hasPairBruteForce(example1, 9));      // true
        System.out.println(hasPairBruteForce(example2, 20));     // false

        System.out.println(hasPairWithSum(new int[]{3, 3}, 6));  // true  (duplicates at two positions)
        System.out.println(hasPairWithSum(new int[]{3}, 6));     // false (one element cannot pair with itself)
    }
}