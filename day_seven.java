package advent_2024;

import java.io.*;
import java.util.*;

public class day_seven {

    public static List<long[]> valuesToCheck = new ArrayList<>();

    public static char[] operators = {'+', '*'};

    public static long extraCount = 0;

    public static String filePath = "src/advent_2024/daySeven_file.txt";


    public static void problemOne() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;

            // Read all lines from the file
            while ((line = br.readLine()) != null) {
                String[] stringArray = line.replace(":", "").split(" ");
                long[] arrayOfValues = new long[stringArray.length];

                // Parse values into long[]
                for (int i = 0; i < stringArray.length; i++) {
                    arrayOfValues[i] = Long.parseLong(stringArray[i]);
                }
                valuesToCheck.add(arrayOfValues);
            }

            // Process each equation
            for (long[] values : valuesToCheck) {
                // Generate all unique permutations of operators
                List<int[]> permutationsToCheck = generateBinaryPermutations(values.length - 1);
                // Check current array of values against all permutations
                iterations(values, permutationsToCheck);
            }
        }
    }

    public static void iterations(long[] values, List<int[]> permutationsToCheck) {
        boolean found = false; // Flag to check if the target value has been found

        // For each permutation of operators
        for (int[] permutation : permutationsToCheck) {
            if (found) break; // If already found, exit the outer loop

            // Start with values[1], not values[0], because values[0] is the target
            long count = values[1];

            // Apply the operators from the permutation, starting from values[1] to the last value
            for (int i = 1; i < values.length - 1; i++) {
                char operator = operators[permutation[i - 1]]; // Get the operator from the permutation array

                // Apply the operator between the current count and the next value
                count = applyOperator(count, values[i + 1], operator);

                // If the result matches the values[0]
                if (count == values[0]) {
                    extraCount += count;
                    System.out.println("Match found! Test value: " + values[0] + " Result: " + count);
                    found = true; // Set flag to true when match is found
                    break; // Exit the loop as soon as the match is found
                }
            }
        }
    }

    // Generate all binary permutations of length `n` (i.e., operators)
    public static List<int[]> generateBinaryPermutations(int n) {
        List<int[]> permutations = new ArrayList<>();
        int numPermutations = (int) Math.pow(2, n);  // 2^n possible permutations of + and *

        // Generate all combinations of binary values (0 for +, 1 for *)
        for (int i = 0; i < numPermutations; i++) {
            int[] binaryArray = new int[n];
            for (int j = 0; j < n; j++) {
                binaryArray[j] = (i >> (n - j - 1)) & 1; // Extract each bit
            }
            permutations.add(binaryArray);
        }

        return permutations;
    }

    // Apply an operator between two numbers
    public static long applyOperator(long a, long b, char operator) {
        return switch (operator) {
            case '+' -> a + b;
            case '*' -> a * b;
            default -> throw new IllegalArgumentException("Unsupported operator: " + operator);
        };
    }

    public static void main(String... args) throws IOException {
        problemOne();
        System.out.println("\nThe total is: " + extraCount);
    }
}

