package com.shaundsmith.aoc2022.day2;

import com.shaundsmith.aoc2022.FileReader;

import java.util.List;
import java.util.stream.Collectors;

class Day2 {

    public static void main(String[] args) {

        List<String> scores = FileReader.readFile("day2/input.txt");

        // Part 1
        System.out.println(scores.stream()
                .map(line -> line.split(" "))
                .map(line -> Game.play(Shape.forPlayer(line[1]), Shape.forOpponent(line[0])))
                .mapToInt(i -> i)
                .sum());

        // Part 2
        System.out.println(scores.stream()
                .map(line -> line.split(" "))
                .map(line -> Game.play2(line[1], Shape.forOpponent(line[0])))
                .mapToInt(i -> i)
                .sum());


    }

}
