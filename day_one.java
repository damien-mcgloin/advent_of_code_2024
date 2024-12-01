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

    public static void compareLists() {
        Collections.sort(list1);
        Collections.sort(list2);

        for (int i = 0; i < list1.size(); i++) {
            int value1 = list1.get(i);
            int value2 = list2.get(i);
            if (value1 - value2 > 0) {
                difference += value1 - value2;
            } else if (value2 - value1 > 0) {
                difference += value2 - value1;
            }
        }
    }

    public static void main(String... args) throws FileNotFoundException {
        buildList();
        compareLists();
        System.out.println(difference);
    }

    public static void buildList() throws FileNotFoundException {
        Scanner s = new Scanner(new File("src/advent_2024/dayOne_file.txt"));
        int count = 1;
        while (s.hasNext()){
            if(count % 2 == 0) {
                list1.add(Integer.valueOf(s.next()));
                ++count;
            } else {
                list2.add(Integer.valueOf(s.next()));
                ++count;
            }
        }
        s.close();
    }
}
