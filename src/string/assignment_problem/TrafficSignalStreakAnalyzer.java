package string.assigment_problems;

/**
 * Assignment 3: The Traffic Signal Streak Analyzer
 * Scenario: Scan a day's signal log and report the longest continuous
 * streak of the same color, to flag a possibly malfunctioning signal.
 */
public class TrafficSignalStreakAnalyzer {

    void findLongestStreak(String signalLog) {
        char longestColor = signalLog.charAt(0);
        int longestLength = 1;

        char currentColor = signalLog.charAt(0);
        int currentLength = 1;

        for (int i = 1; i < signalLog.length(); i++) {
            if (signalLog.charAt(i) == currentColor) {
                currentLength++;
            } else {
                currentColor = signalLog.charAt(i);
                currentLength = 1;
            }

            if (currentLength > longestLength) {
                longestLength = currentLength;
                longestColor = currentColor;
            }
        }

        System.out.println("Longest Streak: '" + longestColor + "' repeated " + longestLength + " times");
    }

    public static void main(String[] args) {
        TrafficSignalStreakAnalyzer analyzer = new TrafficSignalStreakAnalyzer();

        analyzer.findLongestStreak("RRGGGYRR");
        // Longest Streak: 'G' repeated 3 times

        analyzer.findLongestStreak("RRRRYYGG");
        // Longest Streak: 'R' repeated 4 times
    }
}