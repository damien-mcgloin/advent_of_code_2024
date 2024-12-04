package advent_2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class day_four {

    public static int count = 0;

    public static int count2 = 0;

    public static String fileName = "src/advent_2024/dayFour_file.txt";

    public static void problemTwo() {
        try {
            // Read the grid from file
            String grid = readFile(fileName);

            // Find all X-shaped matches in the grid
            List<String> xShapes = findXShapes(grid);

            // Output the results
            System.out.println("Found " + xShapes.size() + " X-shapes:");
            for (String xShape : xShapes) {
                System.out.println(xShape);
                count2+=1;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Function to read the file into a string (grid)
    private static String readFile(String filePath) throws IOException {
        StringBuilder grid = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = br.readLine()) != null) {
            grid.append(line);
        }

        return grid.toString();
    }

    // Function to find all X-shaped occurrences of MAS and SAM in the grid
    private static List<String> findXShapes(String grid) {
        List<String> xShapes = new ArrayList<>();
        int rows = (int) Math.sqrt(grid.length());  // Assume it's a square grid for simplicity

        // Loop through each point in the grid as the potential center of an X shape
        for (int i = 0; i < grid.length(); i++) {
            // Find the row and column for the current index in a square grid
            int row = i / rows;
            int col = i % rows;

            // Ensure that there are enough characters to form diagonals
            if (row > 0 && row < rows - 1 && col > 0 && col < rows - 1) {
                // Check for diagonals forming the X shape:
                // Diagonal 1: (row-1, col-1), (row, col), (row+1, col+1)
                // Diagonal 2: (row-1, col+1), (row, col), (row+1, col-1)
                String diagonal1 = "" + grid.charAt(i - rows - 1) + grid.charAt(i) + grid.charAt(i + rows + 1);
                String diagonal2 = "" + grid.charAt(i - rows + 1) + grid.charAt(i) + grid.charAt(i + rows - 1);

                // Check if both diagonals are "MAS" or "SAM"
                if (isMASOrSAM(diagonal1) && isMASOrSAM(diagonal2)) {
                    xShapes.add("Found X-shape at row " + row + ", col " + col + ": " + diagonal1 + " and " + diagonal2);
                }
            }
        }

        return xShapes;
    }

    // Function to check if a string is "MAS" or "SAM" in any order
    private static boolean isMASOrSAM(String str) {
        return str.equals("MAS") || str.equals("SAM");
    }

    public static void main(String[] args) throws IOException {
        problemOne();
        problemTwo();
        System.out.println(count);
        System.out.println(count2);
    }

    public static void problemOne() {
        char[][] grid = readGridFromFile(fileName);

        if (grid != null) {
            String word = "XMAS";
            String reverseWord = new StringBuilder(word).reverse().toString(); // "SAMX"
            int wordLength = word.length();

            // Search for the word in all directions (forward and backward)
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    // Check for the word "XMAS" and its reverse "SAMX"
                    if (grid[i][j] == word.charAt(0) || grid[i][j] == reverseWord.charAt(0)) {
                        // Check horizontally (right direction)
                        if (j + wordLength - 1 < grid[i].length) {
                            if (checkHorizontal(grid, i, j, word)) {
                                System.out.println("Found '" + word + "' horizontally at: (" + i + ", " + j + ")");
                                count+=1;
                            }
                            if (checkHorizontal(grid, i, j, reverseWord)) {
                                System.out.println("Found '" + reverseWord + "' horizontally at: (" + i + ", " + j + ")");
                                count+=1;
                            }
                        }

                        // Check vertically (down direction)
                        if (i + wordLength - 1 < grid.length) {
                            if (checkVertical(grid, i, j, word)) {
                                System.out.println("Found '" + word + "' vertically at: (" + i + ", " + j + ")");
                                count+=1;
                            }
                            if (checkVertical(grid, i, j, reverseWord)) {
                                System.out.println("Found '" + reverseWord + "' vertically at: (" + i + ", " + j + ")");
                                count+=1;
                            }
                        }

                        // Check diagonal (down-right direction)
                        if (i + wordLength - 1 < grid.length && j + wordLength - 1 < grid[i].length) {
                            if (checkDiagonalDownRight(grid, i, j, word)) {
                                System.out.println("Found '" + word + "' diagonally (down-right) at: (" + i + ", " + j + ")");
                                count+=1;
                            }
                            if (checkDiagonalDownRight(grid, i, j, reverseWord)) {
                                System.out.println("Found '" + reverseWord + "' diagonally (down-right) at: (" + i + ", " + j + ")");
                                count+=1;
                            }
                        }

                        // Check diagonal (down-left direction)
                        if (i + wordLength - 1 < grid.length && j - wordLength + 1 >= 0) {
                            if (checkDiagonalDownLeft(grid, i, j, word)) {
                                System.out.println("Found '" + word + "' diagonally (down-left) at: (" + i + ", " + j + ")");
                                count+=1;
                            }
                            if (checkDiagonalDownLeft(grid, i, j, reverseWord)) {
                                System.out.println("Found '" + reverseWord + "' diagonally (down-left) at: (" + i + ", " + j + ")");
                                count+=1;
                            }
                        }
                    }
                }
            }
        } else {
            System.out.println("Error reading the grid from the file.");
        }
    }

    // Read the grid from the file
    private static char[][] readGridFromFile(String fileName) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        // Convert the list of strings to a 2D char array
        int rows = lines.size();
        int cols = lines.get(0).length();
        char[][] grid = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            grid[i] = lines.get(i).toCharArray();
        }

        return grid;
    }

    // Check horizontally (right direction)
    private static boolean checkHorizontal(char[][] grid, int row, int col, String word) {
        for (int i = 0; i < word.length(); i++) {
            if (grid[row][col + i] != word.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    // Check vertically (down direction)
    private static boolean checkVertical(char[][] grid, int row, int col, String word) {
        for (int i = 0; i < word.length(); i++) {
            if (grid[row + i][col] != word.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    // Check diagonally (down-right direction)
    private static boolean checkDiagonalDownRight(char[][] grid, int row, int col, String word) {
        for (int i = 0; i < word.length(); i++) {
            if (grid[row + i][col + i] != word.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    // Check diagonally (down-left direction)
    private static boolean checkDiagonalDownLeft(char[][] grid, int row, int col, String word) {
        for (int i = 0; i < word.length(); i++) {
            if (grid[row + i][col - i] != word.charAt(i)) {
                return false;
            }
        }
        return true;
    }
}


