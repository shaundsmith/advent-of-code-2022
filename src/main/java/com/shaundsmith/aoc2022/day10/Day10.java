package com.shaundsmith.aoc2022.day10;

import com.shaundsmith.aoc2022.FileReader;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

class Day10 {

    public static void main(String[] args) {

        // Part 1
        var clockCircuit = buildClockCircuitFromFile("day10/input.txt");

        Set<Integer> desiredCycles = Set.of(20, 60, 100, 140, 180, 220);
        Map<Integer, Integer> cycleValues = new HashMap<>();
        while(clockCircuit.canTick()) {
            clockCircuit.tick();
            if (desiredCycles.contains(clockCircuit.getCycle())) {
                cycleValues.put(clockCircuit.getCycle(), clockCircuit.getRegister());
            }
        }

        System.out.println(cycleValues.entrySet().stream()
                .map(entry -> "Cycle %d: %d".formatted(entry.getKey(), entry.getKey() * entry.getValue()))
                .collect(Collectors.joining("\n")));
        System.out.println("Sum: " + cycleValues.entrySet().stream()
                .mapToInt(entry -> entry.getValue() * entry.getKey())
                .sum());

        // Part 2
        var displayCircuit = buildClockCircuitFromFile("day10/input.txt");
        var display = new Display(displayCircuit, 40, 6);
        display.render();
        System.out.println(display);
    }

    private static ClockCircuit buildClockCircuitFromFile(String file) {
        var instructions = FileReader.readFile(file)
                .stream()
                .map(Day10::buildInstruction)
                .collect(Collectors.toCollection(LinkedList::new));

        return new ClockCircuit(instructions);
    }

    private static Instruction buildInstruction(String line) {

        Instruction instruction;
        if ("noop".equals(line)) {
            instruction = new NoopInstruction();
        } else if (line.startsWith("addx")) {
            String addXValue = line.split(" ")[1];
            instruction = new AddxInstruction(Integer.parseInt(addXValue));
        } else {
            throw new RuntimeException();
        }

        return instruction;
    }


}
