package advent_2024;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class day_one {

    public static ArrayList<Integer> list1 = new ArrayList<>(Arrays.asList());
    public static ArrayList<Integer> list2 = new ArrayList<>();

    public static int difference = 0;

    public static int simScore = 0;

    public static void buildList() throws FileNotFoundException {
        Scanner s = new Scanner(new File("src/advent_2024/dayOne_file.txt"));
        int count = 0;
        while (s.hasNext()){
            // add the first value on each line to a list and then second value to different list
            if(count % 2 == 0) {
                list1.add(Integer.valueOf(s.next()));
                ++count;
            } else {
                list2.add(Integer.valueOf(s.next()));
                ++count;
            }
        }
        Collections.sort(list1);
        Collections.sort(list2);
        s.close();
    }

    public static void calculateDifference() {
        // increment through both lists and compare each of the values
        for (int i = 0; i < list1.size(); i++) {
            int value1 = list1.get(i);
            int value2 = list2.get(i);
            // if value from list 1 minus value from list 2 is positive that's the difference
            // add that value to the difference variable and keep going till end of list
            if (value1 - value2 > 0) {
                difference += value1 - value2;
            } else if (value2 - value1 > 0) {
                difference += value2 - value1;
            }
        }
    }

    public static void calculateSimScore() {
        for (Integer i : list1) {
            //check how often value from current iteration in list1 appears in list 2
            int frequency = Collections.frequency(list2, i);
            //if it appears more than 0 times multiply it by the value and add to simScore variable
            if(frequency > 0) {
                simScore += i * frequency;
            }
        }
    }

    public static void main(String... args) throws FileNotFoundException {
        buildList();
        calculateDifference();
        calculateSimScore();
        // print out the difference score - day one part one answer
        System.out.println(difference);
        // print out the similarity score - day one part two answer
        System.out.println(simScore);
    }
}
