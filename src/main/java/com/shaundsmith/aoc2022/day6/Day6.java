package com.shaundsmith.aoc2022.day6;

import com.shaundsmith.aoc2022.FileReader;
import lombok.AllArgsConstructor;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
class Day6 {

    private final String inputFile;

    public static void main(String[] args) {

        var solution = new Day6("day6/input.txt");

        solution.run(4); // Part 1
        solution.run(14); // Part 2
    }

    void run(int messageSize) {

        List<String> lines = FileReader.readFile(inputFile);
        String signal = lines.get(0);

        Window<Character> window = new Window<>(messageSize);
        for (int i = 0; i < signal.length(); i++) {
            window.add(signal.charAt(i));
            if (window.isUnique()) {
                System.out.printf("Found start of marker '%s' at %d%n", window, i + 1);
                break;
            }
        }
    }

    @AllArgsConstructor
    static class Window<R> {

        private final Deque<R> store = new LinkedList<>();
        private int size;

        void add(R elem) {
            if (store.size() == size) {
                store.removeFirst();
            }
            store.addLast(elem);
        }

        boolean isUnique() {
            return new HashSet<>(store).size() == size;
        }

        @Override
        public String toString() {
            return store.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining());
        }
    }
}
