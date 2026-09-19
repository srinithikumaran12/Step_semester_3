package string.assigment_problems;

/**
 * Assignment 5: The Movie Review Word Length Profiler
 * Scenario: Classify each word in a review as Short (1-4 letters),
 * Medium (5-8 letters), or Long (9+ letters), and count each category.
 */
public class MovieReviewWordLengthProfiler {

    void classifyWordLengths(String review) {
        String[] words = review.split(" ");

        int shortCount = 0;
        int mediumCount = 0;
        int longCount = 0;

        for (String word : words) {
            int len = word.length();
            if (len <= 4) {
                shortCount++;
            } else if (len <= 8) {
                mediumCount++;
            } else {
                longCount++;
            }
        }

        System.out.println("Short: " + shortCount + " | Medium: " + mediumCount + " | Long: " + longCount);
    }

    public static void main(String[] args) {
        MovieReviewWordLengthProfiler profiler = new MovieReviewWordLengthProfiler();

        profiler.classifyWordLengths("This movie was absolutely fantastic and thrilling");
        // Short: 3 | Medium: 1 | Long: 3
    }
}