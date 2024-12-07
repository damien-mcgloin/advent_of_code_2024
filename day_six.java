package advent_2024;

import java.io.*;
import java.util.*;

public class day_six {

    public static String filePath = "src/advent_2024/daySix_file.txt";

    public static List<char[]> grid = new ArrayList<>();

    public static List<int[]> playerPosition = new ArrayList<>();

    public static Set<Integer> uniquePositions = new HashSet<>();

    public static void problemOne() throws FileNotFoundException {
        Scanner s = new Scanner(new File(filePath));
        while (s.hasNext()) {
            String line = s.next();
            grid.add(line.toCharArray());
        }

        for (char[] line : grid) {
            for (int index = 0; index < line.length; index++) {
                if (line[index] == '^') {
                    int[] array = {grid.indexOf(line), index};
                    playerPosition.add(0, array);
                }
            }
        }
    }

    public static void goForward() {
        int[] position = playerPosition.get(0);
        int line = position[0];
        int index = position[1];

        for(int i = line; i >= 0; i--) {
            char[] check = grid.get(i);
            if(check[index] != '#') {
                int[] newPosition = {grid.indexOf(check), position[1]};
                uniquePositions.add(newPosition[0] * 1000 + newPosition[1]);
                playerPosition.set(0, newPosition);
            } else {
                goRight();
                break;
            }
        }
    }

    public static void goRight() {
        int[] position = playerPosition.get(0);
        int line = position[0];
        int index = position[1];

        for(int i = index; i < grid.get(line).length; i++) {
            char currentChar = grid.get(line)[i];

            if(currentChar != '#') {
                int[] newPosition = {line, i};
                uniquePositions.add(newPosition[0] * 1000 + newPosition[1]);
                playerPosition.set(0, newPosition);
            } else {
                goDown();
                break;
            }
        }
    }

    public static void goDown() {
        int[] position = playerPosition.get(0);
        int line = position[0];
        int index = position[1];

        for(int i = line; i < grid.size(); i++) {
            char[] currentLine = grid.get(i);
            if(currentLine[index] != '#') {
                int[] newPosition = {grid.indexOf(currentLine), position[1]};
                uniquePositions.add(newPosition[0] * 1000 + newPosition[1]);
                playerPosition.set(0, newPosition);
            } else {
                goLeft();
                break;
            }
        }
    }

    public static void goLeft() {
        int[] position = playerPosition.get(0);
        int line = position[0];
        int index = position[1];
        char[] currentLine = grid.get(position[0]);

        for(int i = index; i >= 0; i--) {
            if(currentLine[i] != '#') {
                int[] newPosition = {line, i};
                uniquePositions.add(newPosition[0] * 1000 + newPosition[1]);
                playerPosition.set(0, newPosition);
            } else {
                goForward();
                break;
            }
        }
    }

    public static void main(String... args) throws IOException {

        problemOne();
        goForward();

        System.out.println("Total number of unique steps taken unhindered: "+uniquePositions.size());
    }
}
