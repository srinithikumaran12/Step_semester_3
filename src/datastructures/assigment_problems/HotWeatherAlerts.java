package datastructures.assigment_problems;


public class HotWeatherAlerts {

    private static void checkInput(int[] readings, int k) {
        if (readings == null) {
            throw new IllegalArgumentException("readings must not be null");
        }
        if (k < 1 || k > readings.length) {
            throw new IllegalArgumentException("k must be between 1 and " + readings.length);
        }
    }

    // Recalculates each block's sum from scratch
    static int countAlertsBruteForce(int[] readings, int k, int threshold) {
        checkInput(readings, k);

        long needed = (long) k * threshold;
        int alerts = 0;

        for (int start = 0; start + k <= readings.length; start++) {
            long sum = 0;
            for (int i = start; i < start + k; i++) {
                sum += readings[i];
            }
            if (sum >= needed) {
                alerts++;
            }
        }
        return alerts;
    }

    // Sliding window
    static int countAlerts(int[] readings, int k, int threshold) {
        checkInput(readings, k);

        long needed = (long) k * threshold;
        long windowSum = 0;

        // Sum of the first block
        for (int i = 0; i < k; i++) {
            windowSum += readings[i];
        }

        int alerts = (windowSum >= needed) ? 1 : 0;

        // Slide one hour at a time: add the new reading, drop the one that left
        for (int i = k; i < readings.length; i++) {
            windowSum += readings[i];
            windowSum -= readings[i - k];

            if (windowSum >= needed) {
                alerts++;
            }
        }
        return alerts;
    }

    public static void main(String[] args) {
        int[] readings = {2, 2, 2, 2, 5, 5, 5, 8};

        System.out.println(countAlerts(readings, 3, 4));           // 3  - [2,5,5], [5,5,5], [5,5,8]
        System.out.println(countAlertsBruteForce(readings, 3, 4)); // 3
    }
}