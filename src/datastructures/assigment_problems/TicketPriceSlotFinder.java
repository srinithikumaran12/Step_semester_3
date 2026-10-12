package datastructures.assigment_problems;


public class TicketPriceSlotFinder {

    // Linear scan, for comparison
    static int findSlotLinear(int[] prices, int newPrice) {
        if (prices == null) {
            return 0;
        }
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] >= newPrice) {
                return i;
            }
        }
        return prices.length;
    }

    // Binary search
    static int findSlot(int[] prices, int newPrice) {
        if (prices == null) {
            return 0;
        }

        int low = 0;
        int high = prices.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2; // avoids int overflow of (low + high)

            if (prices[mid] == newPrice) {
                return mid;          // already present
            } else if (prices[mid] < newPrice) {
                low = mid + 1;       // slot is in the right half
            } else {
                high = mid - 1;      // slot is in the left half
            }
        }

        return low; // insertion point
    }

    public static void main(String[] args) {
        int[] prices = {120, 150, 200, 260};

        System.out.println(findSlot(prices, 150)); // 1  (already present at index 1)
        System.out.println(findSlot(prices, 210)); // 3
        System.out.println(findSlot(prices, 300)); // 4

        System.out.println(findSlotLinear(prices, 150)); // 1
        System.out.println(findSlotLinear(prices, 210)); // 3
        System.out.println(findSlotLinear(prices, 300)); // 4
    }
}