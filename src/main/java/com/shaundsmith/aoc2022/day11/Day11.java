package com.shaundsmith.aoc2022.day11;

import com.shaundsmith.aoc2022.FileReader;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

class Day11 {

    public static void main(String[] args) {
        List<String> lines = FileReader.readFile("day11/input.txt");
        Monkey.MonkeyBuilder builder = new Monkey.MonkeyBuilder();
        List<Monkey> monkeys = new ArrayList<>();
        for (String line : lines) {

            if (line.startsWith("Monkey ")) {
                builder = new Monkey.MonkeyBuilder();
            } else if (line.contains("Starting items: ")) {
                String itemsString = line.split(": ")[1];
                Deque<Long> items = Arrays.stream(itemsString.split(", "))
                        .map(Long::parseLong)
                        .collect(Collectors.toCollection(LinkedList::new));
                builder.items(items);
            } else if (line.contains("Operation: ")) {
                String operationString = line.split("new = ")[1];
                builder.operation(parseOperation(operationString));
            } else if (line.contains("Test: ")) {
                int throwModulus = Integer.parseInt(line.split("divisible by ")[1]);
                builder.throwModulus(throwModulus);
            } else if (line.contains("If true: ")) {
                String targetMonkey = line.split("throw to monkey ")[1];
                builder.testMonkeyTargetTrue(Integer.parseInt(targetMonkey));
            } else if (line.contains("If false: ")) {
                String targetMonkey = line.split("throw to monkey ")[1];
                builder.testMonkeyTargetFalse(Integer.parseInt(targetMonkey));
            } else if (line.isBlank()) {
                var monkey = builder.build();
                monkeys.add(monkey);
                builder = null;
            }
        }

        if (builder != null) {
            monkeys.add(builder.build());
        }

        Round round = new Round(monkeys);
        round.playGame(20);

        System.out.println("Monkey business: " + round.getMonkeyBusiness());
    }

    static Function<Long, Long> parseOperation(String operation) {

        String[] parts = operation.split(" ");
        Function<Long, Long> operationFn;
        if ("+".equals(parts[1])) {
            if ("old".equals(parts[2])) {
                operationFn = x -> x + x;
            } else {
                Long parseNumber = Long.parseLong(parts[2]);
                operationFn = x -> x + parseNumber;
            }
        } else if ("*".equals(parts[1])) {
            if ("old".equals(parts[2])) {
                operationFn = x -> x * x;
            } else {
                Long parseNumber = Long.parseLong(parts[2]);
                operationFn = x -> x * parseNumber;
            }
        } else {
            throw new UnsupportedOperationException();
        }

        return operationFn;
    }


}
