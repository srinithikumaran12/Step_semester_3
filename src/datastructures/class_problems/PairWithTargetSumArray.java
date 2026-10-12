package datastructures.class_problems;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PairWithTargetSumArray {

    // Approach 1
    static boolean hasPairBruteForce(int[] nums, int target) {
        if (nums == null) {
            return false;
        }
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if ((long) nums[i] + nums[j] == target) {
                    return true;
                }
            }
        }
        return false;
    }

    // Approach 2
    static boolean hasPairSortedTwoPointers(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return false;
        }

        int[] sorted = nums.clone(); // keep the caller's array untouched
        Arrays.sort(sorted);

        int left = 0;
        int right = sorted.length - 1;

        while (left < right) { // left < right keeps the two elements at different positions
            long sum = (long) sorted[left] + sorted[right];

            if (sum == target) {
                return true;
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        return false;
    }

    // Approach 3
    static boolean hasPairHashSet(int[] nums, int target) {
        if (nums == null) {
            return false;
        }

        Set<Integer> seen = new HashSet<>();
        for (int num : nums) {
            long complement = (long) target - num;
            boolean inIntRange = complement >= Integer.MIN_VALUE && complement <= Integer.MAX_VALUE;

            if (inIntRange && seen.contains((int) complement)) {
                return true;
            }
            seen.add(num);
        }
        return false;
    }

    public static void main(String[] args) {
        int[] sample1 = {2, 7, 11, 15};
        int[] sample2 = {3, 4, 6};

        System.out.println(hasPairBruteForce(sample1, 9));         // true  (2 + 7)
        System.out.println(hasPairBruteForce(sample2, 20));        // false

        System.out.println(hasPairSortedTwoPointers(sample1, 9));  // true
        System.out.println(hasPairSortedTwoPointers(sample2, 20)); // false

        System.out.println(hasPairHashSet(sample1, 9));            // true
        System.out.println(hasPairHashSet(sample2, 20));           // false

        // Negatives and duplicates
        System.out.println(hasPairSortedTwoPointers(new int[]{-5, 8, 3, -5}, -10)); // true (-5 + -5)
    }
}