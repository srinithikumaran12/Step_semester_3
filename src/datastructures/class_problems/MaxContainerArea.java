package datastructures.class_problems;


public class MaxContainerArea {

    // Returns long because min(height) * width can exceed the int range
    static long maxContainerAreaBruteForce(int[] heights) {
        if (heights == null || heights.length < 2) {
            return 0;
        }

        long best = 0;
        for (int i = 0; i < heights.length; i++) {
            for (int j = i + 1; j < heights.length; j++) {
                long area = (long) Math.min(heights[i], heights[j]) * (j - i);
                best = Math.max(best, area);
            }
        }
        return best;
    }

    static long maxContainerArea(int[] heights) {
        if (heights == null || heights.length < 2) {
            return 0;
        }

        int left = 0;
        int right = heights.length - 1;
        long best = 0;

        while (left < right) {
            long area = (long) Math.min(heights[left], heights[right]) * (right - left);
            best = Math.max(best, area);

            if (heights[left] < heights[right]) {
                left++;   // the shorter wall limits the area, so try a taller one
            } else {
                right--;
            }
        }
        return best;
    }

    public static void main(String[] args) {
        int[] heights = {1, 8, 6, 2, 5, 4, 8, 3, 7};

        System.out.println(maxContainerArea(heights));           // 49
        System.out.println(maxContainerAreaBruteForce(heights)); // 49
    }
}