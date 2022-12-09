package com.shaundsmith.aoc2022.day9;

import com.shaundsmith.aoc2022.FileReader;

import java.util.List;
import java.util.stream.Collectors;

class Day9 {

    public static void main(String[] args) {

        List<String> lines = FileReader.readFile("day9/input.txt");

        var ropeBridge = new RopeBridge(9);
        for (String line : lines) {
            String[] command = line.split(" ");
            Direction direction = Direction.fromSymbol(command[0]);
            int amount = Integer.parseInt(command[1]);

            ropeBridge.move(direction, amount);
        }

        System.out.println(ropeBridge.getUniqueTailLocations().stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n")));
        System.out.println(ropeBridge.getUniqueTailLocations().size());

    }

}
