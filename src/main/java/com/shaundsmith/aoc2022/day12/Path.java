package com.shaundsmith.aoc2022.day12;

import com.shaundsmith.aoc2022.Coordinate;
import lombok.Getter;

import java.util.Deque;
import java.util.LinkedList;

class Path {

    private final LinkedList<Coordinate> steps = new LinkedList<>();
    @Getter
    private final int score;
    @Getter
    private final double estimatedScore;

    Path(Coordinate start, double estimatedScore) {
        this.steps.add(start);
        this.score = 0;
        this.estimatedScore = estimatedScore;
    }

    private Path(LinkedList<Coordinate> steps,
                 Coordinate newStep,
                 int score,
                 double estimatedScore) {
        this.steps.addAll(steps);
        this.steps.add(newStep);
        this.score = score;
        this.estimatedScore = estimatedScore;
    }

    Coordinate getCurrentPosition() {
        return this.steps.getLast();
    }

    Path addStep(Coordinate step, double estimatedScore) {
        return new Path(steps, step, this.score + 1, estimatedScore);
    }

    boolean contains(Coordinate coordinate) {
        return this.steps.contains(coordinate);
    }

    int size() {
        return steps.size();
    }


}
