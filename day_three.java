package advent_2024;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class day_three {

    private static int value1 = 0;

    private static int value2 = 0;

    private static final StringBuilder sb = new StringBuilder();

    public static void problemTwo() {
        // Define the regex pattern to match everything between "don't()" and "do()"
        String regex = "(?s)don't\\(\\).*?do\\(\\)";

        // Use the regex pattern to replace matched sections with an empty string
        String result = sb.toString().replaceAll(regex, "");

        int dontIndex = result.indexOf("don't()");
        if (dontIndex != -1) {
            // If "don't()" exists but no "do()" follows, remove everything after "don't()"
            result = result.substring(0, dontIndex);
        }

        // finally check the newly created result string with the same matching logic applied in part one
        String regexPattern = "mul\\((\\d+),(\\d+)\\)";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(result);
        while(matcher.find()) {
            int firstValue = Integer.parseInt(matcher.group(1));
            int secondValue = Integer.parseInt(matcher.group(2));
            value2 += (firstValue * secondValue);
        }
    }

    // read in file, identify instances of mul(num,num)
    public static void problemOne() throws FileNotFoundException {
        Scanner s = new Scanner(new File("src/advent_2024/dayThree_file.txt"));
        String regexPattern = "mul\\((\\d+),(\\d+)\\)";
        Pattern pattern = Pattern.compile(regexPattern);
        s.useDelimiter(pattern);
        while (s.hasNextLine()){
            String line = s.nextLine();
            Matcher matcher = pattern.matcher(line);
            //building a stringBuilder as it's easier to apply the part two logic on one long stringbuilder
            //as opposed to multiple lines read in
            sb.append(line);
            while(matcher.find()) {
                // matched values are multiplied and result is added to value variable
                int firstValue = Integer.parseInt(matcher.group(1));
                int secondValue = Integer.parseInt(matcher.group(2));
                value1 += (firstValue * secondValue);
            }
        }
        s.close();
    }

    public static void main(String... args) throws FileNotFoundException {
        problemOne();
        problemTwo();
        System.out.println(value1);
        System.out.println(value2);
    }

}
