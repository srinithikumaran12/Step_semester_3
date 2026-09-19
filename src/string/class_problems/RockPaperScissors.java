package string.class_problems;
import java.util.Random;
import java.util.Scanner;

public class RockPaperScissors {
    public static String playRound(String playerMove, String computerMove) {

        if (playerMove.equalsIgnoreCase(computerMove)) {
            return "Draw";
        }

        if ((playerMove.equalsIgnoreCase("Rock") && computerMove.equalsIgnoreCase("Scissors")) ||
                (playerMove.equalsIgnoreCase("Paper") && computerMove.equalsIgnoreCase("Rock")) ||
                (playerMove.equalsIgnoreCase("Scissors") && computerMove.equalsIgnoreCase("Paper"))) {
            return "Player Wins";
        }

        return "Computer Wins";
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        String[] moves = {"Rock", "Paper", "Scissors"};

        int wins = 0;
        int losses = 0;
        int draws = 0;
        int rounds = 5;

        String[][] summary = new String[rounds][4];

        for (int i = 0; i < rounds; i++) {

            System.out.println("Round " + (i + 1));
            System.out.print("Enter your move (Rock/Paper/Scissors): ");
            String playerMove = sc.next();

            String computerMove = moves[random.nextInt(3)];

            String result = playRound(playerMove, computerMove);

            System.out.println("Computer Move: " + computerMove);
            System.out.println(result);
            System.out.println();

            summary[i][0] = "Round " + (i + 1);
            summary[i][1] = playerMove;
            summary[i][2] = computerMove;
            summary[i][3] = result;

            if (result.equals("Player Wins"))
                wins++;
            else if (result.equals("Computer Wins"))
                losses++;
            else
                draws++;
        }

        double winPercentage = (wins * 100.0) / rounds;

        System.out.println("========== Final Summary ==========");
        System.out.printf("%-8s %-12s %-15s %-15s%n",
                "Round", "Player", "Computer", "Result");

        for (int i = 0; i < rounds; i++) {
            System.out.printf("%-8s %-12s %-15s %-15s%n",
                    summary[i][0],
                    summary[i][1],
                    summary[i][2],
                    summary[i][3]);
        }

        System.out.println("\nWins: " + wins);
        System.out.println("Losses: " + losses);
        System.out.println("Draws: " + draws);
        System.out.printf("Win Percentage: %.1f%%\n", winPercentage);

        sc.close();
    }
}
