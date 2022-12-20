package com.shaundsmith.aoc2022.day20;

import com.shaundsmith.aoc2022.FileReader;

import java.util.ArrayList;
import java.util.List;

class Day20 {

    private static final long ENCRYPTION_KEY = 811589153;

    public static void main(String[] args) {

        List<String> lines = FileReader.readFile("day20/input.txt");
        List<ListNumber> originalNumbers = new ArrayList<>();
        List<ListNumber> decryptedOriginalNumbers = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            originalNumbers.add(new ListNumber(i, Long.parseLong(lines.get(i))));
            decryptedOriginalNumbers.add(new ListNumber(i, Long.parseLong(lines.get(i)) * ENCRYPTION_KEY));
        }

        mix(originalNumbers, 1);
        mix(decryptedOriginalNumbers, 10);
    }

    private static void mix(List<ListNumber> originalNumbers, int iterations) {

        CircularList<ListNumber> orderedNumbers = new CircularList<>(originalNumbers);
        for (int j = 0; j < iterations; j++) {
            for (int i = 0; i < orderedNumbers.size(); i++) {

                int originalIndex = i;
                var item = orderedNumbers.getIndexOf(l -> l.originalPosition() == originalIndex);
                orderedNumbers.move(item.location(), item.location() + item.value().value());
            }
        }

        var zero = orderedNumbers.getIndexOf(l -> l.value() == 0);
        var locationOfZero = zero.location();
        System.out.println("Item at 1000 = %s, 2000 = %s, 3000 = %s"
                .formatted(orderedNumbers.get(1000 + locationOfZero),
                        orderedNumbers.get(2000 + locationOfZero),
                        orderedNumbers.get(3000 + locationOfZero)));
        System.out.println("Answer: " +
                (orderedNumbers.get(1000 + locationOfZero).value()
                        + orderedNumbers.get(2000 + locationOfZero).value()
                        + orderedNumbers.get(3000 + locationOfZero).value()));
    }

}
