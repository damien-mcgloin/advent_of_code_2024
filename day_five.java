package advent_2024;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class day_five {

    public static List<int[]> rules = new ArrayList<>();

    public static List<List<Integer>> updates = new ArrayList<>();

    public static List<Integer> numbers = new ArrayList<>();

    public static int count = 0;

    public static void buildList() throws FileNotFoundException {
        Scanner s = new Scanner(new File("src/advent_2024/dayFive_file.txt"));
        while (s.hasNext()){
            String line = s.next();
            if(line.contains("|")) {
                String[] rule = line.split("\\|");
                rules.add(new int[] {Integer.parseInt(rule[0]), Integer.parseInt(rule[1])});
            } else {
                numbers = Arrays.stream(line.split(","))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                updates.add(numbers);
            }
        }

        s.close();
    }

    public static void checkUpdates() {
        for (List<Integer> update : updates) {
            if(isUpdateValid(update, rules)) {
                System.out.println("Update " + update + " is valid");
                count += middleValue(update);
            }
        }
    }

    public static int middleValue(List<Integer> update) {
        return update.get(update.size()/2);
    }

    public static boolean isUpdateValid(List<Integer> update, List<int[]> rules) {
        // Create a map to quickly find the index of each page in the update
        Map<Integer, Integer> pageIndexMap = new HashMap<>();
        for (int i = 0; i < update.size(); i++) {
            pageIndexMap.put(update.get(i), i);
        }

        // For each rule, check if it's respected in the update
        for (int[] rule : rules) {
            int page1 = rule[0];
            int page2 = rule[1];

            // Check if both pages exist in the update
            if (pageIndexMap.containsKey(page1) && pageIndexMap.containsKey(page2)) {
                int index1 = pageIndexMap.get(page1);
                int index2 = pageIndexMap.get(page2);

                // If page1 appears after page2, the order is invalid
                if (index1 > index2) {
                    return false; // Invalid order
                }
            }
        }
        return true;
    }

    public static void main(String... args) throws FileNotFoundException {
        buildList();
        checkUpdates();
        System.out.println("The answer is " + count);
    }
}
