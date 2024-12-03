package advent_2024;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class day_two {

    private static List<String> list1 = new ArrayList<>();
    private static int safeCount = 0;
    private static int safeCount2 = 0;
    private static boolean safe = true;

    //method for day 2 part 1
    public static void calculateSafeLevel() {
        //iterate through list of values
        for(String i: list1) {
            //create array for each line, split on white space
            int[] numbers = Arrays.stream(i.split(" ")).mapToInt(Integer::parseInt).toArray();
            int increase = 0;
            int decrease = 0;
            for(int j = 0; j < numbers.length-1; j++) {
                int valueToCheck1 = numbers[j+1] - numbers[j];
                int valueToCheck2 = numbers[j] - numbers[j+1];
                //check if the values are incrementing on this line and within safety range
                if(valueToCheck1 >=1 && valueToCheck1 <=3 && decrease == 0) {
                    increase++;
                    //check if the values are decrementing on this line and within safety range
                } else if (valueToCheck2 >=1 && valueToCheck2 <=3 && increase == 0) {
                    decrease++;
                } else {
                    //either we're alternating between incrementing and decrementing or the difference
                    //between elements is too great
                    safe=false;
                }
            }

            if(safe) {
                safeCount++;
            } else {
                safe = true;
            }
        }
    }

    // method for day 2 part 2
    public static void calculateSafeLevel2() {
        //iterate through list of values
        for(String i: list1) {
            //create list for each line, split on white space
            List<Integer> numbers = Arrays.stream(i.split(" ")).mapToInt(Integer::parseInt).boxed().toList();
            safe = check(numbers, 0, 0);
            List<Integer> newList = new ArrayList<>(List.copyOf(numbers));
            //recheck to see if this line is safe if one of the elements weren't included
            if(!safe) {
                for(int j = 0; j < numbers.size() && !safe; j++) {
                    newList.remove(j);
                    safe = check(newList, 0, 0);
                    newList.add(j, numbers.get(j));
                }
            }

            if(safe) {
                safeCount2++;
            } else {
                safe = true;
            }
        }
    }

    // for part 2 putting logic for determining safety into a separate method
    private static boolean check(List<Integer> numbers, int increase, int decrease) {
        for(int j = 0; j < numbers.size()-1; j++) {
            int valueToCheck1 = numbers.get(j+1) - numbers.get(j);
            int valueToCheck2 = numbers.get(j) - numbers.get(j+1);
            if(valueToCheck1 >=1 && valueToCheck1 <=3 && decrease == 0) {
                increase++;
            } else if (valueToCheck2 >=1 && valueToCheck2 <=3 && increase == 0) {
                decrease++;
            } else {
                return false;
            }
        }
        return true;
    }

    // create list of values using new line as a delimiter
    public static void buildList() throws FileNotFoundException {
        Scanner s = new Scanner(new File("src/advent_2024/dayTwo_file.txt"));
        s.useDelimiter("\n");
        while (s.hasNext()){
            list1.add(s.next());
        }
        s.close();
    }

    public static void main(String... args) throws FileNotFoundException {
        buildList();
        calculateSafeLevel();
        System.out.println(safeCount);
        calculateSafeLevel2();
        System.out.println(safeCount2);
    }
}
