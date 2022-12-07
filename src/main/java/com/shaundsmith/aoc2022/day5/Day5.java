package com.shaundsmith.aoc2022.day5;

import com.shaundsmith.aoc2022.FileReader;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class Day5 {

    private static final Pattern STACK_PATTERN = Pattern.compile("(.{1,3}) ?", Pattern.MULTILINE);
    private static final Pattern MOVE_PATTERN = Pattern.compile("move (\\d+) from (\\d) to (\\d)");

    public static void main(String[] args) {

        List<String> lines = FileReader.readFile("day5/input.txt");

        CraneStacks.Builder builder = new CraneStacks.Builder();
        for (String line : lines) {
            if (line.contains("[")) {
                Matcher matcher = STACK_PATTERN.matcher(line);
                int counter = 0;
                while (matcher.find()) {
                    builder.addToStack(counter, "" + matcher.group(1).charAt(1));
                    counter++;
                }
            } else if (line.isBlank()) {
                break;
            }
        }

        CraneStacks craneStacks = builder.build();
        System.out.println(craneStacks + "\n\n");

        // Part 2 flag
        boolean moveAtOnce = true;
        for (String line : lines) {
            Matcher matcher = MOVE_PATTERN.matcher(line);
            if (matcher.find()) {
                craneStacks.moveStackTops(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)), moveAtOnce);
            }
        }

        System.out.println(craneStacks.getTopOfStacks().stream().collect(Collectors.joining()));
    }

}
