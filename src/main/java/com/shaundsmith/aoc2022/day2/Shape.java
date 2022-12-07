package com.shaundsmith.aoc2022.day2;

import lombok.AllArgsConstructor;

@AllArgsConstructor
enum Shape {

    ROCK(1),
    PAPER(2),
    SCISSORS(3);

    final int score;

    static Shape forOpponent(String letter) {
        return switch (letter) {
            case "A":
                yield Shape.ROCK;
            case "B":
                yield Shape.PAPER;
            case "C":
                yield Shape.SCISSORS;
            default:
                throw new RuntimeException("Unknown letter " + letter);
        };
    }

    static Shape forPlayer(String letter) {
        return switch (letter) {
            case "X":
                yield Shape.ROCK;
            case "Y":
                yield Shape.PAPER;
            case "Z":
                yield Shape.SCISSORS;
            default:
                throw new RuntimeException("Unknown letter " + letter);
        };
    }

}
