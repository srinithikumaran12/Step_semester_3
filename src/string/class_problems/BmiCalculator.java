package string.class_problems;

import java.util.Scanner;

public class BmiCalculator {

    public static String getBmiStatus(double bmi) {

        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi < 25) {
            return "Normal";
        } else if (bmi < 30) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }

    public static void printWellnessReport(double[] heights, double[] weights) {

        System.out.println("\n------------------------------------------------");
        System.out.println("Person\tHeight(m)\tWeight(kg)\tBMI\tStatus");
        System.out.println("------------------------------------------------");

        for (int i = 0; i < heights.length; i++) {

            double bmi = weights[i] / (heights[i] * heights[i]);

            String status = getBmiStatus(bmi);

            System.out.printf("%d\t%.2f\t\t%.2f\t\t%.2f\t%s%n",
                    i + 1, heights[i], weights[i], bmi, status);
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of people: ");
        int numberOfPeople = scanner.nextInt();

        double[] heights = new double[numberOfPeople];
        double[] weights = new double[numberOfPeople];

        for (int i = 0; i < numberOfPeople; i++) {

            System.out.print("Enter height for Person " + (i + 1) + " (m): ");
            heights[i] = scanner.nextDouble();

            System.out.print("Enter weight for Person " + (i + 1) + " (kg): ");
            weights[i] = scanner.nextDouble();
        }

        printWellnessReport(heights, weights);

        scanner.close();
    }
}