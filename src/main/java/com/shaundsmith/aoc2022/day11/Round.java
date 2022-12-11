package com.shaundsmith.aoc2022.day11;

import lombok.AllArgsConstructor;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
class Round {

    private final List<Monkey> monkeys;

    void playGame(int rounds) {

        for (int i = 0; i < rounds; i++) {
            playRound();
        }

    }

    void playRound() {

        for (Monkey monkey : monkeys) {
            while (monkey.hasItemsLeft()) {
//                long newItem = monkey.processAndThrowItem();

                long newItem = monkey.processAndThrowItem(getMonkeyThrowModulus());
                int targetMonkey = monkey.getTargetMonkey(newItem);
                monkeys.get(targetMonkey).addItem(newItem);
            }
        }

    }

    BigInteger getMonkeyBusiness() {

        return monkeys.stream()
                .map(Monkey::getItemsProcessed)
                .sorted(Comparator.reverseOrder())
                .limit(2)
                .map(BigInteger::valueOf)
                .peek(System.out::println)
                .reduce(BigInteger.ONE, (a, b) -> a.multiply(b));
    }

    private int getMonkeyThrowModulus() {

        return monkeys.stream()
                .map(Monkey::getThrowModulus)
                .reduce(1, (a, b) -> a * b);
    }


}
