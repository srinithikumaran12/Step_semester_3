package datastructures.assigment_problems;

import java.util.Arrays;


public class TokenQueueMerger {

    // A null list is treated as an empty one
    static int[] mergeTokens(int[] counterA, int[] counterB) {
        int[] a = (counterA == null) ? new int[0] : counterA;
        int[] b = (counterB == null) ? new int[0] : counterB;

        int[] merged = new int[a.length + b.length];
        int i = 0; // pointer into counterA
        int j = 0; // pointer into counterB
        int k = 0; // next free slot in merged

        while (i < a.length && j < b.length) {
            if (a[i] <= b[j]) {
                merged[k++] = a[i++];
            } else {
                merged[k++] = b[j++];
            }
        }

        // One list is exhausted - copy whatever remains of the other
        while (i < a.length) {
            merged[k++] = a[i++];
        }
        while (j < b.length) {
            merged[k++] = b[j++];
        }

        return merged;
    }

    public static void main(String[] args) {
        int[] counterA = {3, 8, 15, 20};
        int[] counterB = {5, 8, 12};
        System.out.println(Arrays.toString(mergeTokens(counterA, counterB)));
        // [3, 5, 8, 8, 12, 15, 20]

        System.out.println(Arrays.toString(mergeTokens(new int[]{}, new int[]{4, 9})));
        // [4, 9]
    }
}