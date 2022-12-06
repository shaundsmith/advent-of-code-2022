package com.shaundsmith.aoc2022.day4;

import com.shaundsmith.aoc2022.FileReader;
import com.shaundsmith.aoc2022.Pair;

import java.util.HashSet;
import java.util.Set;

class Day4 {

    public static void main(String[] args) {

        System.out.println("Part 1: " + (Long) FileReader.readFile("day4/input.txt")
                .stream()
                .map(line -> line.split(","))
                .map(ranges -> new Pair<>(Range.fromString(ranges[0]), Range.fromString(ranges[1])))
                .filter(ranges -> ranges.firstItem().contains(ranges.secondItem()) || ranges.secondItem().contains(ranges.firstItem()))
                .count());

        System.out.println("Part 2: " + (Long) FileReader.readFile("day4/input.txt")
                .stream()
                .map(line -> line.split(","))
                .map(ranges -> new Pair<>(Range.fromString(ranges[0]), Range.fromString(ranges[1])))
                .filter(ranges -> ranges.firstItem().overlap(ranges.secondItem()) || ranges.secondItem().overlap(ranges.firstItem()))
                .count());
    }

    record Range(int start, int end) {

        static Range fromString(String s) {

            String[] rangeString = s.split("-");

                return new Range(
                        Integer.parseInt(rangeString[0]),
                        Integer.parseInt(rangeString[1]));
            }

            boolean contains(Range range) {

                boolean containsStart = range.start >= start;
                boolean containsEnd = range.end <= end;

                return containsStart && containsEnd;
            }

            boolean overlap(Range range) {

                // Set intersection
                Set<Integer> asSet = this.toSet();
                asSet.retainAll(range.toSet());

                return !asSet.isEmpty();
            }

            private Set<Integer> toSet() {

                Set<Integer> set = new HashSet<>();
                for (int i = start; i <= end; i++) {
                    set.add(i);
                }
                return set;
            }

        }

}
