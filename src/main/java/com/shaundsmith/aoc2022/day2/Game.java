package com.shaundsmith.aoc2022.day2;

import java.util.Map;

class Game {

    static Map<Shape, Shape> WIN_MAP = Map.of(
            Shape.SCISSORS, Shape.PAPER,
            Shape.PAPER, Shape.ROCK,
            Shape.ROCK, Shape.SCISSORS
    );

    static int play(Shape player, Shape opponent) {

        int gameScore = 0;
        if (opponent.score == player.score) {
            gameScore = 3;
        } else if (WIN_MAP.get(player) == opponent) {
            gameScore = 6;
        }

        return gameScore + player.score;
    }

    static int play2(String strategy, Shape opponent) {

        Shape playerShape;
        if (strategy.equals("Y")) {
            playerShape = opponent;
        } else if (strategy.equals("X")) {
            playerShape = WIN_MAP.get(opponent);
        } else if (strategy.equals("Z")) {
            playerShape = WIN_MAP.entrySet().stream()
                    .filter(entry -> entry.getValue() == opponent)
                    .map(entry -> entry.getKey())
                    .findFirst()
                    .orElseThrow(RuntimeException::new);
        } else {
            throw new RuntimeException();
        }

        return play(playerShape, opponent);
    }

}
