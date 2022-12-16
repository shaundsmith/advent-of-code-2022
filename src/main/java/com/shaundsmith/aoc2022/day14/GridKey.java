package com.shaundsmith.aoc2022.day14;

import lombok.AllArgsConstructor;

@AllArgsConstructor
enum GridKey {

    ROCK("#"),
    SAND_START("+"),
    SAND("o"),
    AIR(".");

    private final String character;

    @Override
    public String toString() {

        return character;
    }

    boolean isEmpty() {

        return this == AIR || this == SAND_START;
    }
}
