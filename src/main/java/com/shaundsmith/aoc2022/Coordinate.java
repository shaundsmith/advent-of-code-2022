package com.shaundsmith.aoc2022;

public record Coordinate(int x, int y) {

    public Coordinate translateX(int x) {
        return new Coordinate(this.x + x, y);
    }

    public Coordinate translateY(int y) {
        return new Coordinate(x, this.y + y);
    }

}
