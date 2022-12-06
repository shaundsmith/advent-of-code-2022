package com.shaundsmith.aoc2022.day3;

import com.shaundsmith.aoc2022.FileReader;

import java.util.*;

class Day3 {

    public static void main(String[] args) {

        // Part 1
        System.out.println("Part 1: " + FileReader.readFile("day3/input.txt")
                .stream()
                .map(Day3::splitBag)
                .map(Day3::getCommonItemsForPart1)
                .map(Day3::convertPartsToNumber)
                .mapToLong(i -> i)
                .sum());

        // Part 2
        Deque<List<String>> groups = new LinkedList<>();
        List<String> fileContents = FileReader.readFile("day3/input.txt");
        for (int i = 0; i < fileContents.size(); i++) {
            if (i % 3 == 0) {
                groups.add(new ArrayList<>());
            }
            List<String> group = groups.getLast();
            group.add(fileContents.get(i));
        }

        System.out.println("Part 2: " + groups.stream()
                .map(Day3::getCommonItemsForPart2)
                .map(Day3::convertPartsToNumber)
                .mapToLong(i -> i)
                .sum());


    }

    static String[] splitBag(String bag) {

        String[] split = new String[2];
        split[0] = bag.substring(0, bag.length() / 2);
        split[1] = bag.substring(bag.length() / 2);

        return split;
    }

    static Set<Character> getCommonItemsForPart1(String[] bagCompartments) {

        Set<Character> common = new HashSet<>();
        for (char partChar : bagCompartments[0].toCharArray()) {
            if (bagCompartments[1].indexOf(partChar) >= 0) {
                common.add(partChar);
            }
        }

        return common;
    }

    static Set<Character> getCommonItemsForPart2(List<String> rucksackContents) {

        Set<Character> common = new HashSet<>();
        for (char partChar : rucksackContents.get(0).toCharArray()) {
            boolean itemInAllRucksacks = rucksackContents.subList(1, rucksackContents.size())
                    .stream()
                    .allMatch(p -> p.indexOf(partChar) >= 0);
            if (itemInAllRucksacks) {
                common.add(partChar);
            }
        }

        return common;
    }

    static long convertPartsToNumber(Set<Character> items) {

        long sum = 0L;

        for (Character item : items) {
            if (item >= 65 && item <= 90) {
                // Upper Case
                sum += item - 65 + 27;
            } else {
                // Lower Case
                sum += item - 96;
            }
        }

        return sum;
    }

}
