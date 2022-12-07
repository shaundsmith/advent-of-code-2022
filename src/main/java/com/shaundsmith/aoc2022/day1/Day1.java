package com.shaundsmith.aoc2022.day1;

import com.shaundsmith.aoc2022.FileReader;
import lombok.Data;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;

class Day1 {

    // 66186 196804

    public static void main(String[] args) {

        System.out.println(FileReader.readFile("day1/input.txt")
                .stream()
                .map(calorie -> calorie.isBlank() ? null : Long.valueOf(calorie))
                .reduce(new LinkedList<Calories>(),
                        (elves, calorie) -> {
                            if (calorie == null || elves.isEmpty()) {
                                elves.addLast(new Calories());
                            }
                            if (calorie != null) {
                                elves.getLast().add(calorie);
                            }

                            return elves;
                        },
                        (elves, newElves) -> {
                            elves.addAll(newElves);
                            return elves;
                        })
                .stream()
                .map(Calories::getValue)
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .mapToLong(i -> i)
                .summaryStatistics());

    }

    @Data
    static class Calories {

        long value = 0;

        void add(long more) {

            value += more;
        }
    }


}
