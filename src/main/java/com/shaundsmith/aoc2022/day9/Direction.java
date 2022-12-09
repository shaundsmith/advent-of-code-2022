package com.shaundsmith.aoc2022.day9;

import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
enum Direction {

    UP("U"),
    DOWN("D"),
    LEFT("L"),
    RIGHT("R");

    private final String symbol;

    static Direction fromSymbol(String symbol) {

        return Arrays.stream(Direction.values())
                .filter(d -> d.symbol.equals(symbol))
                .findFirst()
                .orElseThrow();
    }

}
