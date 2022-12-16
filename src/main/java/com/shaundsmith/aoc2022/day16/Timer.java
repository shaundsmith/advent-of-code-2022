package com.shaundsmith.aoc2022.day16;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
class Timer {

    @Getter
    private int value;

    void tick() {

        if (hasFinished()) {
            throw new IllegalStateException();
        }
        value--;
    }

    void tick(int steps) {

        if (hasFinished() || value - steps < 0) {
            throw new IllegalStateException();
        }
        value -= steps;
    }

    boolean hasFinished() {

        return value == 0;
    }

}
